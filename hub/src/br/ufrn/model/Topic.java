package br.ufrn.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Topic {
	
	private String idTopic;
	private String topicDescription;
	
	
	public String getIdTopic() {
		return idTopic;
	}
	public void setIdTopic(String idTopic) {
		this.idTopic = idTopic;
	}
	public String getTopicDescription() {
		return topicDescription;
	}
	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}
	
	

}
