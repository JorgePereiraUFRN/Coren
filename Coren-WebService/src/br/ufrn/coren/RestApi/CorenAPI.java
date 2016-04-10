package br.ufrn.coren.RestApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import br.ufrn.coren.Controller.CorenFacade;
import br.ufrn.coren.Exceptions.WidgetNotFoundException;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.WidgetModel;

@Path("context")
public class CorenAPI {

	private CorenFacade facade = new CorenFacade();
	private static Map<String, WidgetModel> widgets = Collections.synchronizedMap(new HashMap<String, WidgetModel>());
	private static Map<String, EnactorModel> enactors = Collections.synchronizedMap(new HashMap<String, EnactorModel>());
	
	@POST
	@Path("create-widget")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createWidget(WidgetModel widget) {

		if (widgets.get(widget.getName()) != null) {
			return "error: already have a named widget " + widget.getName();
		}

		String validationMessage = validateWidgetModel(widget);
		if (!validationMessage.equals("success")) {
			return validationMessage;
		}


		facade.createWidget(widget);
		widgets.put(widget.getName(), widget);
		return "success";
	}

	@POST
	@Path("create-enactor")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createEnactor(EnactorModel enactor) {
		
		if (enactors.get(enactor.getName()) != null) {
			return "error: already have a named enactor " + enactor.getName();
		}

		String validationMessage = validateEnactorModel(enactor);
		if (!validationMessage.equals("success")) {
			return validationMessage;
		}

		try {
			facade.createEnactor(enactor);
			enactors.put(enactor.getName(), enactor);
		} catch (WidgetNotFoundException wnfe) {
			wnfe.printStackTrace();
			return "error: " + wnfe.getMessage();
		}
		return "success";
	}
	
	private String validateWidgetModel(WidgetModel widget) {
		return "success";
	}
	
	private String validateEnactorModel(EnactorModel widget) {
		return "success";
	}
	
	public static WidgetModel getWidget(String name) throws WidgetNotFoundException {
		if(!widgets.containsKey(name)) {
			throw new WidgetNotFoundException();
		}
		return widgets.get(name);
	}
	
	
}
