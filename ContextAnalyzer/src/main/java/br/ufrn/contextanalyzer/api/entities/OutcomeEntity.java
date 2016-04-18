package br.ufrn.contextanalyzer.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ufrn.contextanalyzer.api.utils.EntityAPIUtil;
import context.arch.storage.Attribute;

@Entity
@Table(name="OUTCOME")
@SequenceGenerator(name="OUTCOME_SEQUENCE", sequenceName="OUTCOME_SEQUENCE", allocationSize=1, initialValue=0)
public class OutcomeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OUTCOME_SEQUENCE")
    private long id;
	
	private String name;
	
	private String type;
	
	private String value;
	
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
		
		Class<T> attType = (Class<T>) EntityAPIUtil.toClass(this.type);
		Attribute<T> att = Attribute.instance(this.name, attType);
		
		return att;
	}
	
}
