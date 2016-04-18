package br.ufrn.contextanalyzer.hub.dao;

import javax.persistence.EntityManagerFactory;

import br.ufrn.contextanalyzer.arch.dao.EntityManagerDAO;
import br.ufrn.contextanalyzer.hub.entities.TopicEntity;

public class EntityManagerTopicEntityDAO extends EntityManagerDAO<TopicEntity, Long> implements TopicEntityDAO {
	
	public EntityManagerTopicEntityDAO(EntityManagerFactory factory) {
		super(factory);
	}
	
}
