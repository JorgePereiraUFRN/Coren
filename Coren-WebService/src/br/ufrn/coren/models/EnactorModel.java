package br.ufrn.coren.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.coren.services.HubService;
import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.AttributeEvalParser;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.service.Service;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.Attributes;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;

public class EnactorModel {
	
	private String name;
	private WidgetModel inputWidget;
	@SuppressWarnings("rawtypes")
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
	public OutcomeModel<?> getOutcome() {
		return outcome;
	}
	public void setOutcome(OutcomeModel<?> outcome) {
		this.outcome = outcome;
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	public Enactor createEnactor() {
		
		ComponentDescription inWidgetStub = inputWidget.createWidgetStub();
		AbstractQueryItem<?, ?> inWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(inWidgetStub);
		
		Widget outputWidget = WidgetModel.createWidget(outcome);
		Service hubService = new HubService(outputWidget, outcome.getName());
		outputWidget.addService(hubService);
		
		ComponentDescription outWidgetStub = WidgetXmlParser.createWidgetStub(outputWidget);
		AbstractQueryItem<?, ?> outWidgetQuery = WidgetXmlParser.createWidgetSubscriptionQuery(outWidgetStub);
		
		GenericEnactor enactor = new GenericEnactor(inWidgetQuery, outWidgetQuery, outcome.getName(), name) {

			@Override				
			public String getClassname() {
				return name;
			}
		};
		
		Map<String, AbstractQueryItem<?, ?>> queries = new HashMap<String, AbstractQueryItem<?,?>>();
		
		Map<String, Comparable<?>> constVars = new HashMap<String, Comparable<?>>(); // pode ser adicionados constantes do enactor
			
		for (ReferenceModel reference : references) {
			QueryModelParser parser = new QueryModelParser(reference.getQuery().getValue().trim(), constVars, queries, inWidgetStub);
			AbstractQueryItem<?, ?> query = parser.parseQuery();
			queries.put(reference.getQuery().getNome(), query);

			List<AttributeEvalParser<?>> assnParsers = new ArrayList<AttributeEvalParser<?>>();
			assnParsers.add(AttributeEvalParser.instance(reference.getOutcome().toAttribute(), reference.getOutcome().getValue().trim(), constVars));

			List<ServiceInput> serviceInputs = new ArrayList<ServiceInput>();
			serviceInputs.add(new ServiceInput("HubService", "publish", 
					new Attributes() {{
						add(reference.getOutcome().toAttribute());
					}}));
			
			EnactorReference er = new EnactorReference(enactor, query, reference.getName(), assnParsers, serviceInputs);
			enactor.addReference(er);
		}
		
		enactor.start();
		return enactor;
	}
	
	private class GenericEnactor extends Enactor {

		public GenericEnactor(AbstractQueryItem<?, ?> inWidgetSubscriptionQuery,
				AbstractQueryItem<?, ?> outWidgetSubscriptionQuery, String outcomeName, String shortId) {
			super(inWidgetSubscriptionQuery, outWidgetSubscriptionQuery, outcomeName, shortId);
		}
		
		@Override
		public void addReference(EnactorReference er) {
			String outcomeValue = er.getOutcomeValue();

			er.setEnactor(this);
			
			List<EnactorReference> refs = enactorReferences.get(outcomeValue);
			if (refs == null) { 
				refs = new ArrayList<EnactorReference>();
				enactorReferences.put(outcomeValue, refs);
			}
			refs.add(er);
		}
		
	}
	
}
