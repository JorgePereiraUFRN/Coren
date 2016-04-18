package br.ufrn.contextanalyzer.api.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.contextanalyzer.api.dao.WidgetEntityDAO;
import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.AttributeEntityHistory;
import br.ufrn.contextanalyzer.api.entities.OutcomeEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;
import br.ufrn.contextanalyzer.api.exceptions.EntityAlreadyRegistredException;
import br.ufrn.contextanalyzer.api.exceptions.EntityNotFoundException;
import br.ufrn.contextanalyzer.api.exceptions.UnprocessableEntityException;
import br.ufrn.contextanalyzer.arch.dao.DAOFactory;
import br.ufrn.contextanalyzer.hub.controllers.HubFacade;
import br.ufrn.contextanalyzer.hub.exceptions.TopicAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;
import context.arch.storage.AttributeNameValue;
import context.arch.widget.Widget;

public class WidgetService {
	
	private static final WidgetService singleton = new WidgetService();
	private static final WidgetEntityDAO dao = DAOFactory.getFactory().getWidgetEntityDAO();
	private static final Map<String, Widget> widgets = Collections.synchronizedMap(new HashMap<String, Widget>());
	
	private WidgetService() {};
	public static WidgetService getInstance() {
		return singleton;
	}

	public Widget createWidget(WidgetEntity widgetEntity) throws EntityAlreadyRegistredException, UnprocessableEntityException, TopicAlreadyRegistredException {
		
		validate(widgetEntity);
		
		if (widgets.get(widgetEntity.getName()) != null) {
			throw new EntityAlreadyRegistredException();
		}
		
		Widget widget = widgetEntity.createWidget();
		widgets.put(widgetEntity.getName(), widget);
		
		dao.save(widgetEntity);
		
		HubFacade.register(widget);
		
		return widget;
	}
	
	public Widget createWidget(OutcomeEntity outcome) {
		
		Widget widget = WidgetEntity.createWidget(outcome);
		
		return widget;
	}
	
	public void updateWidget(String widget, AttributeEntity attributeEntity) throws EntityNotFoundException, TopicNotFoundException {
		
		if(widgets.get(widget) == null) {
			throw new EntityNotFoundException();
		}
		
		AttributeNameValue<?> att = attributeEntity.toAttributeNameValue();
		widgets.get(widget).updateData(att.getName(), att.getValue());
		
		HubFacade.publish(widget + "_" + attributeEntity.getName(), String.valueOf(attributeEntity.getValue()));
		
		WidgetEntity widgetEntity = dao.findByName(widget);
		widgetEntity.updateAttributeValue(attributeEntity);
		dao.update(widgetEntity);
	
	}
	
	public Widget getMemoryWidget(String name) throws EntityNotFoundException {
		
		if(widgets.get(name) == null) {
			throw new EntityNotFoundException();
		}
		
		return widgets.get(name);
	}

	
	@SuppressWarnings("unused")
	private void validate(WidgetEntity widget) throws UnprocessableEntityException {
		// TODO validar entidade do widget aqui.
		if(false) {
			throw new UnprocessableEntityException();
		}
	}
	public List<AttributeEntityHistory> getLastUpdatesAttributeWidget(String widget, String attribute) {
		List<AttributeEntityHistory> list = dao.getLastUpdatesAttribute(widget, attribute);
		return list;
	}

}
