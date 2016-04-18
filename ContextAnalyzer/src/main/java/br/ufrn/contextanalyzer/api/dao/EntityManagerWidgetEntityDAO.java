package br.ufrn.contextanalyzer.api.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import br.ufrn.contextanalyzer.api.entities.AttributeEntityHistory;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;
import br.ufrn.contextanalyzer.api.exceptions.DAOException;
import br.ufrn.contextanalyzer.arch.dao.EntityManagerDAO;

public class EntityManagerWidgetEntityDAO extends EntityManagerDAO<WidgetEntity, Long> implements WidgetEntityDAO {
	
	public EntityManagerWidgetEntityDAO(EntityManagerFactory factory) {
		super(factory);
	}

	@SuppressWarnings("unchecked")
	public List<AttributeEntityHistory> getLastUpdatesAttribute(String widget, String attribute) throws DAOException {
		EntityManager em = factory.createEntityManager();
		List<AttributeEntityHistory> list = null;
		try {
			list = (List<AttributeEntityHistory>) em.createQuery(
					"SELECT ah FROM AttributeEntityHistory AS ah "
					+ "INNER JOIN AttributeEntity AS a ON (a = ah.attribute) "
					+ "INNER JOIN WidgetEntity AS w ON (w = a.widget) "
					+ "WHERE w.name = :widget AND a.name = :attribute")
					.setParameter("widget", widget)
					.setParameter("attribute", attribute)
					.getResultList();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return list;
	}
	
}
