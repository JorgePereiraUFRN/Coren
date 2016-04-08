package br.ufrn.coren.DAO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import br.ufrn.coren.Exceptions.DAOException;



public abstract class GenericHibernateDAO<T, ID extends Serializable>
		implements GenericDaoInterface<T, ID> {

	public static final String PERSISTENCE_UNIT = "Coren";

	protected static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT);

	EntityManager em = null;

	public GenericHibernateDAO() {

	}

	@Override
	public T save(T entity) throws DAOException {

		try {
			T saved = null;

			try {
				getInstance().getTransaction().begin();
				saved = getInstance().merge(entity);
				getInstance().getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			return saved;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T update(T entity) throws DAOException {
		return save(entity);
	}

	@Override
	public void delete(T entity) throws DAOException {
		try {
			getInstance().getTransaction().begin();
			getInstance().remove(entity);
			getInstance().getTransaction().commit();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public T findById(Class<T> classe, ID id) throws DAOException {

		T found = null;

		try {
			found = getInstance().find(classe, id);
		} catch (NoResultException e) {
			return found;
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}

		return found;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> classe) throws DAOException {
		List<T> list = null;
		try {
			list = getInstance().createQuery(
					"select o from " + classe.getSimpleName() + " o")
					.getResultList();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		return list;
	}

	private EntityManager getEntityManager() {
		if (factory == null) {
			try {
				// TODO rever forma de instanciar EntityManagerFactor
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return factory.createEntityManager();
	}

	public synchronized EntityManager getInstance() {
		if (em == null) {
			em = getEntityManager();
		}

		return em;
	}
}