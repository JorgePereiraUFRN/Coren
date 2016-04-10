package br.ufrn.coren.Controller;

import br.ufrn.coren.Exceptions.WidgetNotFoundException;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.WidgetModel;
import br.ufrn.coren.Services.EnactorService;
import br.ufrn.coren.Services.WidgetService;
import context.arch.enactor.Enactor;
import context.arch.widget.Widget;

public class CorenFacade {
	
	
	public Widget createWidget(WidgetModel widget){
		WidgetService service = new WidgetService();
		return service.createWidget(widget);
	}
	
	public Enactor createEnactor(EnactorModel enactor) throws WidgetNotFoundException{
		EnactorService service = new EnactorService();
		return service.createEnactor(enactor);
	}
	
	
}
