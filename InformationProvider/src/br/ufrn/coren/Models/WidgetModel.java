package br.ufrn.coren.Models;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WidgetModel {
	
	private String name;
	
	private List<AttributeModel<?>> attributes;
	
	public String getName() {		
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<AttributeModel<?>> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<AttributeModel<?>> attributes) {
		this.attributes = attributes;
	}

}
