package br.ufrn.coren.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.AttributeEvalParser;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.enactor.QueryParser;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.Attribute;
import context.arch.storage.Attributes;

public class ReferenceModel {
	
	private String name;
	private QueryModel query;
	private OutcomeModel outcome;
	
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
	public OutcomeModel getOutcome() {
		return outcome;
	}
	public void setOutcome(OutcomeModel outcome) {
		this.outcome = outcome;
	}
	
}
