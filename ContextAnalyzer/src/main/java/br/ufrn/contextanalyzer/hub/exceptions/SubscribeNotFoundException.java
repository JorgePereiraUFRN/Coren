package br.ufrn.contextanalyzer.hub.exceptions;

public class SubscribeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubscribeNotFoundException(){
		super("Subscribe not found");
	}

	public SubscribeNotFoundException(String message) {
		super(message);
	}
}
