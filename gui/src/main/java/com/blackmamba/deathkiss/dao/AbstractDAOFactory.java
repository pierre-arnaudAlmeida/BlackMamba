package com.blackmamba.deathkiss.dao;

import com.blackmamba.deathkiss.dao.AbstractDAOFactory;
import com.blackmamba.deathkiss.dao.DAO;
import com.blackmamba.deathkiss.dao.FactoryType;
import com.blackmamba.deathkiss.dao.json.JSONDAOFactory;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.entity.Resident;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorHistorical;

public abstract class AbstractDAOFactory {

	public abstract DAO<CommonArea> getCommonAreaDAO();

	public abstract DAO<Employee> getEmployeeDAO();

	public abstract DAO<Resident> getResidentDAO();

	public abstract DAO<Sensor> getSensorDAO();

	public abstract DAO<SensorHistorical> getSensorHistoricalDAO();

	public static AbstractDAOFactory getFactory(FactoryType type) {
		if (type.equals(FactoryType.DAO_FACTORY))
			return new DAOFactory();
		if (type.equals(FactoryType.JSON_DAO_FACTORY))
			return new JSONDAOFactory();
		return null;
	}
}
