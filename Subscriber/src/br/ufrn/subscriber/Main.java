package br.ufrn.subscriber;

import br.ufrn.exceptions.ComunicationException;
import br.ufrn.exceptions.TopicDoesNotExistException;

public class Main {

	public static void main(String[] args) throws ComunicationException, TopicDoesNotExistException {
		
		
		ConcreteSubscriber subscriber = new ConcreteSubscriber("presence-room1", "http://localhost:8080/hub/hub","127.0.0.1");
		
		subscriber.subscribe();

	}

}
