package br.ufrn.contextanalyzer.api.exceptions;

public class EntityNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(){
		super("Entity not found");
	}

	public EntityNotFoundException(String entityName) {
		super(entityName + " not found");
	}
}
