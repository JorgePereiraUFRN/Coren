/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.exceptions;

/**
 *
 * @author jorge
 */
public class ComunicationException extends Exception {

    /**
     * Creates a new instance of
     * <code>ComunicationException</code> without detail message.
     */
    public ComunicationException() {
    }

    /**
     * Constructs an instance of
     * <code>ComunicationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ComunicationException(String msg) {
        super(msg);
    }
}
