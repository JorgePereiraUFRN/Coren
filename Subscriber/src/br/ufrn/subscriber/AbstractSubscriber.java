/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.subscriber;

import br.ufrn.exceptions.ComunicationException;
import br.ufrn.exceptions.TopicDoesNotExistException;
import br.ufrn.model.SubscribeBean;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.Random;


public abstract class AbstractSubscriber implements TreatMessage {

    protected SubscribeBean subscribe = new SubscribeBean();
    private Client client = Client.create();
    private WaitUpdates waitUpdates;
    private String uriHub;
/*
    classe responsavel por se realizar a subcrição no hub e cancelar a subsbrição
    recebe como parametro no contrutor o identificador do topico no qual deseja se subscrever
    a url do hub e o endereço ip da máquina no qual o subscriber está sendo executado
    */
    public AbstractSubscriber(String topic, String uriHub, String ipAddress) {
        subscribe.setTopic(topic);
        subscribe.setPort(new Random().nextInt(60000) + 1025);
        subscribe.setAddress(ipAddress);
        this.uriHub = uriHub;
    }

    /*método resposável por solicitar a subscrição em um tópico ao hub, o identificador do tópico no qual
    será subscrito é aquele que recebeu por parametro no construtor mda classe. Este método pode levatar
    duas exceções TopicDoesNotExistException, caso o tópico no qual se deseja subscrever não exista e
    ComunicationException, caso ocorram problemas na comunicação entre o subscribe e o hub */
    
    public void subscribe() throws ComunicationException, TopicDoesNotExistException {
        String response;
        try {
            WebResource resource = client.resource(uriHub + "/subscribe");
            response = resource.type("application/json").put(String.class, subscribe);
        } catch (Exception e) {
            throw new ComunicationException(e.getMessage());
        }
        if (response.equalsIgnoreCase("success")) {
            WaitUpdates();
        } else {
            throw new TopicDoesNotExistException(response);
        }

    }
   /* 
    método responsável por solicitar ao hub o cancelamento de sua subscrição. Este métod pode levantar
    a exceção ComunicationException, caso ocorram problemas na comunicação entre o subscribe e o hub
*/
    public void unsubscribe() throws ComunicationException {
        try {
            WebResource resource = client.resource(uriHub + "/unsubscribe/?address=" + subscribe.getAddress()
                    + "&idTopic=" + subscribe.getTopic() + "&port=" + String.valueOf(subscribe.getPort()));
            resource.delete();
        } catch (Exception e) {
            throw new ComunicationException(e.getMessage());
        }
        waitUpdates.finalize();
    }

    /*método que cria um objeto do tipo WaitUpdates, este objeto aguardará pelas notificações e as enviará
    para o subscriber no momento em que recebê-las
    */
    private void WaitUpdates() {
        waitUpdates = new WaitUpdates(subscribe.getPort(), this);
        waitUpdates.start();
    }
}
