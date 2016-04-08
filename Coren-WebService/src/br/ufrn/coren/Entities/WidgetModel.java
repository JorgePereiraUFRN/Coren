package br.ufrn.coren.Entities;


import context.arch.storage.Attributes;

public class WidgetModel {
	
	private String name;
	private Attributes nonConstAttributes = new Attributes();
	private Attributes constAttributes = new Attributes();
	
	
	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Attributes getNonConstAttributes() {
		return nonConstAttributes;
	}
	public void setNonConstAttributes(Attributes nonConstAttributes) {
		this.nonConstAttributes = nonConstAttributes;
	}
	public Attributes getConstAttributes() {
		return constAttributes;
	}
	public void setConstAttributes(Attributes constAttributes) {
		this.constAttributes = constAttributes;
	}
	
	

}
