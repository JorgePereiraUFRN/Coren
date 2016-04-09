package br.ufrn.coren.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import context.arch.storage.Attribute;

@Entity
@Table(name="OUTCOME")
@SequenceGenerator(name="OUTCOME_SEQUENCE", sequenceName="OUTCOME_SEQUENCE", allocationSize=1, initialValue=0)
public class OutcomeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OUTCOME_SEQUENCE")
    private long id;
	
	@Column
	private String name;
	
	@Column
	private String type;
	
	@Column
	private String value;
	
	@Column
	private String description;
	
	public long getId() {
		return this.id;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> Attribute<T> toAttribute() {
		
		Class<T> attType = (Class<T>) ModelUtils.toClass(this.type);
		Attribute<T> att = Attribute.instance(this.name, attType);
		
		return att;
	}
	
}
