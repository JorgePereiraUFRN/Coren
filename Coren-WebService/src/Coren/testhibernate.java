package Coren;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.coren.DAO.ContextEntityDao;
import br.ufrn.coren.DAO.ContextEntityDaoInterface;
import br.ufrn.coren.Entities.api.AttributeType;
import br.ufrn.coren.Entities.api.ConstAttribute;
import br.ufrn.coren.Entities.api.ContextEntity;
import br.ufrn.coren.Entities.api.NonConstAttribute;
import br.ufrn.coren.Exceptions.DAOException;

public class testhibernate {

	private static ContextEntityDaoInterface entityDao = new ContextEntityDao();
	private static ContextEntity entity1 = new ContextEntity();

	public static void main(String[] args) throws DAOException {

		saveEntity();

	}

	private static void saveEntity() throws DAOException {
		entity1.setName("smart_home");

		List<ConstAttribute> constatt = new ArrayList<>();

		ConstAttribute constAtt1 = new ConstAttribute();
		constAtt1.setAttName("room");
		//constAtt1.setType(AttributeType.INTEGER);
		constAtt1.setValue("room");

		constatt.add(constAtt1);

		entity1.setConstAttributes(constatt);

		List<NonConstAttribute> nConstAtt = new ArrayList<>();
		NonConstAttribute ncAtt1 = new NonConstAttribute();
		ncAtt1.setAttName("temperature");
		//ncAtt1.setType(AttributeType.INTEGER);

		NonConstAttribute ncAtt2 = new NonConstAttribute();
		ncAtt1.setAttName("has_people");
		//ncAtt1.setType(AttributeType.BOOLEAN);

		nConstAtt.add(ncAtt1);
		nConstAtt.add(ncAtt2);

		entity1 = entityDao.save(entity1);
	}
}
