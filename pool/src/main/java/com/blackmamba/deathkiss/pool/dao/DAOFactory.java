package com.blackmamba.deathkiss.pool.dao;

import java.sql.Connection;

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
public class DAOFactory extends AbstractDAOFactory {

	protected final Connection con = null;

	/**
	 * Return a CommonArea Object
	 */
	@Override
	public DAO<CommonArea> getCommonAreaDAO() {
		return new CommonAreaDAO(con);
	}

	/**
	 * Return a Employee Object
	 */
	@Override
	public DAO<Employee> getEmployeeDAO() {
		return new EmployeeDAO(con);
	}

	/**
	 * Return a Resident Object
	 */
	@Override
	public DAO<Resident> getResidentDAO() {
		return new ResidentDAO(con);
	}

	/**
	 * Return a Sensor Object
	 */
	@Override
	public DAO<Sensor> getSensorDAO() {
		return new SensorDAO(con);
	}

	/**
	 * Return a SensorHistorical Object
	 */
	@Override
	public DAO<SensorHistorical> getSensorHistoricalDAO() {
		return new SensorHistoricalDAO(con);
	}

}