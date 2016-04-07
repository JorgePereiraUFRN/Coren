package br.ufrn.coren.Controller;

import java.util.List;

import br.ufrn.coren.Entities.GenericAttribute;
import context.arch.storage.Attribute;
import context.arch.storage.AttributeNameValue;
import context.arch.widget.Widget;

public class GenericWidget extends Widget {

	private String id;

	private List<GenericAttribute> atributes;

	public GenericWidget(String id, List<GenericAttribute> attributes) {
		super(id, id);
		this.id = id;
		this.atributes = attributes;

		super.start(true);
	}

	@Override
	protected void init() {
		
		for (GenericAttribute a : atributes) {

			
			if (a.getAtribute_type() instanceof Integer) {
				addAttribute(Attribute.instance(a.getAtribute_name(), Integer.class));
			} else if (a.getAtribute_type() instanceof String) {
				addAttribute(Attribute.instance(a.getAtribute_name(), String.class));
			}else if (a.getAtribute_type() instanceof Boolean) {
				addAttribute(Attribute.instance(a.getAtribute_name(), Boolean.class));
			}else if (a.getAtribute_type() instanceof Float) {
				addAttribute(Attribute.instance(a.getAtribute_name(), Float.class));
			}else{
				System.out.println("não criou o atributo "+a.getAtribute_name());
				//TODO levantar exceção
			}

		}
	}

}
