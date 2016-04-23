package br.ufrn.contextanalyzer.demos.roomapp;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.LoggingFilter;


public class RoomAPP {
	
	public static final Client client = new Client();
	public static final String SERVER_IP = "localhost";
	public static final String CALLBACK_IP = "127.0.0.1";
//	public static final String SERVER_IP = "192.168.0.100";
//	public static final String CALLBACK_IP = "192.168.0.101";

	public static void main(String[] args) {
		
//		client.addFilter(new LoggingFilter(System.out));
		
		Departament app = new Departament("IMD");
		
		JFrame frame = new JFrame("Estudo de Caso - Context Analyzer");
		frame.setLayout(new GridLayout(1, 2));
		frame.add(app.ui);
		
		JPanel panelDashboard = new JPanel();
		panelDashboard.setLayout(new GridLayout(2, 2));
		panelDashboard.add(app.room1.ui.dashboard);
		panelDashboard.add(app.room2.ui.dashboard);
		panelDashboard.add(app.room3.ui.dashboard);
		panelDashboard.add(app.room4.ui.dashboard);
		frame.add(panelDashboard);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1000, 650));
		frame.setLocationRelativeTo(null); // center of screen
		frame.setVisible(true);

	}

}
