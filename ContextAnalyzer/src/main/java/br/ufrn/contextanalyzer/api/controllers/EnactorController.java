package br.ufrn.contextanalyzer.api.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.api.exceptions.EntityAlreadyRegistredException;
import br.ufrn.contextanalyzer.api.exceptions.EntityNotFoundException;
import br.ufrn.contextanalyzer.api.exceptions.UnprocessableEntityException;

@Path("enactor")
public class EnactorController {	

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createEnactor(EnactorEntity enactorEntity) {
		try {
			APIFacade.createEnactor(enactorEntity);
		} catch (EntityAlreadyRegistredException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		} catch (UnprocessableEntityException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		return "created";
	}
	
}
