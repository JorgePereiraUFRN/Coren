package br.ufrn.coren.InformationProvider;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.coren.Models.AttributeModel;
import br.ufrn.coren.Models.WidgetModel;

public class InformationProvider {
	
	public static void createWidget() {

		Client client = Client.create();
		
		WebResource resource = client.resource("http://localhost:8080/Coren-WebService/context/create-widget");

		List<AttributeModel<?>> atts = new ArrayList<AttributeModel<?>>();
		AttributeModel<Integer> temperatura = new AttributeModel<Integer>();
		temperatura.setType("int");
		temperatura.setName("temperatura");
		atts.add(temperatura);
		AttributeModel<Integer> presenca = new AttributeModel<Integer>();
		presenca.setType("int");
		presenca.setName("presenca");
		atts.add(presenca);
		WidgetModel widget = new WidgetModel();
		widget.setName("RoomWidget");
		widget.setAttributes(atts);
		
		
		String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget);
		
		System.out.println(message);

	}
	
	public static void main(String args[]){
		
		createWidget();
	}

}
