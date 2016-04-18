package br.ufrn.contextanalyzer.hub.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="topic")
@Entity
@Table(name="TOPIC")
@SequenceGenerator(name="TOPIC_SEQUENCE", sequenceName="TOPIC_SEQUENCE", allocationSize=1, initialValue=0)
public class TopicEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOPIC_SEQUENCE")
	private long id;
	
	private String name;
	
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="TOPIC_ID")
	private List<SubscribeEntity> subscribes;
	
	public TopicEntity() {};
	public TopicEntity(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	@XmlTransient
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlTransient
	public List<SubscribeEntity> getSubscribes() {
		return subscribes;
	}
	
	public void setSubscribes(List<SubscribeEntity> subscribes) {
		this.subscribes = subscribes;
	}
	
}
