package br.ufrn.coren.InformationProvider;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.coren.Entities.api.AttributeType;
import br.ufrn.coren.Entities.api.ConstAttribute;
import br.ufrn.coren.Entities.api.ContextEntity;
import br.ufrn.coren.Entities.api.NonConstAttribute;

public class InformationProvider {

	

	public static void createEntity() {

		Client client = Client.create();
		
		WebResource resource = client.resource("http://localhost:8080/Coren-WebService/context/create-entity");

		ContextEntity entity1 = new ContextEntity();

		entity1.setName("smart_home");

		List<ConstAttribute> constatt = new ArrayList<>();

		ConstAttribute constAtt1 = new ConstAttribute();
		constAtt1.setAttName("room");
		constAtt1.setType(AttributeType.INTEGER);
		constAtt1.setValue("room");

		constatt.add(constAtt1);

		entity1.setConstAttributes(constatt);

		List<NonConstAttribute> nConstAtt = new ArrayList<>();
		NonConstAttribute ncAtt1 = new NonConstAttribute();
		ncAtt1.setAttName("temperature");
		ncAtt1.setType(AttributeType.INTEGER);

		NonConstAttribute ncAtt2 = new NonConstAttribute();
		ncAtt2.setAttName("has_people");
		ncAtt2.setType(AttributeType.BOOLEAN);

		nConstAtt.add(ncAtt1);
		nConstAtt.add(ncAtt2);
		entity1.setNonConstAttributes(nConstAtt);
		
		String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, entity1);
		
		System.out.println(message);

	}
	
	public static void main(String args[]){
		
		createEntity();
	}

}
