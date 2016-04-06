package experimento;

import context.arch.discoverer.component.NonConstantAttributeElement;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.discoverer.query.ORQueryItem;
import context.arch.discoverer.query.RuleQueryItem;
import context.arch.discoverer.query.comparison.AttributeComparison;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.AttributeNameValue;
import context.arch.storage.Attributes;

public class GenericEnactor extends Enactor {

	public GenericEnactor(AbstractQueryItem<?, ?> inWidgetSubscriptionQuery,
			AbstractQueryItem<?, ?> outWidgetSubscriptionQuery, String outcomeName, String shortId, Query<?> query) {

		super(inWidgetSubscriptionQuery, outWidgetSubscriptionQuery, outcomeName, shortId);
		// TODO Auto-generated constructor stub


		NonConstantAttributeElement attributeElement = null;
		AttributeComparison atComparison = null;

		if (query.getBaseValue() instanceof Integer) {

			attributeElement = new NonConstantAttributeElement(AttributeNameValue
					.instance(query.getGenericAtribute().getAtribute_name(), (Integer) query.getBaseValue()));
		} else if (query.getBaseValue() instanceof String) {
			attributeElement = new NonConstantAttributeElement(AttributeNameValue
					.instance(query.getGenericAtribute().getAtribute_name(), (String) query.getBaseValue()));
		} else if (query.getBaseValue() instanceof Float) {
			attributeElement = new NonConstantAttributeElement(AttributeNameValue
					.instance(query.getGenericAtribute().getAtribute_name(), (Float) query.getBaseValue()));
		} else if (query.getBaseValue() instanceof Boolean) {
			attributeElement = new NonConstantAttributeElement(AttributeNameValue
					.instance(query.getGenericAtribute().getAtribute_name(), (boolean) query.getBaseValue()));
		}else{
			System.out.println("erro");
		}

		if (query.getQueryComparassion().toString().equals(QueryComparassion.DIFFERENT.toString())) {
			atComparison = new AttributeComparison(AttributeComparison.Comparison.DIFFERENT);
		} else if (query.getQueryComparassion().toString().equals(QueryComparassion.EQUAL.toString())) {
			atComparison = new AttributeComparison(AttributeComparison.Comparison.EQUAL);
		} else if (query.getQueryComparassion().toString().equals(QueryComparassion.GREATER.toString())) {
			atComparison = new AttributeComparison(AttributeComparison.Comparison.GREATER);
		} else if (query.getQueryComparassion().toString().equals(QueryComparassion.GREATER_OR_EQUAL.toString())) {
			atComparison = new AttributeComparison(AttributeComparison.Comparison.GREATER_EQUAL);
		} else if (query.getQueryComparassion().toString().equals(QueryComparassion.LESS.toString())) {
			atComparison = new AttributeComparison(AttributeComparison.Comparison.LESS);
		} else if (query.getQueryComparassion().toString().equals(QueryComparassion.LESS_OR_EQUAL.toString())) {
			atComparison = new AttributeComparison(AttributeComparison.Comparison.LESS_EQUAL);
		}else{
			System.out.println("erro");
		}
		
		System.out.println(query.getGenericAtribute().getAtribute_name());

		AbstractQueryItem<?, ?> queryitem = new ORQueryItem(RuleQueryItem.instance(attributeElement, atComparison));

		EnactorReference er = new GenericEnactorReference(this, queryitem, "mensagem");
		
		er.addServiceInput(new ServiceInput("GenericService", "generic", new Attributes() {
			{
				addAttribute("mensagem", String.class);
			}
		}));
		addReference(er);
		
		start();
		
		System.out.println("enactor criado");
	}

}
