package br.ufrn.contextanalyzer.demos.evaluation;

import java.util.Random;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;

public class Avaliacao2_Passo3_InformationProvider_Update {
	
	private static final String SERVER_IP = "192.168.0.100";
//	private static final String SERVER_IP = "localhost";
	private static final int LOOP = 1;
	
	public static void main(String[] args) {
		
		Client client = Client.create();
	
		Random r = new Random(0);
		for(int i = 0; i < LOOP; i++) {
			WebResource resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/Sensor");
			AttributeEntity att = new AttributeEntity();
			att.setName("attribute_in");
			att.setValue(String.valueOf(r.nextInt(100)));
			att.setType("int");
			resource.put(att);		
		}
		
	}
	
	

}
