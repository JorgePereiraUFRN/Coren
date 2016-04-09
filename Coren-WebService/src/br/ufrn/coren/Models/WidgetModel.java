package br.ufrn.coren.Models;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import context.arch.discoverer.ComponentDescription;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;

@XmlRootElement
@Entity
@Table(name="WIDGET")
@SequenceGenerator(name="WIDGET_SEQUENCE", sequenceName="WIDGET_SEQUENCE", allocationSize=1, initialValue=0)
public class WidgetModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WIDGET_SEQUENCE")
    private long id;
	
	@Column
	private String name;
	
	@OneToMany
	@JoinColumn(name="WIDGET_ID", nullable = false)
	private List<AttributeModel> attributes;
	
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
	
	public List<AttributeModel> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<AttributeModel> attributes) {
		this.attributes = attributes;
	}
	
	public Widget createWidget() {
		Widget widget = new Widget(
				name, 
				name) {
			
			@Override
			protected void init() {
				for (AttributeModel att : attributes) {
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
	
	public ComponentDescription createWidgetStub() {
		Widget widget = new Widget(
				name, 
				name) {
			
			@Override
			protected void init() {
				// TODO: aqui os atributos constantes deveriam ficar sem os valores varificar se com os valores irar gerar algum problema
				for (AttributeModel att : attributes) {
					addAttribute(att.toAttribute(), att.isConstant());
				}
			}

			
			@Override
			public String getClassname() {
				return name;
			}
		};
		widget.start(false); // not register in discoverer
		return WidgetXmlParser.createWidgetStub(widget);
	}
	
	public static Widget createWidget(OutcomeModel outcome) {
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
	
	

}
