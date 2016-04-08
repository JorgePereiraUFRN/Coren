package br.ufrn.coren.DAO;


public class HibernateDAOFactory extends DAOFactory {

    private GenericHibernateDAO instantiateDAO(Class daoClass) {
        try {
            GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
            //dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

	@Override
	public ContextEntityDao getContextEntityDao() {
		return  (ContextEntityDao) instantiateDAO(ContextEntityDao.class);
	}



	
}
