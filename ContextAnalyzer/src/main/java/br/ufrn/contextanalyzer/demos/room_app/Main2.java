package br.ufrn.contextanalyzer.demos.room_app;

import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntityHistory;

public class Main2 {

	public static void main(String[] args) {
		
//		WidgetEntity widget = new WidgetEntity();
//		widget.setName("WidgetTeste");
//		List<AttributeEntity> atts = new ArrayList<AttributeEntity>();
//		AttributeEntity att = new AttributeEntity();
//		att.setName("presence");
//		att.setType("int");
//		atts.add(att);
//		widget.setAttributes(atts);
//		DAOFactory.getFactory().getWidgetEntityDAO().save(widget);
//
//		
//		EnactorEntity enactor = new EnactorEntity();
//		enactor.setName("RoomEnactor");
//		List<String> widgets = new ArrayList<String>();
//		widgets.add("PresenceWidget");
//		widgets.add("BrightnessWidget");
//		enactor.setWidgets(widgets);
//		OutcomeEntity outcome = new OutcomeEntity();
//		outcome.setName("light");
//		outcome.setType("string");
//		outcome.setDescription("lampada da sala");
//		enactor.setOutcome(outcome);
//		List<ReferenceEntity> references = new ArrayList<ReferenceEntity>();
//		ReferenceEntity off = new ReferenceEntity();
//		off.setName("Off");
//		OutcomeEntity outcomeOff = new OutcomeEntity();
//		outcomeOff.setName("light");
//		outcomeOff.setType("string");
//		outcomeOff.setDescription("lampada da sala");
//		outcomeOff.setValue("0");
//		off.setOutcome(outcomeOff);
//		QueryEntity lightOff = new QueryEntity();
//		lightOff.setNome("lightOff");
//		lightOff.setValue("(OR (EQUAL presence 0) (GREATER brightness 100) )");
//		off.setQuery(lightOff);
//		references.add(off);
//		ReferenceEntity on = new ReferenceEntity();
//		on.setName("On");
//		OutcomeEntity outcomeOn = new OutcomeEntity();
//		outcomeOn.setName("light");
//		outcomeOn.setType("string");
//		outcomeOn.setDescription("lampada da sala");
//		outcomeOn.setValue("1");
//		on.setOutcome(outcomeOn);
//		QueryEntity lightOn = new QueryEntity();
//		lightOn.setNome("lightOn");
//		lightOn.setValue("(ELSE (QUERY lightOff) )");
//		on.setQuery(lightOn);
//		references.add(on);
//		enactor.setReferences(references);
//		
//		DAOFactory.getFactory().getEnactorEntityDAO().save(enactor);
		
//		List<AttributeEntityHistory> atts = DAOFactory.getFactory().getWidgetEntityDAO().getLastUpdatesAttribute("PresenceWidget", "presence");
//		for (AttributeEntityHistory att : atts) {
//			System.out.println(att.getValue());
//		}
		
		Client client = Client.create();
		
		WebResource resource = client.resource("http://localhost:8080/ContextAnalyzer/api/widget/PresenceWidget/last_updates/presence");

		List<AttributeEntityHistory> atts = (List<AttributeEntityHistory>) resource.get(List.class);
		for (AttributeEntityHistory att : atts) {
			System.out.println(att.getValue());
		}
	}

}
