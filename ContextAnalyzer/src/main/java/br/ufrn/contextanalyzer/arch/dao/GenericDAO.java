package br.ufrn.contextanalyzer.arch.dao;

import java.io.Serializable;
import java.util.List;

import br.ufrn.contextanalyzer.api.exceptions.DAOException;

public interface GenericDAO<T, ID extends Serializable> {
	
	public T findById(ID id) throws DAOException;
	
	public T findByName(String name) throws DAOException;
	 
	public List<T> findAll()throws DAOException;
 
	public T save(T entity)throws DAOException;
 
	public T update(T entity)throws DAOException;
        
	public void delete(T entity)throws DAOException;

}
