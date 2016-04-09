package br.ufrn.coren.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufrn.coren.Services.HubService;
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

@XmlRootElement
@Entity
@Table(name="ENACTOR")
@SequenceGenerator(name="ENACTOR_SEQUENCE", sequenceName="ENACTOR_SEQUENCE", allocationSize=1, initialValue=0)
public class EnactorModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENACTOR_SEQUENCE")
    private long id;
	
	@Column
	private String name;
	
	@OneToOne
	@JoinColumn(name="WIDGET_ID")
	private WidgetModel inputWidget;

	@OneToOne
	@JoinColumn(name="OUTCOME_ID")
	private OutcomeModel<?> outcome;
	
	@OneToMany
	@JoinColumn(name="ENACTOR_ID")
	private List<ReferenceModel> references;
	
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
	
	@SuppressWarnings({ "serial" })
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
