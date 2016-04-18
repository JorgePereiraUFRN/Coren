package br.ufrn.contextanalyzer.api.dao;

import java.util.List;

import br.ufrn.contextanalyzer.api.entities.AttributeEntityHistory;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;
import br.ufrn.contextanalyzer.api.exceptions.DAOException;
import br.ufrn.contextanalyzer.arch.dao.GenericDAO;

public interface WidgetEntityDAO extends GenericDAO<WidgetEntity, Long> {

	List<AttributeEntityHistory> getLastUpdatesAttribute(String widget, String attribute) throws DAOException;

}
