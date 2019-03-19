package com.blackmamba.deathkiss.dao.json;

import com.blackmamba.deathkiss.dao.AbstractDAOFactory;
import com.blackmamba.deathkiss.dao.DAO;
import com.blackmamba.deathkiss.dao.json.JSONCommonAreaDAO;
import com.blackmamba.deathkiss.dao.json.JSONEmployeeDAO;
import com.blackmamba.deathkiss.dao.json.JSONResidentDAO;
import com.blackmamba.deathkiss.dao.json.JSONSensorDAO;
import com.blackmamba.deathkiss.dao.json.JSONSensorHistoricalDAO;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.entity.Resident;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorHistorical;

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
