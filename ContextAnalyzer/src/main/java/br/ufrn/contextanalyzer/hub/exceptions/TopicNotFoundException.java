package br.ufrn.contextanalyzer.hub.exceptions;

public class TopicNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TopicNotFoundException(){
		super("Topic not found");
	}

	public TopicNotFoundException(String message) {
		super(message);
	}
}
