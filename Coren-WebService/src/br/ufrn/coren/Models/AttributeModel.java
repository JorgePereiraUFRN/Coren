package br.ufrn.coren.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import context.arch.storage.Attribute;
import context.arch.storage.AttributeNameValue;	

@Entity
@Table(name="ATTRIBUTE")
@SequenceGenerator(name="ATTRIBUTE_SEQUENCE", sequenceName="ATTRIBUTE_SEQUENCE", allocationSize=1, initialValue=0)
public class AttributeModel<T extends Comparable<T>> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTRIBUTE_SEQUENCE")
    private long id;
	
	@Column
	private String name;
	
	@Column
	private String type;
	
	@Column
	private T value;
	
	@Column
	private boolean constant;
	
	@Column(name="WIDGET_ID")
	private long widgetId;
	
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
	
	public long getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(long widgetId) {
		this.widgetId = widgetId;
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
