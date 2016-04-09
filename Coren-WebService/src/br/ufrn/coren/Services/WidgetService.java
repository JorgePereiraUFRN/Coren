package br.ufrn.coren.Services;

import br.ufrn.coren.Models.WidgetModel;
import context.arch.widget.Widget;

public class WidgetService {

	public Widget createWidget(WidgetModel widgetModel) {
		Widget widget = widgetModel.createWidget();
		
		// salvar no banco de dados
		
		return widget;
	}

	
}
