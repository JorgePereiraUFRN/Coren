/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.contextanalyzer.demos.room_app;

/**
 *
 * @author Jorge
 */
public class TopicDoesNotExistException extends Exception {

    /**
     * Creates a new instance of
     * <code>TopicDoesNotExistException</code> without detail message.
     */
    public TopicDoesNotExistException() {
    }

    /**
     * Constructs an instance of
     * <code>TopicDoesNotExistException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public TopicDoesNotExistException(String msg) {
        super(msg);
    }
}
