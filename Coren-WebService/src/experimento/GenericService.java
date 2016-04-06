package experimento;

import context.arch.comm.CommunicationsHandler;
import context.arch.comm.DataObject;
import context.arch.service.Service;
import context.arch.service.helper.FunctionDescription;
import context.arch.service.helper.FunctionDescriptions;
import context.arch.service.helper.ServiceInput;
import context.arch.widget.Widget;

public class GenericService extends Service{

	public GenericService(final Widget widget) {
		super(widget, "GenericService", 
				new FunctionDescriptions() {
					{ // constructor
						// define function for the service
						add(new FunctionDescription(
								"generic", 
								"funcao generica", 
								widget.getNonConstantAttributes()));
					}
				});
		
}

	@Override
	public DataObject execute(ServiceInput arg0) {
		
		System.out.println("publicar no hub");
		
		return new DataObject();
	}
	
}
