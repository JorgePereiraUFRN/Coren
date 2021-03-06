package br.ufrn.contextanalyzer.demos.evaluation;

import java.io.IOException;
import java.net.ServerSocket;

import br.ufrn.contextanalyzer.demos.test.ComunicationException;
import br.ufrn.contextanalyzer.demos.test.ConcreteSubscriber;
import br.ufrn.contextanalyzer.demos.test.TopicDoesNotExistException;

public class Avaliacao2_Passo2_InformationConsumer {
	
	private static final String CALLBACK_IP = "192.168.0.101";
	private static final String SERVER_IP = "192.168.0.100";
//	private static final String CALLBACK_IP = "127.0.0.1";
//	private static final String SERVER_IP = "localhost";
	private static final int LOOP = 500;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		for(int i = 0; i < LOOP; i++) {
			ServerSocket s = new ServerSocket(0);
			final int port = s.getLocalPort();
			Thread t = new Thread() {
				public void run() {
					try {
						ConcreteSubscriber subscriber = new ConcreteSubscriber("attribute_out_attribute_out", "http://" + SERVER_IP + ":8080/ContextAnalyzer/hub/topic", CALLBACK_IP, port );
						subscriber.subscribe();
					} catch (ComunicationException e) {
						e.printStackTrace();
					} catch (TopicDoesNotExistException e) {
						e.printStackTrace();
					}
			    }
			};
			t.start();
			s.close();
			
			Thread.sleep(300);
			
		}		
	}

}
