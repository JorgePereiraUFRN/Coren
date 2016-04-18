package br.ufrn.contextanalyzer.server.listeners;

import javax.servlet.ServletContextEvent;

import context.arch.discoverer.Discoverer;

public class DiscoveverListener implements javax.servlet.ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Discoverer.start();
	}

   
}
