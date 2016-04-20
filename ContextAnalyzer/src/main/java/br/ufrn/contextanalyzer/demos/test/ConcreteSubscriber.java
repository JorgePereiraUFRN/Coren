package br.ufrn.contextanalyzer.demos.test;

public class ConcreteSubscriber extends AbstractSubscriber{

	public ConcreteSubscriber(String topic, String uriHub, String ipAddress, Integer port) {
		super(topic, uriHub, ipAddress, port);
		// TODO Auto-generated constructor stub
	}
	
	public ConcreteSubscriber(String topic, String uriHub, String ipAddress) {
		this(topic, uriHub, ipAddress, null);
		// TODO Auto-generated constructor stub
	}

	public void TreatMessage(String message) {
		System.out.println("nofification recivied: "+message);
		
	}
	

}
