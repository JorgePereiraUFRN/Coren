package br.ufrn.coren.testsDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ufrn.coren.DAO.ContextEntityDao;
import br.ufrn.coren.DAO.ContextEntityDaoInterface;
import br.ufrn.coren.Entities.api.AttributeType;
import br.ufrn.coren.Entities.api.ConstAttribute;
import br.ufrn.coren.Entities.api.ContextEntity;
import br.ufrn.coren.Entities.api.NonConstAttribute;
import br.ufrn.coren.Exceptions.DAOException;


public class ContextEntityDaoTest {

	private static ContextEntityDaoInterface entityDao = new ContextEntityDao();
	private static ContextEntity entity1 = new ContextEntity();
	
	@BeforeClass
	public static void createEntity() throws DAOException{
		
		
		
		entity1.setName("smart_home");
		
		List<ConstAttribute> constatt = new ArrayList<>();
		
		ConstAttribute constAtt1 = new ConstAttribute();
		constAtt1.setAttName("room");
		constAtt1.setType(AttributeType.INTEGER);
		constAtt1.setValue("room");
		
		constatt.add(constAtt1);
	
		entity1.setConstAttributes(constatt);
		
		
		List<NonConstAttribute> nConstAtt = new ArrayList<>();
		NonConstAttribute ncAtt1 = new NonConstAttribute();
		ncAtt1.setAttName("temperature");
		ncAtt1.setType(AttributeType.INTEGER);
		
		NonConstAttribute ncAtt2 = new NonConstAttribute();
		ncAtt1.setAttName("has_people");
		ncAtt1.setType(AttributeType.BOOLEAN);
		
		nConstAtt.add(ncAtt1);
		nConstAtt.add(ncAtt2);
		
		entity1 = entityDao.save(entity1);		
		
	}
	
	@Test
	public void listAllEntities() throws DAOException{
		
		List<ContextEntity> entities = entityDao.findAll(ContextEntity.class);
		
		Assert.assertTrue(entities.size() > 0);
		
	}
	
	@Test
	public void searchById() throws DAOException{
		
		ContextEntity entity = entityDao.findById(ContextEntity.class, entity1.getName());
		
		Assert.assertEquals(entity, entity1);
	}
	
	@AfterClass
	public static void delete() throws DAOException{
		
		entityDao.delete(entity1);
		
		Assert.assertTrue(entityDao.findById(ContextEntity.class, entity1.getName()) == null);
		
	}
	
	
}
