/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.contextanalyzer.demos.room_app;

/**
 *
 * @author Jorge
 */
public interface TreatMessage {
    
/*	para este m�todo ser�o enviadas todas as notifica��es no momento em
	que chegarem, ele deve ser implementado numa classe concreta, � nele 
	que deve ser definido o que deve ser feito com a menssagem quando a mesma for recebida.*/
    public abstract void TreatMessage(String message);
}
