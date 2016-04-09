package br.ufrn.subscriber;

public class ConcreteSubscriber extends AbstractSubscriber{

	public ConcreteSubscriber(String topic, String uriHub, String ipAddress) {
		super(topic, uriHub, ipAddress);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void TreatMessage(String message) {
		System.out.println("nofification recivied: "+message);
		
	}
	

}
