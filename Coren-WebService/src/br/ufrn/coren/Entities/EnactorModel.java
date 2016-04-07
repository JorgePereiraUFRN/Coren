package br.ufrn.coren.Entities;

import java.util.List;

public class EnactorModel {
	
	private String name;
	private WidgetModel inputWidget;
	private ReferenceModel reference;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WidgetModel getInputWidget() {
		return inputWidget;
	}
	public void setInputWidget(WidgetModel inputWidget) {
		this.inputWidget = inputWidget;
	}
	public ReferenceModel getReference() {
		return reference;
	}
	public void setReference(ReferenceModel reference) {
		this.reference = reference;
	}
	

	
}
