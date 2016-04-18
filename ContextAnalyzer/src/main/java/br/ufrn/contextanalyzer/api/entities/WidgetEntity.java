package br.ufrn.contextanalyzer.api.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import context.arch.widget.Widget;

@XmlRootElement
@Entity
@Table(name="WIDGET")
@SequenceGenerator(name="WIDGET_SEQUENCE", sequenceName="WIDGET_SEQUENCE", allocationSize=1, initialValue=0)
public class WidgetEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WIDGET_SEQUENCE")
    private long id;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="WIDGET_ID")
	private List<AttributeEntity> attributes;
	
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
	
	public List<AttributeEntity> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<AttributeEntity> attributes) {
		this.attributes = attributes;
	}
	
	public Widget createWidget() {
		Widget widget = new Widget(
				name, 
				name) {
			
			@Override
			protected void init() {
				for (AttributeEntity att : attributes) {
					addAttribute(att.toAttribute(), att.isConstant());
				}
			}

			
			@Override
			public String getClassname() {
				return name;
			}
		};
		widget.start(true); // register widget in discoverer
		return widget;
	}
	
	public static Widget createWidget(final OutcomeEntity outcome) {
		Widget widget = new Widget(
				outcome.getName(), 
				outcome.getName()) {
			
			@Override
			protected void init() {
				addAttribute(outcome.toAttribute());
			}

			
			@Override
			public String getClassname() {
				return outcome.getName();
			}
		};
		widget.start(true); // register widget in discoverer
		return widget;
		
	}

	public void updateAttributeValue(AttributeEntity attributeEntity) {
		int index = attributes.indexOf(attributeEntity);
		if (index >= 0) {
			AttributeEntity att = attributes.get(index);
			att.setValue(attributeEntity.getValue());
			att.getAttributeEntityHistory().add(new AttributeEntityHistory(attributeEntity.getValue()));
			attributes.set(index, att);
		}
	}

}
