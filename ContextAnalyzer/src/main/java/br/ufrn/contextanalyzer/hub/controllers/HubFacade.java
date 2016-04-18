package br.ufrn.contextanalyzer.hub.controllers;

import java.util.List;

import br.ufrn.contextanalyzer.hub.entities.SubscribeEntity;
import br.ufrn.contextanalyzer.hub.entities.TopicEntity;
import br.ufrn.contextanalyzer.hub.exceptions.SubscribeAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.SubscribeNotFoundException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;
import br.ufrn.contextanalyzer.hub.services.TopicService;
import context.arch.widget.Widget;

public class HubFacade {

	public static List<TopicEntity> getAllTopics() {
		return TopicService.getInstance().getAll();
	}

	public static void publish(String topic, String value) throws TopicNotFoundException   {
		TopicService.getInstance().publish(topic, value);
	}

	public static void register(TopicEntity topicEntity) throws TopicAlreadyRegistredException   {
		TopicService.getInstance().register(topicEntity);
	}

	public static void subscribe(String topic, SubscribeEntity subscribeEntity) throws TopicNotFoundException, SubscribeAlreadyRegistredException {
		TopicService.getInstance().subscribe(topic, subscribeEntity);
	}

	public static void unsubscribe(String topic, SubscribeEntity subscribeEntity) throws SubscribeNotFoundException, TopicNotFoundException {
		TopicService.getInstance().unsubscribe(topic, subscribeEntity);
	}

	public static void register(Widget widget) throws TopicAlreadyRegistredException {
		TopicService.getInstance().register(widget);
	}

}
