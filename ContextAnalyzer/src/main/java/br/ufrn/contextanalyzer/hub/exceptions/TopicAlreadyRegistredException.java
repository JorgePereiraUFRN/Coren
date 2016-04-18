package br.ufrn.contextanalyzer.hub.exceptions;

public class TopicAlreadyRegistredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TopicAlreadyRegistredException(){
		super("Topic already registred");
	}

	public TopicAlreadyRegistredException(String message) {
		super(message);
	}
}
