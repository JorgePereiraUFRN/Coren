/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.subscriber;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitUpdates extends Thread{
    
    private ServerSocket serverSocket; 
    private TreatMessage tratarMensagem;
   

/*    classe responsavel por aguardar as notificações envidas pelo hub e encaminha-las para
    a classe subscriber. Recebe como argumento p numero da porta no qual vai aguardar pelas
    notificações e um objeto que implementa a interface TreatMessage, é para ele que a mansagem 
    enviada nom momento em que for recebida.*/
    public WaitUpdates(int porta, TreatMessage tratarMensagem) {
        try {
            serverSocket = new ServerSocket(porta);
        } catch (IOException ex) {
            Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.tratarMensagem = tratarMensagem;
    }
    
    
    /*método responsável por extrair a mensagem do pacote TCP e ecaminha-la para o objeto que 
    que implemanta a inteface TreatMessage.
    */
    private void extractMessage(Socket socket){
        ObjectInputStream input = null;
        try {
            
            input = new ObjectInputStream(socket.getInputStream());
            String message = (String) input.readObject();
            tratarMensagem.TreatMessage(message);
 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
       
    }
    
    @Override
    public void run(){
        for(;;){
            try {
                extractMessage(serverSocket.accept());
            } catch (IOException ex) {
                Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception e){
            	Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    /* método responsável por encerrar a thread que espera pelas aptualizações
     */
    @Override
    public void finalize(){
        try {
            this.stop();
            serverSocket.close();
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(WaitUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }   
    
}
