package br.ufrn.contextanalyzer.api.exceptions;

public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(){
		super();
	}

	public DAOException(String message) {
		super(message);
	}
}
