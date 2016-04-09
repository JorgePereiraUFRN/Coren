package br.ufrn.coren.models;

import context.arch.storage.Attribute;
import context.arch.storage.AttributeNameValue;

public class AttributeModel<T extends Comparable<T>> {
	
	private String name;
	private String type;
	private T value;
	private boolean constant;
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
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public boolean isConstant() {
		return constant;
	}
	public void setConstant(boolean constant) {
		this.constant = constant;
	}
	
	@SuppressWarnings("unchecked")
	public Attribute<T> toAttribute() {
		Attribute<T> att;		
		
		if (constant) {
			att = AttributeNameValue.instance(
					name,
					value
					);
		}
		else {
			Class<T> attType = (Class<T>) ModelUtils.toClass(type);
			att = Attribute.instance(name, attType);
		}
		
		return att;
	}
	
}
