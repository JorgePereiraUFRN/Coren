package Coren;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.coren.Models.AttributeModel;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.OutcomeModel;
import br.ufrn.coren.Models.QueryModel;
import br.ufrn.coren.Models.ReferenceModel;
import br.ufrn.coren.Models.WidgetModel;
import br.ufrn.coren.RestApi.CorenAPI;
import context.arch.discoverer.Discoverer;

public class testRoomApp {

	public static void main(String[] args) {
		CorenAPI api = new CorenAPI();
		Discoverer.start();
		
		List<AttributeModel> atts1 = new ArrayList<AttributeModel>();
		AttributeModel presence = new AttributeModel();
		presence.setType("int");
		presence.setName("presence");
		atts1.add(presence);
		WidgetModel widget1 = new WidgetModel();
		widget1.setName("PresenceWidget");
		widget1.setAttributes(atts1);
		api.createWidget(widget1);
		
		List<AttributeModel> atts2 = new ArrayList<AttributeModel>();
		AttributeModel brightness = new AttributeModel();
		brightness.setType("int");
		brightness.setName("brightness");
		atts2.add(brightness);
		WidgetModel widget2 = new WidgetModel();
		widget2.setName("BrightnessWidget");
		widget2.setAttributes(atts2);
		api.createWidget(widget2);
		
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
		
		api.createEnactor(enactor); 
		
		try {
			Thread.sleep(2000);
			api.updateWidget("PresenceWidget", "presence", 0);
			Thread.sleep(2000);
			api.updateWidget("BrightnessWidget", "brightness", 0);
			Thread.sleep(2000);
			api.updateWidget("PresenceWidget", "presence", 1);
			Thread.sleep(2000);
			api.updateWidget("BrightnessWidget", "brightness", 20);
			Thread.sleep(2000);
			api.updateWidget("BrightnessWidget", "brightness", 30);
		} catch (Exception e) {
			
		}
		
		

	}

}
