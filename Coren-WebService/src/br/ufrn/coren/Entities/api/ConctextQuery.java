package br.ufrn.coren.Entities.api;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class ConctextQuery {

	private String entity;
	private String queryName;
	private String query;
	private String outcome;
	private String outcomeValue;
	private String queryDescriptiom;
	
	
	public String getQueryDescriptiom() {
		return queryDescriptiom;
	}
	public void setQueryDescriptiom(String queryDescriptiom) {
		this.queryDescriptiom = queryDescriptiom;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String enity) {
		this.entity = enity;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public String getOutcomeValue() {
		return outcomeValue;
	}
	public void setOutcomeValue(String outcomeValue) {
		this.outcomeValue = outcomeValue;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String querieName) {
		this.queryName = querieName;
	}
	
	
}
