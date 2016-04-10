package br.ufrn.coren.Models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnactorModel {
	
	private String name;
	
	private String widget;

	private OutcomeModel outcome;
	
	private List<ReferenceModel> references;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getWidget() {
		return widget;
	}
	
	public void setWidget(String widget) {
		this.widget = widget;
	}
	
	public List<ReferenceModel> getReferences() {
		return references;
	}
	
	public void setReferences(List<ReferenceModel> references) {
		this.references = references;
	}
	
	public OutcomeModel getOutcome() {
		return outcome;
	}
	
	public void setOutcome(OutcomeModel outcome) {
		this.outcome = outcome;
	}
		
}
