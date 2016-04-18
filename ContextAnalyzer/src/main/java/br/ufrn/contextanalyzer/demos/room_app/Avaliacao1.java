package br.ufrn.contextanalyzer.demos.room_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;

public class Avaliacao1 {

	private List<WidgetEntity> widgets = new ArrayList<WidgetEntity>();

	private int numberWidgets;
	private int numberAttributes;
	
	public Avaliacao1(int numberWidgets, int numberAttributes) {
		this.numberAttributes = numberAttributes;
		this.numberWidgets = numberWidgets;
	}
	
	public static void main(String[] args) {
		
//		int numberWidgets = Integer.valueOf(args[0]);
//		int numberAttributes = Integer.valueOf(args[1]);
		
		int numberWidgets = 10;
		int numberAttributes = 5;
		
		Avaliacao1 av1 = new Avaliacao1(numberWidgets, numberAttributes);
		av1.createWidgets();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		av1.updateWidgets();
		
	}
	
	public void createWidgets() {
		Client client = Client.create();		
		WebResource resource = client.resource("http://localhost:8080/ContextAnalyzer/api/widget/create");
		
		for(int i = 1; i <= numberWidgets; i++) {
			List<AttributeEntity> atts = new ArrayList<AttributeEntity>();
			for(int j = 1; j <= numberAttributes; j++) {
				AttributeEntity attribute = new AttributeEntity();
				attribute.setType("int");
				attribute.setName("attribute"+j);
				atts.add(attribute);
			}
			WidgetEntity widget = new WidgetEntity();
			widget.setName("sensor" + i);
			widget.setAttributes(atts);
			
			String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget);
			if (message.equals("created")) {
				widgets.add(widget);
				System.out.println("Widget " + i + " " + message);
			} else {
				System.out.println("Error: Widget " + i + " " + message);
			}
		}
	}
	
	public void updateWidgets() {
		for(int i = 0; i < numberWidgets; i++) {
			Thread t = new Thread(new UpdateWidget(widgets.get(i)));
			t.start();
		}
	}
	
	private class UpdateWidget implements Runnable {

		private WidgetEntity widget;
		private Random random;
		
		public UpdateWidget(WidgetEntity widget) {
			this.widget = widget;
			this.random = new Random(0);
		}
		
		public void run() {
			Client client = Client.create();		
			WebResource resource = client.resource("http://localhost:8080/ContextAnalyzer/api/widget/update/" + widget.getName());
			for (AttributeEntity att : widget.getAttributes()) {
				att.setValue(String.valueOf(random.nextInt(100)));
				resource.put(att);
			}
			
		}

	}
	
	

}
