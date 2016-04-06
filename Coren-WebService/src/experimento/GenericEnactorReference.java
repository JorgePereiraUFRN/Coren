package experimento;

import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.storage.AttributeNameValue;
import context.arch.storage.Attributes;
import context.arch.widget.Widget;
import context.arch.widget.Widget.WidgetData;

public class GenericEnactorReference extends EnactorReference {

	public GenericEnactorReference(Enactor enactor, AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
		super(enactor, conditionQuery, outcomeValue);

	}
	
	
	
	protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
		
		long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
		
				
		outAtts.add(AttributeNameValue.instance("mensagem", "mensagem atualizada"));

		
//		outAtts.add(AttributeNameValue.instance(LightWidget.LIGHT, light));
        return outAtts;
		
	}

}
