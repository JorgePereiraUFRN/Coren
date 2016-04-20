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


public class Light {
	
	public BufferedImage lightImage;
	private String room;
	private String topic;
	private int port;

	public Light(String room) {
		try {
			this.room = room;
			this.lightImage = ImageIO.read(new File("img/light_0.png"));
			this.port = getFreePort();
			this.topic = room + "light";
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
                new LightUpdate(port).start();
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
	
	private class LightUpdate extends Thread{
	    
	    private ServerSocket serverSocket;
	   
	    public LightUpdate(int port) {
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
	            String light = input.readObject().toString();
	            
	            System.out.println("light: " + light);
//    			if (light == 0) {
//    				lightImage = ImageIO.read(new File("img/light_0.png"));
//    			} else if (light > 80 && light <= 100) {
//    				lightImage = ImageIO.read(new File("img/light_1.png"));
//    			} else if (light > 100 && light <= 110) {
//    				lightImage = ImageIO.read(new File("img/light_2.png"));
//    			} else if (light > 110 && light <= 120) {
//    				lightImage = ImageIO.read(new File("img/light_3.png"));
//    			} else if (light > 120 && light <= 130) {
//    				lightImage = ImageIO.read(new File("img/light_4.png"));
//    			} else if (light > 130 && light <= 140) {
//    				lightImage = ImageIO.read(new File("img/light_5.png"));
//    			} else if (light > 140 && light <= 150) {
//    				lightImage = ImageIO.read(new File("img/light_6.png"));
//    			} else if (light > 150 && light <= 160) {
//    				lightImage = ImageIO.read(new File("img/light_7.png"));
//    			}
	            lightImage = ImageIO.read(new File("img/light_" + light + ".png"));
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
