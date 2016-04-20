package br.ufrn.contextanalyzer.demos.roomapp;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Departament {
	
	public String departament;
	public Room room1;
	public Room room2;
	public Room room3;
	public Room room4;
	public DepartamentUI ui;
	
	public Departament(String departament) {
		this.departament = departament;
		this.room1 = new Room("1");
		this.room2 = new Room("2");
		this.room3 = new Room("3");
		this.room4 = new Room("4");
		this.ui = new DepartamentUI();
	}
	
	@SuppressWarnings("serial")
	public class DepartamentUI extends JPanel{ 
		
		BufferedImage departamentImage;
		
		public DepartamentUI() {
			setLayout(null);
			try {
				departamentImage = ImageIO.read(new File("img/departament.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			add(room1.ui);
			room1.ui.setLocation(0, room1.ui.getHeight());
			add(room2.ui);
			room2.ui.setLocation(departamentImage.getWidth() - room2.ui.getWidth(), room2.ui.getHeight());
			add(room3.ui);
			room3.ui.setLocation(0, 0);
			add(room4.ui);
			room4.ui.setLocation(departamentImage.getWidth() - room4.ui.getWidth(), 0);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphics = (Graphics2D) g;
			int w = departamentImage.getWidth();   
	    	int h = departamentImage.getHeight();   

	    	graphics.drawImage(departamentImage, 0, 0, w, h, null);
	    	graphics.dispose();
	    }
	}

}
