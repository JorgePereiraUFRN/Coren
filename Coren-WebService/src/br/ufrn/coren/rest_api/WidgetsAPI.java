package br.ufrn.coren.rest_api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/widgets")
public class WidgetsAPI {
	
	
	@POST
	@Path("/{widget_name}")
	@Consumes(MediaType.APPLICATION_XML)
	public void createWidget(String xml_widget, @PathParam("widget_name") String widget_name){
		
		//receber o xml, salvar num arquivo e instanciar o widget
	}
	
	
	@PUT
	@Path("/{widget_name}/{widget_atribute}")
	public void updateValueWidget(@PathParam("widget_name") String widget_name, 
			@PathParam("widget_atribute") String widget_atribute,
			@QueryParam("value") String value){
		
	}

}
