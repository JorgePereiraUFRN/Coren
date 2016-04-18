package br.ufrn.contextanalyzer.hub.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.contextanalyzer.arch.dao.DAOFactory;
import br.ufrn.contextanalyzer.hub.dao.TopicEntityDAO;
import br.ufrn.contextanalyzer.hub.entities.SubscribeEntity;
import br.ufrn.contextanalyzer.hub.entities.TopicEntity;
import br.ufrn.contextanalyzer.hub.exceptions.SubscribeAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.SubscribeNotFoundException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;
import br.ufrn.contextanalyzer.hub.processors.NotifySubscribes;
import context.arch.storage.Attribute;
import context.arch.widget.Widget;

public class TopicService {
	
	private static final TopicService singleton = new TopicService();
	private static final TopicEntityDAO dao = DAOFactory.getFactory().getTopicEntityDAO();
	private static final Map<String, List<SubscribeEntity>> subscribers = Collections.synchronizedMap(new HashMap<String, List<SubscribeEntity>>());
	private static final Map<String, TopicEntity> topics = Collections.synchronizedMap(new HashMap<String, TopicEntity>());
	
	private TopicService() {};
	public static TopicService getInstance() {
		return singleton;
	}
	
	public List<TopicEntity> getAll() {
		return dao.findAll();
	}
	
	public void publish(String topic, String value) throws TopicNotFoundException {
		
		if (topics.get(topic) == null) {
			throw new TopicNotFoundException();
		}
		
		new NotifySubscribes(subscribers.get(topic).iterator(),value).notifySubscribers();
	}
	
	public void register(TopicEntity topicEntity) throws TopicAlreadyRegistredException {
		
		if (topics.get(topicEntity.getName()) != null) {
			throw new TopicAlreadyRegistredException();
		}
		
		dao.save(topicEntity);
		
		subscribers.put(topicEntity.getName(), new ArrayList<SubscribeEntity>());
		topics.put(topicEntity.getName(), topicEntity);
	}
	
	public void subscribe(String topic, SubscribeEntity subscribeEntity) throws TopicNotFoundException, SubscribeAlreadyRegistredException {

		if (topics.get(topic) == null) {
			throw new TopicNotFoundException();
		}
		
		if (subscribers.get(topic).contains(subscribeEntity)) {
			throw new SubscribeAlreadyRegistredException();
		}
		
		TopicEntity topicEntity = dao.findByName(topic);
		topicEntity.getSubscribes().add(subscribeEntity);
		dao.update(topicEntity);
		
		subscribers.get(topic).add(subscribeEntity);
	}
	
	public void unsubscribe(String topic, SubscribeEntity subscribeEntity) throws SubscribeNotFoundException, TopicNotFoundException {
		
		if (topics.get(topic) == null) {
			throw new TopicNotFoundException();
		}
		
		if (!subscribers.get(topic).contains(subscribeEntity)) {
			throw new SubscribeNotFoundException();
		}
		
		subscribers.get(topic).remove(subscribeEntity);
		
		TopicEntity topicEntity = dao.findByName(topic);
		topicEntity.getSubscribes().remove(subscribeEntity);
		dao.update(topicEntity);
	}
	
	@SuppressWarnings("rawtypes")
	public void register(Widget widget) throws TopicAlreadyRegistredException {
		for (Attribute att : widget.getNonConstantAttributes().values()) {
			TopicEntity topic = new TopicEntity(widget.getClassname() + "_" + att.getName(), 
					"Topic for attribute '" + att.getName() + "' of widget '" + widget.getClassname() + "'.");
			register(topic);
		}
	}

}
