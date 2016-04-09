package Coren;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.coren.Models.AttributeModel;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.OutcomeModel;
import br.ufrn.coren.Models.QueryModel;
import br.ufrn.coren.Models.ReferenceModel;
import br.ufrn.coren.Models.WidgetModel;
import context.arch.discoverer.Discoverer;
import context.arch.enactor.Enactor;
import context.arch.widget.Widget;

public class testRoomApp {

	public static void main(String[] args) {
		Discoverer.start();
		
		List<AttributeModel> atts = new ArrayList<AttributeModel>();
		AttributeModel temperatura = new AttributeModel();
		temperatura.setType("int");
		temperatura.setName("temperatura");
		atts.add(temperatura);
		AttributeModel presenca = new AttributeModel();
		presenca.setType("int");
		presenca.setName("presenca");
		atts.add(presenca);
		WidgetModel widget = new WidgetModel();
		widget.setName("RoomWidget");
		widget.setAttributes(atts);
		
		Widget roomWidget = widget.createWidget();
		
		EnactorModel enactor = new EnactorModel();
		enactor.setName("RoomEnactor");
		enactor.setInputWidget(widget);
		OutcomeModel outcome = new OutcomeModel();
		outcome.setName("lampada");
		outcome.setType("string");
		outcome.setDescription("lampada da sala");
		enactor.setOutcome(outcome);
		List<ReferenceModel> references = new ArrayList<ReferenceModel>();
		ReferenceModel off = new ReferenceModel();
		off.setName("Off");
		OutcomeModel outcomeOff = new OutcomeModel();
		outcomeOff.setName("lampada");
		outcomeOff.setType("string");
		outcomeOff.setDescription("lampada da sala");
		outcomeOff.setValue("0");
		off.setOutcome(outcomeOff);
		QueryModel lightOff = new QueryModel();
		lightOff.setNome("lightOff");
		lightOff.setValue("(OR (EQUAL presenca 0) (GREATER temperatura 25) )");
		off.setQuery(lightOff);
		references.add(off);
		ReferenceModel on = new ReferenceModel();
		on.setName("On");
		OutcomeModel outcomeOn = new OutcomeModel();
		outcomeOn.setName("lampada");
		outcomeOn.setType("string");
		outcomeOn.setDescription("lampada da sala");
		outcomeOn.setValue("1");
		on.setOutcome(outcomeOn);
		QueryModel lightOn = new QueryModel();
		lightOn.setNome("lightOn");
		lightOn.setValue("(ELSE (QUERY lightOff) )");
		on.setQuery(lightOn);
		references.add(on);
		enactor.setReference(references);
		
		Enactor appTemperatura = enactor.createEnactor(); 
		
		try {
			roomWidget.updateData("temperatura", 0);
			roomWidget.updateData("presenca", 0);
			Thread.sleep(2000);
			roomWidget.updateData("presenca", 1);
			Thread.sleep(2000);
			roomWidget.updateData("temperatura", 30);
			Thread.sleep(2000);
			roomWidget.updateData("temperatura", 20);
			Thread.sleep(2000);
			roomWidget.updateData("temperatura", 30);
			Thread.sleep(2000);
			roomWidget.updateData("presenca", 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
