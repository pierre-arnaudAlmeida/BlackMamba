package com.blackmamba.deathkiss.dao;

import java.sql.Connection;

import com.blackmamba.deathkiss.connectionpool.DataSource;
import com.blackmamba.deathkiss.connectionpool.JDBCConnectionPool;
import com.blackmamba.deathkiss.entity.CommonArea;
import com.blackmamba.deathkiss.entity.Employee;
import com.blackmamba.deathkiss.entity.Resident;
import com.blackmamba.deathkiss.entity.Sensor;
import com.blackmamba.deathkiss.entity.SensorHistorical;

public class DAOFactory extends AbstractDAOFactory {

	protected JDBCConnectionPool pool;
	protected final Connection con = null; //DataSource.getConnectionFromJDBC(pool);
	
	@Override
	public DAO<CommonArea> getCommonAreaDAO() {
		// TODO Auto-generated method stub
		return new CommonAreaDAO(con);
	}

	@Override
	public DAO<Employee> getEmployeeDAO() {
		// TODO Auto-generated method stub
		return new EmployeeDAO(con);
	}

	@Override
	public DAO<Resident> getResidentDAO() {
		// TODO Auto-generated method stub
		return new ResidentDAO(con);
	}

	@Override
	public DAO<Sensor> getSensorDAO() {
		// TODO Auto-generated method stub
		return new SensorDAO(con);
	}

	@Override
	public DAO<SensorHistorical> getSensorHistoricalDAO() {
		// TODO Auto-generated method stub
		return new SensorHistoricalDAO(con);
	}

}
