package br.ufrn.coren.services;

import br.ufrn.coren.models.WidgetModel;
import context.arch.widget.Widget;

public class WidgetService {

	public Widget createWidget(WidgetModel widgetModel) {
		Widget widget = widgetModel.createWidget();
		return widget;
	}

	
}
