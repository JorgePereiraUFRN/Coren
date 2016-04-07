package br.ufrn.coren.Entities;

import java.io.Serializable;

public class GenericAttribute<T> {

	private String atribute_name;
	private T atribute_type;
	
	public GenericAttribute(String atribute_name) {
		//super();
		this.atribute_name = atribute_name;
	}

	public String getAtribute_name() {
		return atribute_name;
	}

	public void setAtribute_name(String atribute_name) {
		this.atribute_name = atribute_name;
	}

	public T getAtribute_type() {
		return atribute_type;
	}

	public void setAtribute_type(T atribute_type) {
		this.atribute_type = atribute_type;
	}
	
	
	
	
}
