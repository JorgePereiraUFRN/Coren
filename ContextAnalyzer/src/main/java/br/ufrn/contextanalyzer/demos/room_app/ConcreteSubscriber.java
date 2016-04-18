package br.ufrn.contextanalyzer.demos.room_app;

public class ConcreteSubscriber extends AbstractSubscriber{

	public ConcreteSubscriber(String topic, String uriHub, String ipAddress) {
		super(topic, uriHub, ipAddress);
		// TODO Auto-generated constructor stub
	}

	public void TreatMessage(String message) {
		System.out.println("nofification recivied: "+message);
		
	}
	

}
