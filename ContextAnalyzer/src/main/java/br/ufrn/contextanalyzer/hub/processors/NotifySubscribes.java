package br.ufrn.contextanalyzer.hub.processors;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrn.contextanalyzer.hub.entities.SubscribeEntity;


public class NotifySubscribes {

    private Iterator<SubscribeEntity> subscribes;
    private String notification;

    /*classe respons�vel por enviar as atualiza��es para os susbcritos em algum t�pico
    , recebe um iterator contendo todos os subscribers q recebr�o as atualiza��es
    o valor da atualiza��o e o t�pico*/
    
    public NotifySubscribes(Iterator<SubscribeEntity> subscribes, String notification) {
        this.subscribes = subscribes;
        this.notification = notification;
    }
    
   /* envia a atualiza��o para todos os subscritos em no t�pico */
    @SuppressWarnings("resource")
	public void notifySubscribers() {
        while (subscribes.hasNext()) {
            SubscribeEntity subscribe = subscribes.next();
            try {
                Socket socket = new Socket(subscribe.getAddress(), subscribe.getPort());
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(notification);
            } catch (UnknownHostException ex) {
                Logger.getLogger(NotifySubscribes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NotifySubscribes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
