package com.pds.blackmamba.daofactory;

import com.pds.blackmamba.bean.CommonArea;
import com.pds.blackmamba.bean.Employee;
import com.pds.blackmamba.bean.Resident;
import com.pds.blackmamba.bean.Sensor;
import com.pds.blackmamba.bean.SensorHistorical;
import com.pds.blackmamba.daofactory.dao.DAOFactory;
import com.pds.blackmamba.daofactory.jsondao.JSONDAOFactory;

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
