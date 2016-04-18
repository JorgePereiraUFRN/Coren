package br.ufrn.contextanalyzer.arch.dao;

import java.lang.reflect.InvocationTargetException;

import br.ufrn.contextanalyzer.api.dao.EnactorEntityDAO;
import br.ufrn.contextanalyzer.api.dao.WidgetEntityDAO;
import br.ufrn.contextanalyzer.api.exceptions.DAOException;
import br.ufrn.contextanalyzer.hub.dao.TopicEntityDAO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class DAOFactory {

	private static DAOFactory factory = null;
	
	private static final String DEFAULT_PERSISTENCE_UNIT = "ContextAnalyzer";
	private static final Class DEFAULT_FACTORY_CLASS = EntityManagerDAOFactory.class;
	
	public static DAOFactory getFactory() {
		return getFactory(DEFAULT_FACTORY_CLASS,DEFAULT_PERSISTENCE_UNIT);
	}
	
	public static DAOFactory getFactory(Class factoryClass) {
		return getFactory(factoryClass,DEFAULT_PERSISTENCE_UNIT);
	}
	
	public static DAOFactory getFactory(String persistenceUnit) {
		return getFactory(DEFAULT_FACTORY_CLASS,persistenceUnit);
	}
	
	public static DAOFactory getFactory(Class factoryClass, String persistenceUnit) {
		try {
			if (factory == null) {
				factory = (DAOFactory) factoryClass.getConstructor(String.class).newInstance(persistenceUnit);
			}
		} catch (IllegalArgumentException e) {
			throw new DAOException();
		} catch (InvocationTargetException e) {
			throw new DAOException();
		} catch (NoSuchMethodException e) {
			throw new DAOException();
		} catch (SecurityException e) {
			throw new DAOException();
		} catch (IllegalAccessException e) {
			throw new DAOException();
		} catch (InstantiationException e) {
			throw new DAOException();
		}
		return factory;
	}
	
	public abstract WidgetEntityDAO getWidgetEntityDAO();
	
	public abstract EnactorEntityDAO getEnactorEntityDAO();
	
	public abstract TopicEntityDAO getTopicEntityDAO();

}