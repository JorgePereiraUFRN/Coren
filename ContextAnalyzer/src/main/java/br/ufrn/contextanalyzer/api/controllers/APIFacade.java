package br.ufrn.contextanalyzer.api.controllers;

import java.util.List;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.AttributeEntityHistory;
import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.api.entities.OutcomeEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;
import br.ufrn.contextanalyzer.api.exceptions.EntityAlreadyRegistredException;
import br.ufrn.contextanalyzer.api.exceptions.EntityNotFoundException;
import br.ufrn.contextanalyzer.api.exceptions.UnprocessableEntityException;
import br.ufrn.contextanalyzer.api.services.EnactorService;
import br.ufrn.contextanalyzer.api.services.WidgetService;
import br.ufrn.contextanalyzer.hub.exceptions.TopicAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;
import context.arch.enactor.Enactor;
import context.arch.widget.Widget;

public class APIFacade {
	
	private APIFacade() {};
	
	public static Widget createWidget(WidgetEntity widgetEntity) throws EntityAlreadyRegistredException, UnprocessableEntityException, TopicAlreadyRegistredException{
		return WidgetService.getInstance().createWidget(widgetEntity); 
	}
	
	public static <T extends Comparable<? super T>> void updateWidget(String widget, AttributeEntity attributeEntity) throws EntityNotFoundException, TopicNotFoundException {
		WidgetService.getInstance().updateWidget(widget, attributeEntity);;
	}
	
	public static Widget getMemoryWidget(String name) throws EntityNotFoundException{
		return WidgetService.getInstance().getMemoryWidget(name);
	}

	public static Widget createWidget(OutcomeEntity outcome) {
		return WidgetService.getInstance().createWidget(outcome);
	}
	
	public static Enactor createEnactor(EnactorEntity enactorEntity) throws EntityNotFoundException, EntityAlreadyRegistredException, UnprocessableEntityException{
		return EnactorService.getInstance().createEnactor(enactorEntity);
	}

	public static List<AttributeEntityHistory> getLastUpdatesAttributeWidget(String widget, String attribute) {
		return WidgetService.getInstance().getLastUpdatesAttributeWidget(widget, attribute);
	}
	
}
