package br.ufrn.contextanalyzer.arch.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import br.ufrn.contextanalyzer.api.exceptions.DAOException;

@SuppressWarnings("unchecked")
public abstract class EntityManagerDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	protected EntityManagerFactory factory;
	
	public EntityManagerDAO(EntityManagerFactory factory) {
		super();
		this.factory = factory;
	}
	
	public T save(T entity) throws DAOException {
		EntityManager em = factory.createEntityManager();
		T saved = null;
		try {
			em.getTransaction().begin();
			saved = em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			em.close();
		}
		return saved;
	}

	public T update(T entity) throws DAOException {
		return save(entity);
	}

	public void delete(T entity) throws DAOException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DAOException(e.getMessage());
		} finally {
			em.close();
		}
	}

	public T findById(ID id) throws DAOException {
		EntityManager em = factory.createEntityManager();
		T found = null;
		try {
			found = (T) em.find(getTypeClass(), id);
		} catch (NoResultException e) {
			found = null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		} finally {
			em.close();
		}
		return found;
	}
	
	public T findByName(String name) throws DAOException {
		EntityManager em = factory.createEntityManager();
		T found = null;
		try {
			found = (T) em.createQuery("SELECT o FROM " + getTypeClass().getSimpleName() + " o WHERE o.name = :name").setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			found = null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		} finally {
			em.close();
		}
		return found;
	}

	public List<T> findAll() throws DAOException {
		EntityManager em = factory.createEntityManager();
		List<T> list = null;
		try {
			list = em.createQuery(
					"select o from " + getTypeClass().getSimpleName() + " o")
					.getResultList();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		} finally {
			em.close();
		}
		return list;
	}
	
	protected Class<T> getTypeClass() {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }
	
}
