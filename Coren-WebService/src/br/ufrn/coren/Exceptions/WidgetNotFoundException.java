package br.ufrn.coren.Exceptions;

public class WidgetNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WidgetNotFoundException(){
		super("Widget not found");
	}

	public WidgetNotFoundException(String message) {
		super("Widget not found");
	}
}
