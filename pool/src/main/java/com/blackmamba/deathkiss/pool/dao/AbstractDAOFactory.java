package com.blackmamba.deathkiss.pool.dao;

import com.blackmamba.deathkiss.pool.entity.CommonArea;
import com.blackmamba.deathkiss.pool.entity.Employee;
import com.blackmamba.deathkiss.pool.entity.Resident;
import com.blackmamba.deathkiss.pool.entity.Sensor;
import com.blackmamba.deathkiss.pool.entity.SensorHistorical;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
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