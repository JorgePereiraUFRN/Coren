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

		List<AttributeModel<?>> atts1 = new ArrayList<AttributeModel<?>>();		
		AttributeModel<Integer> presenca = new AttributeModel<Integer>();
		presenca.setType("int");
		presenca.setName("presence");
		atts1.add(presenca);
		WidgetModel widget1 = new WidgetModel();
		widget1.setName("PresenceWidget");
		widget1.setAttributes(atts1);
		
		String message1 = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget1);
		System.out.println(message1);
		
		List<AttributeModel<?>> atts2 = new ArrayList<AttributeModel<?>>();		
		AttributeModel<Integer> brightness = new AttributeModel<Integer>();
		brightness.setType("int");
		brightness.setName("brightness");
		atts2.add(brightness);
		WidgetModel widget2 = new WidgetModel();
		widget2.setName("BrightnessWidget");
		widget2.setAttributes(atts2);
		
		String message2 = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget2);
		System.out.println(message2);

	}
	
	public static void main(String args[]){
		
		createWidget();
	}

}
