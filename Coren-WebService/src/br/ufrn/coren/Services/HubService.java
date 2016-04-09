package br.ufrn.coren.Services;


import context.arch.comm.DataObject;
import context.arch.service.Service;
import context.arch.service.helper.FunctionDescription;
import context.arch.service.helper.FunctionDescriptions;
import context.arch.service.helper.ServiceInput;
import context.arch.widget.Widget;

public class HubService extends Service {

	private String topic;
	
	@SuppressWarnings("serial")
	public HubService(final Widget widget, String topic) {
		super(widget, "HubService", 
				new FunctionDescriptions() {
					{ // constructor
						// define function for the service
						add(new FunctionDescription(
								"publish", 
								"publish value in hub", 
								widget.getNonConstantAttributes()));
					}
				});
		this.topic = topic;
	}

	@Override
	public DataObject execute(ServiceInput serviceInput) {
		String value = serviceInput.getInput().getAttributeValue(topic);

		System.out.println("Publish " + value + " in " + topic + "topic.");
		
		return new DataObject();
	}

}
