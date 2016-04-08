package br.ufrn.coren.Entities.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class ContextEntity {

	protected String name;

	protected List<ConstAttribute> constAttributes =  new ArrayList<>();
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
