package br.ufrn.contextanalyzer.demos.roomapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.demos.test.ComunicationException;
import br.ufrn.contextanalyzer.demos.test.TopicDoesNotExistException;
import br.ufrn.contextanalyzer.hub.entities.SubscribeEntity;


public class Air {
	
	public BufferedImage airImage;
	private String room;
	private String topic;
	private int port;

	public Air(String room) {
		try {
			this.room = room;
			this.airImage = ImageIO.read(new File("img/air_top_0.png"));
			this.port = getFreePort();
			this.topic = room + "air";
			subscribe();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void subscribe() throws ComunicationException, TopicDoesNotExistException {
        String response;
        SubscribeEntity subscribe = new SubscribeEntity();
        subscribe.setAddress(RoomAPP.CALLBACK_IP);
        subscribe.setPort(port);
        try {
            WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/hub/topic/" + topic + "_" + topic + "/subscribe");
            response = resource.type("application/json").post(String.class, subscribe);
            if (response.equalsIgnoreCase("subscribed")) {
                new AirUpdate(port).start();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
	
	private int getFreePort() {
		int port = -1;
		try {
			ServerSocket s = new ServerSocket(0);
			port = s.getLocalPort();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return port;
	}
	
	private class AirUpdate extends Thread{
	    
	    private ServerSocket serverSocket;
	   
	    public AirUpdate(int port) {
	        try {
	            serverSocket = new ServerSocket(port);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    private void update(Socket socket){
	        ObjectInputStream input = null;
	        try {
	            
	            input = new ObjectInputStream(socket.getInputStream());
	            String air = input.readObject().toString();
    			String position = "_botton_";
    			if (room.equals("3") || room.equals("4")) {
    				position = "_top_";
    			}
//    			if (air == 0) {
//    				airImage = ImageIO.read(new File("img/air_0.png"));
//    			} else if (potencia < 23 && potencia >= 21) {
//    				airImage = ImageIO.read(new File("img/air" + position + "1.png"));
//    			} else if (potencia < 21 && potencia >= 19) {
//    				airImage = ImageIO.read(new File("img/air" + position + "2.png"));
//    			} else if (potencia < 19 && potencia >= 16) {
//    				airImage = ImageIO.read(new File("img/air" + position + "3.png"));
//    			}
    			
    			airImage = ImageIO.read(new File("img/air" + position + air + ".png"));
	 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                input.close();
	                socket.close();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    
	    @Override
	    public void run(){
	        for(;;){
	            try {
	                update(serverSocket.accept());
	            } catch(Exception e){
	            	e.printStackTrace();
	            }
	        }
	    }
	    
	    @SuppressWarnings("deprecation")
		@Override
	    public void finalize(){
	        try {
	            this.stop();
	            serverSocket.close();
	            super.finalize();
	        } catch (Throwable ex) {
	            ex.printStackTrace();
	        }
	    }   
	    
	}

}
