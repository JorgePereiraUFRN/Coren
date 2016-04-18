package br.ufrn.contextanalyzer.api.exceptions;

public class UnprocessableEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnprocessableEntityException(){
		super("Unprocessable entity");
	}

	public UnprocessableEntityException(String entityName) {
		super("Unprocessable " + entityName);
	}
}
