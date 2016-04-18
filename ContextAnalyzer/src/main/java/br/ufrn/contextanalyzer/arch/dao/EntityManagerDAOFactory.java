package br.ufrn.contextanalyzer.arch.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufrn.contextanalyzer.api.dao.EnactorEntityDAO;
import br.ufrn.contextanalyzer.api.dao.EntityManagerEnactorEntityDAO;
import br.ufrn.contextanalyzer.api.dao.EntityManagerWidgetEntityDAO;
import br.ufrn.contextanalyzer.hub.dao.EntityManagerTopicEntityDAO;
import br.ufrn.contextanalyzer.hub.dao.TopicEntityDAO;

public class EntityManagerDAOFactory extends DAOFactory {
	
	private EntityManagerFactory entityManagerFactory;

	public EntityManagerDAOFactory(String persistenceUnit) {
		this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
	}
	
	@Override
	public EntityManagerWidgetEntityDAO getWidgetEntityDAO() {
		return new EntityManagerWidgetEntityDAO(entityManagerFactory);
	}

	@Override
	public EnactorEntityDAO getEnactorEntityDAO() {
		return new EntityManagerEnactorEntityDAO(entityManagerFactory);
	}

	@Override
	public TopicEntityDAO getTopicEntityDAO() {
		return new EntityManagerTopicEntityDAO(entityManagerFactory);
	}

}
