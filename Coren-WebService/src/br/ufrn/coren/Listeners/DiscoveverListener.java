package br.ufrn.coren.Listeners;

import javax.servlet.ServletContextEvent;

import context.arch.discoverer.Discoverer;

public class DiscoveverListener implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Discoverer.start();
		
	}

   
}
