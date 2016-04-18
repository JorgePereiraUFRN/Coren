package br.ufrn.contextanalyzer.api.dao;

import javax.persistence.EntityManagerFactory;

import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.arch.dao.EntityManagerDAO;

public class EntityManagerEnactorEntityDAO extends EntityManagerDAO<EnactorEntity, Long> implements EnactorEntityDAO {
	
	public EntityManagerEnactorEntityDAO(EntityManagerFactory factory) {
		super(factory);
	}
	
}
