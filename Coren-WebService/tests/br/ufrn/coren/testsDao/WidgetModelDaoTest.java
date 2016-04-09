package br.ufrn.coren.testsDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ufrn.coren.DAO.WidgetModelDao;
import br.ufrn.coren.DAO.WidgetModelDaoInterface;
import br.ufrn.coren.Exceptions.DAOException;
import br.ufrn.coren.Models.AttributeModel;
import br.ufrn.coren.Models.WidgetModel;


public class WidgetModelDaoTest {

	private static WidgetModelDaoInterface widgetDao = new WidgetModelDao();
	private static WidgetModel widget1 = new WidgetModel();
	
	@BeforeClass
	public static void createWidget() throws DAOException{
		
		widget1.setName("smart_home");
		
		List<AttributeModel<?>> atts = new ArrayList<AttributeModel<?>>();
		AttributeModel<Integer> temperatura = new AttributeModel<Integer>();
		temperatura.setType("int");
		temperatura.setName("temperatura");
		atts.add(temperatura);
		AttributeModel<Integer> presenca = new AttributeModel<Integer>();
		presenca.setType("int");
		presenca.setName("presenca");
		atts.add(presenca);
	
		widget1.setAttributes(atts);
		
		widget1 = widgetDao.save(widget1);		
		
	}
	
	@Test
	public void listAllEntities() throws DAOException{
		
		List<WidgetModel> entities = widgetDao.findAll(WidgetModel.class);
		
		Assert.assertTrue(entities.size() > 0);
		
	}
	
	@Test
	public void searchById() throws DAOException{
		
		WidgetModel widget = widgetDao.findByName(WidgetModel.class, widget1.getName());
		
		Assert.assertEquals(widget, widget1);
	}
	
	@AfterClass
	public static void delete() throws DAOException{
		
		widgetDao.delete(widget1);
		
		Assert.assertTrue(widgetDao.findByName(WidgetModel.class, widget1.getName()) == null);
		
	}
	
	
}
