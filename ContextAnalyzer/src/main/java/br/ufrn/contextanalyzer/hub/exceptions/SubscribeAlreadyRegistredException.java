package br.ufrn.contextanalyzer.hub.exceptions;

public class SubscribeAlreadyRegistredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubscribeAlreadyRegistredException(){
		super("Subscribe already registred");
	}

	public SubscribeAlreadyRegistredException(String message) {
		super(message);
	}
}
