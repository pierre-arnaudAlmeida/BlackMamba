package com.pds.blackmamba.dao;

import com.pds.blackmamba.bean.Badger;
import com.pds.blackmamba.bean.CommonArea;
import com.pds.blackmamba.bean.Employee;
import com.pds.blackmamba.bean.Manage;
import com.pds.blackmamba.bean.Monitor;
import com.pds.blackmamba.bean.Report;
import com.pds.blackmamba.bean.Resident;
import com.pds.blackmamba.bean.Sensor;
import com.pds.blackmamba.bean.SensorHistorical;

public class DAOFactory extends AbstractDAOFactory {

	@Override
	public DAO<Badger> getBadgerDAO() {
		// TODO Auto-generated method stub
		return new BadgerDAO();
	}

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
	public DAO<Manage> getManageDAO() {
		// TODO Auto-generated method stub
		return new ManageDAO();
	}

	@Override
	public DAO<Monitor> getMonitorDAO() {
		// TODO Auto-generated method stub
		return new MonitorDAO();
	}

	@Override
	public DAO<Report> getReportDAO() {
		// TODO Auto-generated method stub
		return new ReportDAO();
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
