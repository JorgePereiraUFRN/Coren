package br.ufrn.contextanalyzer.demos.room_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;

public class Avaliacao2 {

	private List<WidgetEntity> widgets = new ArrayList<WidgetEntity>();

	private int numberWidgets;
	private int numberAttributes;
	private Random random;
	private String ipServer;
	
	public Avaliacao2(int numberWidgets, int numberAttributes, String ipServer) {
		this.numberAttributes = numberAttributes;
		this.numberWidgets = numberWidgets;
		this.ipServer = ipServer;
		this.random = new Random();
	}
	
	public static void main(String[] args) {
		
//		int numberWidgets = Integer.valueOf(args[0]);
//		int numberAttributes = Integer.valueOf(args[1]);
		
		int numberWidgets = 5;
		int numberAttributes = 5;
		String ipServer = "192.168.0.100";
		
		Avaliacao2 av = new Avaliacao2(numberWidgets, numberAttributes, ipServer);
		av.createWidgets();
		
		try{
			Thread.sleep(3000);
		} catch(Exception e) {}
		
		av.updateWidgets();
		
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
			
			Long inicio = System.currentTimeMillis();
			String message = resource.type(MediaType.APPLICATION_JSON).post(String.class, widget);
			Long fim = System.currentTimeMillis();
			
			if (message.equals("created")) {
				widgets.add(widget);
				System.out.println("Widget " + i + " " + message);
			} else {
				System.out.println("Error: Widget " + i + " " + message);
			}
		}
	}
	
	public void updateWidgets() {
		Executor exec = Executors.newFixedThreadPool(1000);
		for(int i = 0; i < 10000; i++) {
			exec.execute(new UpdateWidget2());
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
			WebResource resource = client.resource("http://" + ipServer + ":8080/ContextAnalyzer/api/widget/update/" + widget.getName());
			for (AttributeEntity att : widget.getAttributes()) {
				att.setValue(String.valueOf(random.nextInt(100)));
				resource.put(att);
			}
			
		}

	}
	
	private class UpdateWidget2 implements Runnable {
		
		public void run() {
			Client client = Client.create();
			
			int indexWidget = random.nextInt(numberWidgets);
			int indexAttribute = random.nextInt(numberAttributes);
			
			WidgetEntity widget = widgets.get(indexWidget);
			WebResource resource = client.resource("http://" + ipServer + ":8080/ContextAnalyzer/api/widget/update/" + widget.getName());
			
			AttributeEntity att = widget.getAttributes().get(indexAttribute);
			att.setValue(String.valueOf(random.nextInt(100)));
			Long inicio = System.currentTimeMillis();
			resource.put(att);
			Long fim = System.currentTimeMillis();
			System.out.println(fim - inicio);
			
		}

	}

}
