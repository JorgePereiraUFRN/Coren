package br.ufrn.coren.RestApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import br.ufrn.coren.Controller.CorenFacade;
import br.ufrn.coren.Exceptions.WidgetNotFoundException;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.WidgetModel;
import context.arch.enactor.Enactor;
import context.arch.widget.Widget;

@Path("context")
public class CorenAPI {

	private CorenFacade facade = new CorenFacade();
	private static Map<String, Widget> widgets = Collections.synchronizedMap(new HashMap<String, Widget>());
	private static Map<String, Enactor> enactors = Collections.synchronizedMap(new HashMap<String, Enactor>());
	
	@POST
	@Path("create-widget")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createWidget(WidgetModel widgetModel) {

		if (widgets.get(widgetModel.getName()) != null) {
			return "error: already have a named widget " + widgetModel.getName();
		}

		String validationMessage = validateWidgetModel(widgetModel);
		if (!validationMessage.equals("success")) {
			return validationMessage;
		}


		Widget widget = facade.createWidget(widgetModel);
		widgets.put(widgetModel.getName(), widget);
		return "success";
	}

	@POST
	@Path("create-enactor")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createEnactor(EnactorModel enactorModel) {
		
		if (enactors.get(enactorModel.getName()) != null) {
			return "error: already have a named enactor " + enactorModel.getName();
		}

		String validationMessage = validateEnactorModel(enactorModel);
		if (!validationMessage.equals("success")) {
			return validationMessage;
		}

		try {
			Enactor enactor = facade.createEnactor(enactorModel);
			enactors.put(enactorModel.getName(), enactor);
		} catch (WidgetNotFoundException wnfe) {
			wnfe.printStackTrace();
			return "error: " + wnfe.getMessage();
		}
		return "success";
	}
	
	@PUT
	@Path("update-widget")
	public <T extends Comparable<? super T>> String updateWidget(String widget, String attribute, T value) {
		if(!widgets.containsKey(widget)) {
			return "error: widget " + widget + " not found";
		}
		widgets.get(widget).updateData(attribute, value);
		return "success";
	}
	
	private String validateWidgetModel(WidgetModel widget) {
		return "success";
	}
	
	private String validateEnactorModel(EnactorModel widget) {
		return "success";
	}
	
	public static Widget getWidget(String name) throws WidgetNotFoundException {
		if(!widgets.containsKey(name)) {
			throw new WidgetNotFoundException();
		}
		return widgets.get(name);
	}
	
	
}
