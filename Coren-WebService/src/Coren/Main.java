package Coren;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.coren.Controller.GenericEnactor;
import br.ufrn.coren.Controller.GenericService;
import br.ufrn.coren.Controller.GenericWidget;
import br.ufrn.coren.Entities.GenericAttribute;
import br.ufrn.coren.Entities.Query;
import br.ufrn.coren.Entities.QueryComparassion;
import context.arch.discoverer.Discoverer;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.service.Service;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;

public class Main {

	public static void main(String[] args) {
		
		Discoverer.start();
		
		List<GenericAttribute> atributesEntrada = new ArrayList<>();
		GenericAttribute<Integer> temp = new GenericAttribute<Integer>("temperatura");
		temp.setAtribute_type(0);
		atributesEntrada.add(temp);
		
		Widget widgetEntrada = new GenericWidget("entrada", atributesEntrada);
		
		
		List<GenericAttribute> atributesSaida = new ArrayList<>();
		GenericAttribute<String> mensagem = new GenericAttribute<String>("mensagem");
		mensagem.setAtribute_type("mensagem");
		atributesSaida.add(mensagem);
		
		Widget widgetSaida = new GenericWidget("saida", atributesSaida);
		
		
		
		Service service = new GenericService(widgetSaida);
		 
		widgetSaida.addService(service);
		
		AbstractQueryItem<?,?> WidgetQueryEntrada = WidgetXmlParser.createWidgetSubscriptionQuery(widgetEntrada);
		
		AbstractQueryItem<?,?> WidgetQuerySaida = WidgetXmlParser.createWidgetSubscriptionQuery(widgetSaida);
		
		
		Query<Integer> query = new Query<Integer>(temp, QueryComparassion.GREATER, 70);
		
		
		GenericEnactor enactor = new GenericEnactor(WidgetQueryEntrada,WidgetQuerySaida, "mensagem", "enactor1", query);
		
		//widgetEntrada.updateData("temperatura", 72);
		
		System.out.println("entrada: "+ widgetEntrada.getNonConstantAttributes());
		System.out.println("saida: "+ widgetSaida.getNonConstantAttributes());

	}

}
