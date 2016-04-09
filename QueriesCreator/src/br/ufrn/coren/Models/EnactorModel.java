package br.ufrn.coren.Models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnactorModel {
	
	private String name;
	
	private WidgetModel inputWidget;

	private OutcomeModel outcome;
	
	private List<ReferenceModel> references;
	
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
	
	public List<ReferenceModel> getReferences() {
		return references;
	}
	
	public void setReference(List<ReferenceModel> references) {
		this.references = references;
	}
	
	public OutcomeModel getOutcome() {
		return outcome;
	}
	
	public void setOutcome(OutcomeModel outcome) {
		this.outcome = outcome;
	}
		
}
