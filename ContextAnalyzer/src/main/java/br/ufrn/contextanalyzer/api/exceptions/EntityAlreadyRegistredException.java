package br.ufrn.contextanalyzer.api.exceptions;

public class EntityAlreadyRegistredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityAlreadyRegistredException(){
		super("Entity already registred");
	}

	public EntityAlreadyRegistredException(String entityName) {
		super(entityName + " already registred");
	}
}
