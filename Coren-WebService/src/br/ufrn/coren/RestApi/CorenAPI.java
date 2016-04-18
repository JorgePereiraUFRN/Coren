package br.ufrn.coren.RestApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.el.ReferenceSyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrn.coren.Controller.CorenFacade;
import br.ufrn.coren.Exceptions.WidgetNotFoundException;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.ReferenceModel;
import br.ufrn.coren.Models.WidgetModel;
import context.arch.enactor.Enactor;
import context.arch.widget.Widget;

@Path("context")
public class CorenAPI {

	private CorenFacade facade = new CorenFacade();
	private static Map<String, Widget> widgets = Collections.synchronizedMap(new HashMap<String, Widget>());
	private static Map<String, Enactor> enactors = Collections.synchronizedMap(new HashMap<String, Enactor>());
	private static List<WidgetModel> widgetsModel = Collections.synchronizedList(new ArrayList<WidgetModel>());
	private static List<ReferenceModel> references = Collections.synchronizedList(new ArrayList<ReferenceModel>());
	
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
		widgetsModel.add(widgetModel);
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
		
		references.addAll(enactorModel.getReferences());
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
	
	@GET
	@Path("list-queries")
	@Produces(MediaType.APPLICATION_JSON)
	public  ReferenceModel[] listQueries(){
		return references.toArray(new ReferenceModel[references.size()]);
	}
	
	@GET
	@Path("list-entities")
	@Produces(MediaType.APPLICATION_JSON)
	public WidgetModel[] listEntities(){
		return widgetsModel.toArray(new WidgetModel[widgetsModel.size()]);
		
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
