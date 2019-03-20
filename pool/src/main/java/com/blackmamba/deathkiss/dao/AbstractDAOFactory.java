package com.blackmamba.deathkiss.dao;

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

	public static AbstractDAOFactory getFactory() {
			return new DAOFactory();
	}
}
