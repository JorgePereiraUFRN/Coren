package br.ufrn.coren.models;


import java.util.List;

import context.arch.discoverer.ComponentDescription;
import context.arch.storage.Attribute;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;

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
	
	public Widget createWidget() {
		Widget widget = new Widget(
				name, 
				name) {
			
			@Override
			protected void init() {
				for (AttributeModel<?> att : attributes) {
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
				for (AttributeModel<?> att : attributes) {
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
	
	public static Widget createWidget(OutcomeModel<?> outcome) {
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
