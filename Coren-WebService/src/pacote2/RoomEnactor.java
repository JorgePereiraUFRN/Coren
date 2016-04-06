package pacote2;


import context.arch.discoverer.component.NonConstantAttributeElement;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.discoverer.query.ElseQueryItem;
import context.arch.discoverer.query.ORQueryItem;
import context.arch.discoverer.query.RuleQueryItem;
import context.arch.discoverer.query.comparison.AttributeComparison;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.AttributeNameValue;
import context.arch.storage.Attributes;

public class RoomEnactor extends Enactor {
	
	public static final short BRIGHTNESS_THRESHOLD = 100;

	
	@SuppressWarnings("serial")
	public RoomEnactor(AbstractQueryItem<?,?> inWidgetQuery, AbstractQueryItem<?,?> outWidgetQuery) {
		super(inWidgetQuery, outWidgetQuery, "light", "");
		
		/*
		 * Set up for enactor references, one for each outcome
		 */
		
		// light off
		AbstractQueryItem<?, ?> offQI = 
			new ORQueryItem(
					RuleQueryItem.instance(
							new NonConstantAttributeElement(AttributeNameValue.instance("presence", 0)),
							new AttributeComparison(AttributeComparison.Comparison.EQUAL)), // equal to 0; no one in the room, OR
					RuleQueryItem.instance(
							new NonConstantAttributeElement(AttributeNameValue.instance("brightness", BRIGHTNESS_THRESHOLD)), 
							new AttributeComparison(AttributeComparison.Comparison.GREATER)) // brightness more than BRIGHTNESS_THRESHOLD
					);
		EnactorReference er = new RoomEnactorReference(this, 
				offQI, 
				LightWidget.LIGHT_OFF);
		er.addServiceInput(new ServiceInput("LightService", "lightControl",
				new Attributes() {{
					addAttribute("light", Integer.class);
				}}));
		addReference(er);
		
		// light on, and brightness dependent
		er = new RoomEnactorReference(this, 
				new ElseQueryItem(offQI), 
				LightWidget.LIGHT_ON);
		er.addServiceInput(new ServiceInput("LightService", "lightControl", 
				new Attributes() {{
					addAttribute("light", Integer.class);
				}}));
		addReference(er);
		
		start();
	}
	
	

}
