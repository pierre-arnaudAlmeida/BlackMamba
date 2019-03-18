package com.pds.blackmamba.daofactory.jsondao;

import com.pds.blackmamba.bean.CommonArea;
import com.pds.blackmamba.bean.Employee;
import com.pds.blackmamba.bean.Resident;
import com.pds.blackmamba.bean.Sensor;
import com.pds.blackmamba.bean.SensorHistorical;
import com.pds.blackmamba.daofactory.AbstractDAOFactory;
import com.pds.blackmamba.daofactory.DAO;

public class JSONDAOFactory extends AbstractDAOFactory {

	@Override
	public DAO<CommonArea> getCommonAreaDAO() {
		// TODO Auto-generated method stub
		return new JSONCommonAreaDAO();
	}

	@Override
	public DAO<Employee> getEmployeeDAO() {
		// TODO Auto-generated method stub
		return new JSONEmployeeDAO();
	}

	@Override
	public DAO<Resident> getResidentDAO() {
		// TODO Auto-generated method stub
		return new JSONResidentDAO();
	}

	@Override
	public DAO<Sensor> getSensorDAO() {
		// TODO Auto-generated method stub
		return new JSONSensorDAO();
	}

	@Override
	public DAO<SensorHistorical> getSensorHistoricalDAO() {
		// TODO Auto-generated method stub
		return new JSONSensorHistoricalDAO();
	}

}
