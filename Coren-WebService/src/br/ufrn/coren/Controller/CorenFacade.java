package br.ufrn.coren.Controller;

import br.ufrn.coren.Exceptions.WidgetNotFoundException;
import br.ufrn.coren.Models.EnactorModel;
import br.ufrn.coren.Models.WidgetModel;
import br.ufrn.coren.Services.EnactorService;
import br.ufrn.coren.Services.WidgetService;

public class CorenFacade {
	
	
	public void createWidget(WidgetModel widget){
		WidgetService service = new WidgetService();
		service.createWidget(widget);
	}
	
	public void createEnactor(EnactorModel enactor) throws WidgetNotFoundException{
		EnactorService service = new EnactorService();
		service.createEnactor(enactor);
	}
	
	
}
