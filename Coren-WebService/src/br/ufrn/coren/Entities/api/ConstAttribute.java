package br.ufrn.coren.Entities.api;

import javax.persistence.Entity;

@Entity
public class ConstAttribute extends NonConstAttribute {
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
