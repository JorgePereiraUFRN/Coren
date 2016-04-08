package br.ufrn.coren.Entities.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class ContextEntity {
	
	@Id
	protected String name;
	
	@OneToMany
	//@JoinTable(name = "EntityContsAttr", joinColumns = @JoinColumn(name = "entity_name"), inverseJoinColumns = @JoinColumn(name = "att_id"))
	protected List<ConstAttribute> constAttributes = new ArrayList<>();
	
	@OneToMany
	//@JoinTable(name = "EntityNomConstAttrs", joinColumns = @JoinColumn(name = "entity_name"), inverseJoinColumns = @JoinColumn(name = "att_id"))
	protected List<NonConstAttribute> nonConstAttributes = new ArrayList<>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ConstAttribute> getConstAttributes() {
		return constAttributes;
	}
	public void setConstAttributes(List<ConstAttribute> constAttributes) {
		this.constAttributes = constAttributes;
	}
	public List<NonConstAttribute> getNonConstAttributes() {
		return nonConstAttributes;
	}
	public void setNonConstAttributes(List<NonConstAttribute> nonConstAttributes) {
		this.nonConstAttributes = nonConstAttributes;
	}
	
	
	

}
