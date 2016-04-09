package Coren;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.coren.DAO.WidgetModelDao;
import br.ufrn.coren.DAO.WidgetModelDaoInterface;
import br.ufrn.coren.Exceptions.DAOException;
import br.ufrn.coren.Models.AttributeModel;
import br.ufrn.coren.Models.WidgetModel;

public class testhibernate {

	private static WidgetModelDaoInterface entityDao = new WidgetModelDao();
	private static WidgetModel entity1 = new WidgetModel();

	public static void main(String[] args) throws DAOException {

		saveWidget();

	}

	private static void saveWidget() throws DAOException {
		entity1.setName("smart_home");

		List<AttributeModel> atts = new ArrayList<AttributeModel>();
		AttributeModel temperatura = new AttributeModel();
		temperatura.setType("int");
		temperatura.setName("temperatura");
		atts.add(temperatura);
		AttributeModel presenca = new AttributeModel();
		presenca.setType("int");
		presenca.setName("presenca");
		atts.add(presenca);
		
		entity1.setAttributes(atts);
		
		entity1 = entityDao.save(entity1);
	}
}
