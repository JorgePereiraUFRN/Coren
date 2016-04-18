package br.ufrn.contextanalyzer.api.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.AttributeEntityHistory;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;
import br.ufrn.contextanalyzer.api.exceptions.DAOException;
import br.ufrn.contextanalyzer.api.exceptions.EntityAlreadyRegistredException;
import br.ufrn.contextanalyzer.api.exceptions.EntityNotFoundException;
import br.ufrn.contextanalyzer.api.exceptions.UnprocessableEntityException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;

@Path("widget")
public class WidgetController {
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createWidget(WidgetEntity widgetEntity) throws TopicAlreadyRegistredException {
		try {
			APIFacade.createWidget(widgetEntity);
		} catch (EntityAlreadyRegistredException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		} catch (UnprocessableEntityException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		return "created";
	}
	
	@PUT
	@Path("update/{widget}")
	public String updateWidget(@PathParam("widget") String widget, AttributeEntity attributeEntity) {
		try {
			APIFacade.updateWidget(widget, attributeEntity);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		} catch (TopicNotFoundException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		return "updated";
	}
	
	@GET
	@Path("{widget}/last_updates/{attribute}")
	@Produces(MediaType.APPLICATION_JSON)
	public AttributeEntityHistory[] lastUpdatesAttributeWidget(@PathParam("widget") String widget, @PathParam("attribute") String attribute) {
		AttributeEntityHistory[] list = null;
		try {
			List<AttributeEntityHistory> temp = APIFacade.getLastUpdatesAttributeWidget(widget, attribute);
			list = temp.toArray(new AttributeEntityHistory[temp.size()]);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
