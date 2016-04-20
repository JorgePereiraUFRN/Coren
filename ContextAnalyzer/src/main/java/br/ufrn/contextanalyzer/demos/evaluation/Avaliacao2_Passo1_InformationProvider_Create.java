package br.ufrn.contextanalyzer.demos.evaluation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.api.entities.OutcomeEntity;
import br.ufrn.contextanalyzer.api.entities.QueryEntity;
import br.ufrn.contextanalyzer.api.entities.ReferenceEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;

public class Avaliacao2_Passo1_InformationProvider_Create {
	
	private static final String SERVER_IP = "192.168.0.100";
//	private static final String SERVER_IP = "localhost";
	
	public static void main(String[] args) {
		
		Client client = Client.create();
		
		WebResource resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/widget/create");
		List<AttributeEntity> atts = new ArrayList<AttributeEntity>();		
		AttributeEntity temperature = new AttributeEntity();
		temperature.setType("int");
		temperature.setName("attribute_in");
		atts.add(temperature);
		WidgetEntity widget = new WidgetEntity();
		widget.setName("Sensor");
		widget.setAttributes(atts);
		String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget);
		System.out.println(message);
		
		resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/enactor/create");
		EnactorEntity enactor = new EnactorEntity();
		enactor.setName("Rule");
		List<String> widgets = new ArrayList<String>();
		widgets.add("Sensor");
		enactor.setWidgets(widgets);
		OutcomeEntity outcome = new OutcomeEntity();
		outcome.setName("attribute_out");
		outcome.setType("string");
		outcome.setDescription("Attribute out");
		enactor.setOutcome(outcome);
		List<ReferenceEntity> references = new ArrayList<ReferenceEntity>();
		ReferenceEntity off = new ReferenceEntity();
		off.setName("Off");
		OutcomeEntity outcomeOff = new OutcomeEntity();
		outcomeOff.setName("attribute_out");
		outcomeOff.setType("string");
		outcomeOff.setDescription("Attribute out");
		outcomeOff.setValue("0");
		off.setOutcome(outcomeOff);
		QueryEntity attributeOff = new QueryEntity();
		attributeOff.setNome("attributeOff");
		attributeOff.setValue("(OR (GREATER attribute_in 50) )");
		off.setQuery(attributeOff);
		references.add(off);
		ReferenceEntity on = new ReferenceEntity();
		on.setName("On");
		OutcomeEntity outcomeOn = new OutcomeEntity();
		outcomeOn.setName("attribute_out");
		outcomeOn.setType("string");
		outcomeOn.setDescription("Attribute out");
		outcomeOn.setValue("1");
		on.setOutcome(outcomeOn);
		QueryEntity attributeOn = new QueryEntity();
		attributeOn.setNome("attributeOn");
		attributeOn.setValue("(ELSE (QUERY attributeOff) )");
		on.setQuery(attributeOn);
		references.add(on);
		enactor.setReferences(references);
		message = resource.type(MediaType.APPLICATION_JSON).post(String.class, enactor);
		System.out.println(message);
		
	}
	
	

}
