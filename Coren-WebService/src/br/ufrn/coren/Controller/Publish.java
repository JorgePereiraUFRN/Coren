package br.ufrn.coren.Controller;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.coren.Models.Topic;

public class Publish {

	private static Client client = Client.create();
	
	public void publishInTopic(String idTopic, String message){
		WebResource resource = client.resource("http://localhost:8080/hub/hub/publish/"+idTopic);
		
		resource.put(message);
		
	}
	
	public void createTopic(Topic topic){
		WebResource resource = client.resource("http://localhost:8080/hub/hub/create-topic");
		
		resource.type(MediaType.APPLICATION_JSON).put(topic);
	}
}
