package br.ufrn.coren.Models;

public class ReferenceModel {
	
	private String name;
	
	private QueryModel query;
	
	private OutcomeModel<?> outcome;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public QueryModel getQuery() {
		return query;
	}
	public void setQuery(QueryModel query) {
		this.query = query;
	}
	public OutcomeModel<?> getOutcome() {
		return outcome;
	}
	public void setOutcome(OutcomeModel<?> outcome) {
		this.outcome = outcome;
	}

}
