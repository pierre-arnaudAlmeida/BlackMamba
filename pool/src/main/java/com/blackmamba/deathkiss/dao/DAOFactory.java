package com.blackmamba.deathkiss.dao;

import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.entity.Resident;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorHistorical;

public class DAOFactory extends AbstractDAOFactory {

	@Override
	public DAO<CommonArea> getCommonAreaDAO() {
		// TODO Auto-generated method stub
		return new CommonAreaDAO();
	}

	@Override
	public DAO<Employee> getEmployeeDAO() {
		// TODO Auto-generated method stub
		return new EmployeeDAO();
	}

	@Override
	public DAO<Resident> getResidentDAO() {
		// TODO Auto-generated method stub
		return new ResidentDAO();
	}

	@Override
	public DAO<Sensor> getSensorDAO() {
		// TODO Auto-generated method stub
		return new SensorDAO();
	}

	@Override
	public DAO<SensorHistorical> getSensorHistoricalDAO() {
		// TODO Auto-generated method stub
		return new SensorHistoricalDAO();
	}

}
