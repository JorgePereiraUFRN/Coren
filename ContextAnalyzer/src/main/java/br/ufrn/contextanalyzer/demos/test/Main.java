package br.ufrn.contextanalyzer.demos.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.api.entities.OutcomeEntity;
import br.ufrn.contextanalyzer.api.entities.QueryEntity;
import br.ufrn.contextanalyzer.api.entities.ReferenceEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;

public class Main {
	
	private static final Client client = Client.create();
//	private static final String SERVER_IP = "192.168.0.100";
//	private static final String CALLBACK_IP = "192.168.0.101";
	private static final String SERVER_IP = "localhost";
	private static final String CALLBACK_IP = "127.0.0.1";
	
	public static void createWidget() {
		
		WebResource resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/widget/create");

		List<AttributeEntity> atts1 = new ArrayList<AttributeEntity>();		
		AttributeEntity presenca = new AttributeEntity();
		presenca.setType("int");
		presenca.setName("presence");
		atts1.add(presenca);
		WidgetEntity widget1 = new WidgetEntity();
		widget1.setName("PresenceWidget");
		widget1.setAttributes(atts1);
		
		String message1 = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget1);
		System.out.println(message1);
		
		List<AttributeEntity> atts2 = new ArrayList<AttributeEntity>();		
		AttributeEntity brightness = new AttributeEntity();
		brightness.setType("int");
		brightness.setName("brightness");
		atts2.add(brightness);
		WidgetEntity widget2 = new WidgetEntity();
		widget2.setName("BrightnessWidget");
		widget2.setAttributes(atts2);
		
		String message2 = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget2);
		System.out.println(message2);

	}
	
	public static void createEnactor(){
		
		WebResource resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/enactor/create");
		
		EnactorEntity enactor = new EnactorEntity();
		enactor.setName("RoomEnactor");
		List<String> widgets = new ArrayList<String>();
		widgets.add("PresenceWidget");
		widgets.add("BrightnessWidget");
		enactor.setWidgets(widgets);
		OutcomeEntity outcome = new OutcomeEntity();
		outcome.setName("light");
		outcome.setType("string");
		outcome.setDescription("lampada da sala");
		enactor.setOutcome(outcome);
		List<ReferenceEntity> references = new ArrayList<ReferenceEntity>();
		ReferenceEntity off = new ReferenceEntity();
		off.setName("Off");
		OutcomeEntity outcomeOff = new OutcomeEntity();
		outcomeOff.setName("light");
		outcomeOff.setType("string");
		outcomeOff.setDescription("lampada da sala");
		outcomeOff.setValue("0");
		off.setOutcome(outcomeOff);
		QueryEntity lightOff = new QueryEntity();
		lightOff.setNome("lightOff");
		lightOff.setValue("(OR (EQUAL presence 0) (GREATER brightness 100) )");
		off.setQuery(lightOff);
		references.add(off);
		ReferenceEntity on = new ReferenceEntity();
		on.setName("On");
		OutcomeEntity outcomeOn = new OutcomeEntity();
		outcomeOn.setName("light");
		outcomeOn.setType("string");
		outcomeOn.setDescription("lampada da sala");
		outcomeOn.setValue("1");
		on.setOutcome(outcomeOn);
		QueryEntity lightOn = new QueryEntity();
		lightOn.setNome("lightOn");
		lightOn.setValue("(ELSE (QUERY lightOff) )");
		on.setQuery(lightOn);
		references.add(on);
		enactor.setReferences(references);
		
		String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, enactor);
		
		System.out.println(message);
	}
	
	public static void updateWidget() {
		Random r = new Random(0);
		while(true) {
			if(r.nextBoolean()) {
				WebResource resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/PresenceWidget");
				AttributeEntity att = new AttributeEntity();
				att.setName("presence");
				att.setValue(String.valueOf(r.nextInt(2)));
				att.setType("int");
				resource.put(att);
			} else {
				WebResource resource = client.resource("http://" + SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/BrightnessWidget");
				AttributeEntity att = new AttributeEntity();
				att.setName("brightness");
				att.setValue(String.valueOf(r.nextInt(200)));
				att.setType("int");
				resource.put(att);
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
					
		}
		
	}

	public static void subscribe() throws ComunicationException, TopicDoesNotExistException {
		ConcreteSubscriber subscriber = new ConcreteSubscriber("4light_4light", "http://" + SERVER_IP + ":8080/ContextAnalyzer/hub/topic", CALLBACK_IP );
		subscriber.subscribe();
	}
	
	public static void main(String[] args) {
//		createWidget();
//		createEnactor();
		Thread t = new Thread() {
			public void run() {
				try {
					subscribe();
				} catch (ComunicationException e) {
					e.printStackTrace();
				} catch (TopicDoesNotExistException e) {
					e.printStackTrace();
				}
		    }
		};
		t.start();
//		updateWidget();
	}

}
