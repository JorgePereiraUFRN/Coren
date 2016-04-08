package br.ufrn.coren.QueryCreator;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.coren.Entities.api.ConctextQuery;
import br.ufrn.coren.Entities.api.ConstAttribute;
import br.ufrn.coren.Entities.api.ContextEntity;
import br.ufrn.coren.Entities.api.NonConstAttribute;

public class QueryCreator {
	
	private static Client client = Client.create();
	
	public static void searchEntities(){
		WebResource resource = client.resource("http://localhost:8080/Coren-WebService/context/list-entities");
		
		ContextEntity entities[];
		
		entities = resource.type(MediaType.APPLICATION_JSON).get(ContextEntity[].class);
		
		for(ContextEntity e : entities){
			System.out.println("entity = "+ e.getName());
			
			for(ConstAttribute at : e.getConstAttributes()){
				System.out.println("at_name = "+at.getAttName()+", at_type: "+at.getType()+", at value = "+at.getValue());
			}
			
			for(NonConstAttribute at : e.getNonConstAttributes()){
				System.out.println("at_name = "+at.getAttName()+", at_type: "+at.getType());
			}
		}
		
	}
	
	public static void createQuery(){
		
		WebResource resource = client.resource("http://localhost:8080/Coren-WebService/context/create-query");
		ConctextQuery query = new ConctextQuery();
		
		query.setEntity("smart_home");
		query.setOutcome("potencia");
		query.setOutcomeValue("temperature * 2");
		query.setQuery("And (temperature > 20 ) (has_people = true)");
		query.setQueryDescriptiom("retorna a potencia do condicionador de ar quando exixtem pessoas na sala");
		query.setQueryName("has_people_in_room");
		
		String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, query);
		
		System.out.println(message);
	}

	public static void main(String[] args) {
		
		searchEntities();
		createQuery();

	}

}
