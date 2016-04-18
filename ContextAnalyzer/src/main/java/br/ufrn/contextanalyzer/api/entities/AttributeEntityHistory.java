package br.ufrn.contextanalyzer.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="attributeHistory")
@Entity
@Table(name="ATTRIBUTE_HISTORY")
@SequenceGenerator(name="ATTRIBUTE_HISTORY_SEQUENCE", sequenceName="ATTRIBUTE_HISTORY_SEQUENCE", allocationSize=1, initialValue=0)
public class AttributeEntityHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRIBUTE_HISTORY_SEQUENCE")
	private Long id;
	
	private String value;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at")
	private Date updateAt;

	@ManyToOne
	private AttributeEntity attribute;
	
	public AttributeEntityHistory() { }
	
	public AttributeEntityHistory(String value) {
		this.value = value;
		this.updateAt = new Date();
	}

	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	@XmlTransient
	public AttributeEntity getAttribute() {
		return attribute;
	}

	public void setAttribute(AttributeEntity attribute) {
		this.attribute = attribute;
	}
	
	

}
