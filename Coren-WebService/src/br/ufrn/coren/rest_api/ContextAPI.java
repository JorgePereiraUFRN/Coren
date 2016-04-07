package br.ufrn.coren.rest_api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import br.ufrn.coren.Controller.ContextFacade;
import br.ufrn.coren.Entities.EnactorModel;
import br.ufrn.coren.Entities.OutcomeModel;
import br.ufrn.coren.Entities.QueryModel;
import br.ufrn.coren.Entities.ReferenceModel;
import br.ufrn.coren.Entities.WidgetModel;
import br.ufrn.coren.Entities.api.AttributeType;
import br.ufrn.coren.Entities.api.ConctextQuery;
import br.ufrn.coren.Entities.api.ConstAttribute;
import br.ufrn.coren.Entities.api.ContextEntity;
import br.ufrn.coren.Entities.api.NonConstAttribute;
import context.arch.storage.Attribute;

@Path("/context")
public class ContextAPI {

	private ContextFacade facade = new ContextFacade();
	private static Map<String, WidgetModel> widgets = Collections.synchronizedMap(new HashMap<String, WidgetModel>());
	private static AtomicInteger numEnactor = new AtomicInteger(0);

	@POST
	@Path("/create-entity")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createWidget(ContextEntity entity) {

		if (widgets.get(entity.getName()) != null) {
			return "erro: already have a named entity " + entity.getName();
		}

		String validationMessage = validateEntity(entity);
		if (!validationMessage.equals("sucess")) {
			return validationMessage;
		}

		WidgetModel widgetModel = new WidgetModel();

		widgetModel.setName(entity.getName());

		// ###### não encontrei aqui uma forma de setar o valor dos atributos
		// constantes ###
		for (ConstAttribute constAtt : entity.getConstAttributes()) {

			if (constAtt.getType() == AttributeType.BOOLEAN) {
				widgetModel.getConstAttributes().add(new Attribute<>(constAtt.getAttName(), Boolean.class));
			} else if (constAtt.getType() == AttributeType.FLOAT) {
				widgetModel.getConstAttributes().add(new Attribute<>(constAtt.getAttName(), Float.class));
			} else if (constAtt.getType() == AttributeType.INTEGER) {
				widgetModel.getConstAttributes().add(new Attribute<>(constAtt.getAttName(), Integer.class));
			} else if (constAtt.getType() == AttributeType.STRING) {
				widgetModel.getConstAttributes().add(new Attribute<>(constAtt.getAttName(), String.class));
			}
		}

		for (NonConstAttribute nConstAtt : entity.getConstAttributes()) {

			if (nConstAtt.getType() == AttributeType.BOOLEAN) {
				widgetModel.getNonConstAttributes().add(new Attribute<>(nConstAtt.getAttName(), Boolean.class));
			} else if (nConstAtt.getType() == AttributeType.FLOAT) {
				widgetModel.getNonConstAttributes().add(new Attribute<>(nConstAtt.getAttName(), Float.class));
			} else if (nConstAtt.getType() == AttributeType.INTEGER) {
				widgetModel.getNonConstAttributes().add(new Attribute<>(nConstAtt.getAttName(), Integer.class));
			} else if (nConstAtt.getType() == AttributeType.STRING) {
				widgetModel.getNonConstAttributes().add(new Attribute<>(nConstAtt.getAttName(), String.class));
			}
		}

		facade.createWidget(widgetModel);
		widgets.put(entity.getName(), widgetModel);

		return "sucess";
	}

	@POST
	@Path("/create-query")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createQuery(ConctextQuery query) {

		String validationMessage = validateQuery(query);
		if (!validationMessage.equals("sucess")) {
			return validationMessage;
		}
		
		EnactorModel enactorModel = new EnactorModel();

		WidgetModel widgetModel = widgets.get(query.getEntity());
		enactorModel.setInputWidget(widgetModel);

		ReferenceModel referenceModel = new ReferenceModel();

		OutcomeModel outcomeModel = new OutcomeModel();
		outcomeModel.setName(query.getOutcome());
		outcomeModel.setDescription(query.getQueryDescriptiom());
		outcomeModel.setValue(query.getOutcomeValue());

		referenceModel.setOutcome(outcomeModel);

		QueryModel queryModel = new QueryModel();
		queryModel.setNome(query.getQueryName());
		queryModel.setValue(query.getQuery());

		referenceModel.setQuery(queryModel);

		enactorModel.setReference(referenceModel);

		enactorModel.setName("enactor" + numEnactor.getAndIncrement());

		facade.createEnactor(enactorModel);

		return "sucess";
	}

	private String validateEntity(ContextEntity entity) {

		if (entity.getName() == null || entity.getName().equals("")) {
			return "The entity name was not be null";
		}

		for (ConstAttribute constAtt : entity.getConstAttributes()) {

			if (constAtt.getAttName() == null || constAtt.getAttName().equals("")) {
				return "the constant attribute name can not be null";
			}
			if (constAtt.getType() == null) {
				return "the constant attribute type can not be null";
			}
			if (constAtt.getValue() == null || constAtt.getValue().equals("")) {
				return "the constant attribute value can not be null";
			}
		}

		for (NonConstAttribute nConstAtt : entity.getNonConstAttributes()) {

			if (nConstAtt.getAttName() == null || nConstAtt.getAttName().equals("")) {
				return "the attribute name can not be null";
			}
			if (nConstAtt.getType() == null) {
				return "the  attribute type can not be null";
			}

		}

		return "sucess";
	}

	private String validateQuery(ConctextQuery query) {

		if (query.getEntity() == null || query.getEntity().equals("")) {
			return "The entity can not be null";
		}
		if (query.getOutcome() == null || query.getOutcome().equals("")) {
			return "The outcome can not be null";
		}
		if (query.getOutcomeValue() == null || query.getOutcomeValue().equals("")) {
			return "The outcome value can not be null";
		}
		if (query.getQuery() == null || query.getQuery().equals("")) {
			return "The query can not be null";
		}
		if(query.getQueryDescriptiom() == null || query.getQueryDescriptiom().equals("")){
			return "The query description can not be null";
		}
		if(query.getQueryName() == null || query.getQueryName().equals("")){
			return "The query name can not be null";
		}

		return "sucess";
	}
}
