package experimento;

import java.util.ArrayList;
import java.util.List;

import context.arch.discoverer.Discoverer;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.service.Service;
import context.arch.widget.Widget;
import context.arch.widget.WidgetXmlParser;

public class Main {

	public static void main(String[] args) {
		
		Discoverer.start();
		
		List<GenericAtribute> atributesEntrada = new ArrayList<>();
		GenericAtribute<Integer> temp = new GenericAtribute<Integer>("temperatura");
		temp.setAtribute_type(0);
		atributesEntrada.add(temp);
		
		Widget widgetEntrada = new GenericWidget("entrada", atributesEntrada);
		
		
		List<GenericAtribute> atributesSaida = new ArrayList<>();
		GenericAtribute<String> mensagem = new GenericAtribute<String>("mensagem");
		mensagem.setAtribute_type("mensagem");
		atributesSaida.add(mensagem);
		
		Widget widgetSaida = new GenericWidget("saida", atributesSaida);
		
		
		
		Service service = new GenericService(widgetSaida);
		 
		widgetSaida.addService(service);
		
		AbstractQueryItem<?,?> WidgetQueryEntrada = WidgetXmlParser.createWidgetSubscriptionQuery(widgetEntrada);
		
		AbstractQueryItem<?,?> WidgetQuerySaida = WidgetXmlParser.createWidgetSubscriptionQuery(widgetSaida);
		
		
		Query<Integer> query = new Query<Integer>(temp, QueryComparassion.GREATER, 70);
		
		
		GenericEnactor enactor = new GenericEnactor(WidgetQueryEntrada,WidgetQuerySaida, "enactor1", "enactor1", query);
		
		widgetEntrada.updateData("temperatura", 72);
		
		System.out.println("entrada: "+ widgetEntrada.getNonConstantAttributes());
		System.out.println("saida: "+ widgetSaida.getNonConstantAttributes());

	}

}
