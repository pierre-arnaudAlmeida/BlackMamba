package com.pds.blackmamba.daofactory.dao;

import com.pds.blackmamba.bean.CommonArea;
import com.pds.blackmamba.bean.Employee;
import com.pds.blackmamba.bean.Resident;
import com.pds.blackmamba.bean.Sensor;
import com.pds.blackmamba.bean.SensorHistorical;
import com.pds.blackmamba.daofactory.AbstractDAOFactory;
import com.pds.blackmamba.daofactory.DAO;

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
