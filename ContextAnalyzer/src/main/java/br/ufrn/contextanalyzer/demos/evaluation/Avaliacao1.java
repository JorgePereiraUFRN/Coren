package br.ufrn.contextanalyzer.demos.evaluation;

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
	private int numberThreads;
	private int numberLoopAttributes;
	private Random random;
	private String ipServer;
	
	public Avaliacao1(int numberWidgets, int numberAttributes, String ipServer) {
		this.numberAttributes = numberAttributes;
		this.numberWidgets = numberWidgets;
		this.ipServer = ipServer;
		this.random = new Random();
	}
	
	public Avaliacao1(int numberWidgets, int numberAttributes, int numberThreads, int numberLoopAttributes, String ipServer) {
		this.numberAttributes = numberAttributes;
		this.numberWidgets = numberWidgets;
		this.ipServer = ipServer;
		this.numberLoopAttributes = numberLoopAttributes;
		this.numberThreads = numberThreads;
		this.random = new Random();
	}
	
	public static void main(String[] args) {
		
//		int numberWidgets = Integer.valueOf(args[0]);
//		int numberAttributes = Integer.valueOf(args[1]);
//		String ipServer = args[2];
//		int numberThreads = Integer.valueOf(args[3]);
//		int numberLoopAttributes = Integer.valueOf(args[4]);
		
		int numberWidgets = 20;
		int numberAttributes = 5;
		String ipServer = "192.168.0.100";
		int numberThreads = 1000;
		int numberLoopAttributes = 10;
		
//		Avaliacao1 av = new Avaliacao1(numberWidgets, numberAttributes, ipServer);
		Avaliacao1 av = new Avaliacao1(numberWidgets, numberAttributes, numberThreads, numberLoopAttributes, ipServer);
		av.createWidgets();
		
		try{
			Thread.sleep(3000);
		} catch(Exception e) {}
		
		av.updateRandomWidgets();
		
	}
	
	public void createWidgets() {
		Client client = Client.create();
		
		WebResource resource = client.resource("http://" + ipServer + ":8080/ContextAnalyzer/api/widget/create");
		
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

	public void updateAllWidgets() {
		for(WidgetEntity widget : widgets) {
			Thread t = new Thread(new UpdateAllAttributeWidget(widget));
			t.start();
		}
	}
	
	private class UpdateAllAttributeWidget implements Runnable {

		private WidgetEntity widget;
		
		public UpdateAllAttributeWidget(WidgetEntity widget) {
			this.widget = widget;
		}
		
		public void run() {
			Client client = Client.create();		
			WebResource resource = client.resource("http://" + ipServer + ":8080/ContextAnalyzer/api/widget/update/" + widget.getName());
			for (AttributeEntity att : widget.getAttributes()) {
				att.setValue(String.valueOf(random.nextInt(100)));
				resource.put(att);
			}
		}

	}
	
	public void updateRandomWidgets() {
		for(int i = 0; i < numberThreads; i++) {
			Thread t = new Thread(new UpdateRandomWidget());
			t.start();
		}
	}
	
	private class UpdateRandomWidget implements Runnable {
		
		public void run() {
			Client client = Client.create();
			
			int indexWidget = random.nextInt(numberWidgets);
			
			WidgetEntity widget = widgets.get(indexWidget);
			WebResource resource = client.resource("http://" + ipServer + ":8080/ContextAnalyzer/api/widget/update/" + widget.getName());
			
			for(int i = 0; i < numberLoopAttributes; i++) {
				int indexAttribute = random.nextInt(numberAttributes);
				AttributeEntity att = widget.getAttributes().get(indexAttribute);
				att.setValue(String.valueOf(random.nextInt(100)));
				Long inicio = System.currentTimeMillis();
				resource.put(att);
				Long fim = System.currentTimeMillis();
				System.out.println(fim - inicio);
			}
		}

	}

}
