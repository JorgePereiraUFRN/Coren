package pacote2;

import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.storage.Attributes;
import context.arch.widget.Widget;
import context.arch.widget.Widget.WidgetData;

public class RoomEnactorReference extends EnactorReference {
	
	public static final short BRIGHTNESS_THRESHOLD = 100;

	public RoomEnactorReference(Enactor enactor, AbstractQueryItem<?,?> conditionQuery, String outcomeValue) {
		super(enactor, conditionQuery, outcomeValue);
	}
	
	private double LIGHT_SCALE = Math.pow(BRIGHTNESS_THRESHOLD, 0.33);
	
	@Override
	protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
		long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
		WidgetData data = new WidgetData("LightWidget", timestamp);
		int light;
		if (outcomeValue == LightWidget.LIGHT_ON) {
			/*
			 * "Proprietary or complicated" way to calculate light voltage level from sensed brightness
			 * Actually, using Steven's power law (http://en.wikipedia.org/wiki/Steven%27s_power_law) for light perception
			 * psi = k*I^0.33
			 * Scaled to 10
			 */
			
			short brightness = inWidgetState.getAttributeValue(RoomWidget.BRIGHTNESS);
			light = (int) (10*Math.pow(BRIGHTNESS_THRESHOLD - brightness, 0.33) / LIGHT_SCALE); // brightness will never be lower than BRIGHTNESS_THRESHOLD
		}
		else {
			light = 0;
		}			
			        
        /*
         * Note that outcomeValue would not equal the light level, so user may not be able to ask about specific level
         * TODO consider whether and how to support functional relationships
         */
		
		data.setAttributeValue(LightWidget.LIGHT, light);
		outAtts.putAll(data.toAttributes());
		
//		outAtts.add(AttributeNameValue.instance(LightWidget.LIGHT, light));
        return outAtts;
	}
	
}
