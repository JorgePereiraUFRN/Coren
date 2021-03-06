/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.hub.resource;


import br.ufrn.hub.processors.NotifySubscribes;
import br.ufrn.model.SubscribeBean;
import br.ufrn.model.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hub")
public class HubResource {

	public HubResource() {
	}

	private static final Map<String, List<SubscribeBean>> subscribers = Collections
			.synchronizedMap(new HashMap<String, List<SubscribeBean>>());

	private static final Map<String, Topic> topics = Collections.synchronizedMap(new HashMap<String, Topic>());
	
	/*
	 * metodo usado para publicar atualiza��es, para isto p publish deve passar
	 * o identificador do topico e a atualiza��o que ser� enviada para os
	 * subscriber
	 */


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list-topics")
	public Topic[] getAllTopics() {
		return topics.values().toArray(new Topic[topics.values().size()]);
	}

	@PUT
	@Path("publish/{idTopic}")
	public void publish(@PathParam("idTopic") String idTopic, String valueTopic) {

		if (subscribers.get(idTopic) != null) {
			new NotifySubscribes(subscribers.get(idTopic).iterator(),
					valueTopic, idTopic).notifySubscribers();
		}

	}

	/*
	 * m�todo usado para registrar um t�pico no hub, para isto o publish deve
	 * enviar o identificador do topico. Caso o topico n�o exista ele
	 * ser�cadadtrado e retornara a string sucess, caso o topico ja esteja
	 * cadastrado ele retornar� "the topic "+idTopic+" already exists"
	 */

	@PUT
	@Path("create-topic")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(Topic topic) {

		if (subscribers.get(topic.getIdTopic()) == null) {
			subscribers.put(topic.getIdTopic(), new ArrayList<SubscribeBean>());
			topics.put(topic.getIdTopic(), topic);
			return "sucess";
		} else {
			return "the topic " + topic.getIdTopic()+ " already exists";
		}

	}

	/*
	 * metodo usado pelo subscriber para registrar interesse em receber
	 * atualiza��es acerca de um topico, para isso ele deve enviar um objeto do
	 * tipo subscriberbean que contem seu endere�o ip, a porta na quala
	 * aguardara pelas atualiza��es e o topico de interesse
	 */

	@PUT
	@Path("subscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String subscribe(SubscribeBean subscribe)
			throws InterruptedException {

		if (subscribers.get(subscribe.getTopic()) != null) {
			subscribers.get(subscribe.getTopic()).add(subscribe);
			return "success";
		} else {
			return "topic " + subscribe.getTopic() + " not exists";
		}

	}

	/*
	 * metodo usado pelo subscribe para cancelar o interesse em receber
	 * atualiza��es a cerca de um t�pico, para isso ele deve enviar um objeto do
	 * tipo subscriberbean que contem seu endere�o ip, a porta na quala aguardar
	 * pelas atualiza��es e o topico de interesse
	 */

	@DELETE
	@Path("/unsubscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	public void unsubscribe(@PathParam("address") String address,
			@PathParam("idTopic") String topic, @PathParam("port") int port) {

		if (subscribers.get(topic) != null) {

			for (int i = 0; i < subscribers.get(topic).size(); i++) {

				SubscribeBean s = subscribers.get(topic).get(i);

				if (address.equals(s.getAddress()) && port == s.getPort()
						&& topic.equals(s.getTopic())) {

					subscribers.get(topic).remove(i);

				}

			}
		}

	}
}
