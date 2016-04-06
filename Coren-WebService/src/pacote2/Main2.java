package pacote2;

import context.arch.discoverer.Discoverer;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.Enactor;
import context.arch.service.Service;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;

public class Main2 {

	public static void main(String[] args) {
		Discoverer.start();

		Widget roomWidget = new RoomWidget("Sala");
		
		Widget lightWidget = new LightWidget("Sala");
		Service lightService = new LightService(lightWidget);
		lightWidget.addService(lightService);
		
		AbstractQueryItem<?,?> roomWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(roomWidget);
		AbstractQueryItem<?,?> lightWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(lightWidget);

		Enactor roomEnactor = new RoomEnactor(roomWidgetQuery, lightWidgetQuery);

		try {
			Thread.sleep(3000);
			roomWidget.updateData(RoomWidget.PRESENCE, 2);
			Thread.sleep(3000);
			roomWidget.updateData(RoomWidget.BRIGHTNESS,(short)80);
			Thread.sleep(3000);
			roomWidget.updateData(RoomWidget.PRESENCE, 0);
			Thread.sleep(3000);
			roomWidget.updateData(RoomWidget.BRIGHTNESS,(short)60);
			Thread.sleep(3000);
			roomWidget.updateData(RoomWidget.PRESENCE, 1);
			Thread.sleep(3000);
			roomWidget.updateData(RoomWidget.BRIGHTNESS,(short)50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
