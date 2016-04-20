package br.ufrn.contextanalyzer.hub.services;


import br.ufrn.contextanalyzer.hub.controllers.HubFacade;
import br.ufrn.contextanalyzer.hub.exceptions.TopicAlreadyRegistredException;
import br.ufrn.contextanalyzer.hub.exceptions.TopicNotFoundException;
import context.arch.comm.DataObject;
import context.arch.service.Service;
import context.arch.service.helper.FunctionDescription;
import context.arch.service.helper.FunctionDescriptions;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.Attribute;
import context.arch.widget.Widget;

public class PublishHubService extends Service {

	private String widgetClassname;
	
	@SuppressWarnings("serial")
	public PublishHubService(final Widget widget) {
		super(widget, "PublishHubService", 
				new FunctionDescriptions() {
					{
						add(new FunctionDescription(
								"publish", 
								"publish value in hub", 
								widget.getNonConstantAttributes()));
					}
				});
		this.widgetClassname = widget.getClassname();
		try {
			HubFacade.register(widget);
		} catch (TopicAlreadyRegistredException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public DataObject execute(ServiceInput serviceInput) {
		for (Attribute att : serviceInput.getInput().values()) {
			String idTopic = widgetClassname + "_" + att.getName();
			String value = serviceInput.getInput().getAttributeValue(att.getName()).toString();
			
			System.out.println("publish  " + widgetClassname + "_" + att.getName() + " = " + value);
			try {
				HubFacade.publish(idTopic, value);
			} catch (TopicNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new DataObject();
	}

}
