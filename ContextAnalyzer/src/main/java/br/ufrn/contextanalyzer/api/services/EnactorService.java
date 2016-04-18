package br.ufrn.contextanalyzer.api.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.contextanalyzer.api.dao.EnactorEntityDAO;
import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.api.exceptions.EntityAlreadyRegistredException;
import br.ufrn.contextanalyzer.api.exceptions.EntityNotFoundException;
import br.ufrn.contextanalyzer.api.exceptions.UnprocessableEntityException;
import br.ufrn.contextanalyzer.arch.dao.DAOFactory;
import context.arch.enactor.Enactor;

public class EnactorService {
	
	private static final EnactorService singleton = new EnactorService();
	private static final EnactorEntityDAO dao = DAOFactory.getFactory().getEnactorEntityDAO();
	private static final Map<String, Enactor> enactors = Collections.synchronizedMap(new HashMap<String, Enactor>());

	private EnactorService() {};
	public static EnactorService getInstance() {
		return singleton;
	}
	
	public Enactor createEnactor(EnactorEntity enactorEntity) throws EntityNotFoundException, EntityAlreadyRegistredException, UnprocessableEntityException {
		
		validate(enactorEntity);
		
		if (enactors.get(enactorEntity.getName()) != null) {
			throw new EntityAlreadyRegistredException();
		}
		
		Enactor enactor = enactorEntity.createEnactor();
		enactors.put(enactorEntity.getName(), enactor);
		
		dao.save(enactorEntity);
		
		return enactor;
	}
	
	@SuppressWarnings("unused")
	private void validate(EnactorEntity enactor) throws UnprocessableEntityException {
		// TODO validar entidade do enactor aqui.
		if(false) {
			throw new UnprocessableEntityException();
		}
	}
}
