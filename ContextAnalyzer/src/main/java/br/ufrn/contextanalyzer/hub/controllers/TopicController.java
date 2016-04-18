/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.contextanalyzer.hub.controllers;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrn.contextanalyzer.hub.entities.SubscribeEntity;
import br.ufrn.contextanalyzer.hub.entities.TopicEntity;
import br.ufrn.contextanalyzer.hub.exceptions.SubscribeAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.SubscribeNotFoundException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;

@Path("topic")
public class TopicController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	public TopicEntity[] getAllTopics() {
		TopicEntity[] list = null;
		List<TopicEntity> temp = HubFacade.getAllTopics(); 
		list = temp.toArray(new TopicEntity[temp.size()]);
		return list;
	}

//	/*
//	 * metodo usado para publicar atualiza��es, para isto p publish deve passar
//	 * o identificador do topico e a atualiza��o que ser� enviada para os
//	 * subscriber
//	 */
//	@PUT
//	@Path("{topic}/publish")
//	public String publish(@PathParam("topic") String topic, String value) {
//		try {
//			HubFacade.publish(topic, value);
//		} catch (TopicNotFoundException e) {
//			e.printStackTrace();
//			return "error: " + e.getMessage();
//		}
//		return "published";
//	}
//
//	/*
//	 * m�todo usado para registrar um t�pico no hub, para isto o publish deve
//	 * enviar o identificador do topico. Caso o topico n�o exista ele
//	 * ser�cadadtrado e retornara a string sucess, caso o topico ja esteja
//	 * cadastrado ele retornar� "the topic "+idTopic+" already exists"
//	 */
//	@POST
//	@Path("register")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String register(TopicEntity topicEntity) {
//		try {
//			HubFacade.register(topicEntity);
//		} catch (TopicAlreadyRegistredException e) {
//			e.printStackTrace();
//			return "error: " + e.getMessage();
//		}
//		return "registered";
//	}

	/*
	 * metodo usado pelo subscriber para registrar interesse em receber
	 * atualiza��es acerca de um topico, para isso ele deve enviar um objeto do
	 * tipo subscriberbean que contem seu endere�o ip, a porta na quala
	 * aguardara pelas atualiza��es e o topico de interesse
	 */
	@POST
	@Path("{topic}/subscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String subscribe(@PathParam("topic") String topic, SubscribeEntity subscribeEntity) {
		try {
			HubFacade.subscribe(topic, subscribeEntity);
		} catch (TopicNotFoundException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		} catch (SubscribeAlreadyRegistredException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		return "subscribed";
	}

	/*
	 * metodo usado pelo subscribe para cancelar o interesse em receber
	 * atualiza��es a cerca de um t�pico, para isso ele deve enviar um objeto do
	 * tipo subscriberbean que contem seu endere�o ip, a porta na quala aguardar
	 * pelas atualiza��es e o topico de interesse
	 */
	@DELETE
	@Path("{topic}/unsubscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	public String unsubscribe(@PathParam("topic") String topic, SubscribeEntity subscribeEntity) {
		try {
			HubFacade.unsubscribe(topic, subscribeEntity);
		} catch (SubscribeNotFoundException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		} catch (TopicNotFoundException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		
		return "unsubscribed";
	}
}
