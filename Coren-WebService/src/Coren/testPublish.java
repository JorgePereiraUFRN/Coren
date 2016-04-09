package Coren;

import br.ufrn.coren.Controller.Publish;
import br.ufrn.coren.Models.Topic;

public class testPublish {

	public static void main(String[] args) {
		
		Publish publish = new Publish();
		
		Topic t = new Topic();
		
		t.setIdTopic("presence-room1");
		t.setTopicDescription("true = has peoples in room1, false = has not peoples in room 1");
		
		publish.createTopic(t);
		
		publish.publishInTopic("presence-room1", "true");
		
		// para verificar os topicos criados ponha no browser http://localhost:8080/hub/hub/list-topics

	}

}
