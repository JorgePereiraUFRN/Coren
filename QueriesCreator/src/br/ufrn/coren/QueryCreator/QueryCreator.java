package br.ufrn.coren.QueryCreator;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.OutcomeModel;
import br.ufrn.coren.Models.QueryModel;
import br.ufrn.coren.Models.ReferenceModel;

public class QueryCreator {
	
	private static Client client = Client.create();
	
//	public static void searchEntities(){
//		WebResource resource = client.resource("http://localhost:8080/Coren-WebService/context/list-entities");
//		
//		ContextEntity entities[];
//		
//		entities = resource.type(MediaType.APPLICATION_JSON).get(ContextEntity[].class);
//		
//		for(ContextEntity e : entities){
//			System.out.println("entity = "+ e.getName());
//			
//			for(ConstAttribute at : e.getConstAttributes()){
//				System.out.println("at_name = "+at.getAttName()+", at_type: "+at.getType()+", at value = "+at.getValue());
//			}
//			
//			for(NonConstAttribute at : e.getNonConstAttributes()){
//				System.out.println("at_name = "+at.getAttName()+", at_type: "+at.getType());
//			}
//		}
//		
//	}
	
	public static void createEnactor(){
		
		WebResource resource = client.resource("http://localhost:8080/Coren-WebService/context/create-enactor");
		
		EnactorModel enactor = new EnactorModel();
		enactor.setName("RoomEnactor");
		List<String> widgets = new ArrayList<String>();
		widgets.add("PresenceWidget");
		widgets.add("BrightnessWidget");
		enactor.setWidgets(widgets);
		OutcomeModel outcome = new OutcomeModel();
		outcome.setName("light");
		outcome.setType("string");
		outcome.setDescription("lampada da sala");
		enactor.setOutcome(outcome);
		List<ReferenceModel> references = new ArrayList<ReferenceModel>();
		ReferenceModel off = new ReferenceModel();
		off.setName("Off");
		OutcomeModel outcomeOff = new OutcomeModel();
		outcomeOff.setName("light");
		outcomeOff.setType("string");
		outcomeOff.setDescription("lampada da sala");
		outcomeOff.setValue("0");
		off.setOutcome(outcomeOff);
		QueryModel lightOff = new QueryModel();
		lightOff.setNome("lightOff");
		lightOff.setValue("(OR (EQUAL presence 0) (GREATER brightness 25) )");
		off.setQuery(lightOff);
		references.add(off);
		ReferenceModel on = new ReferenceModel();
		on.setName("On");
		OutcomeModel outcomeOn = new OutcomeModel();
		outcomeOn.setName("light");
		outcomeOn.setType("string");
		outcomeOn.setDescription("lampada da sala");
		outcomeOn.setValue("1");
		on.setOutcome(outcomeOn);
		QueryModel lightOn = new QueryModel();
		lightOn.setNome("lightOn");
		lightOn.setValue("(ELSE (QUERY lightOff) )");
		on.setQuery(lightOn);
		references.add(on);
		enactor.setReferences(references);
		
		String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, enactor);
		
		System.out.println(message);
	}

	public static void main(String[] args) {
		
		//searchEntities();
		createEnactor();

	}

}
