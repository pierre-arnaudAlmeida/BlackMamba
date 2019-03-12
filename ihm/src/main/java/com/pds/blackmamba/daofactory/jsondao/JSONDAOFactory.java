package com.pds.blackmamba.daofactory.jsondao;

import com.pds.blackmamba.daofactory.AbstractDAOFactory;
import com.pds.blackmamba.daofactory.DAO;

public class JSONDAOFactory extends AbstractDAOFactory {

	@Override
	public DAO getBadgerDAO() {
		// TODO Auto-generated method stub
		return new JSONBadgerDAO();
	}

	@Override
	public DAO getCommonAreaDAO() {
		// TODO Auto-generated method stub
		return new JSONCommonAreaDAO();
	}

	@Override
	public DAO getEmployeeDAO() {
		// TODO Auto-generated method stub
		return new JSONEmployeeDAO();
	}

	@Override
	public DAO getManageDAO() {
		// TODO Auto-generated method stub
		return new JSONManageDAO();
	}

	@Override
	public DAO getMonitorDAO() {
		// TODO Auto-generated method stub
		return new JSONMonitorDAO();
	}

	@Override
	public DAO getReportDAO() {
		// TODO Auto-generated method stub
		return new JSONReportDAO();
	}

	@Override
	public DAO getResidentDAO() {
		// TODO Auto-generated method stub
		return new JSONResidentDAO();
	}

	@Override
	public DAO getSensorDAO() {
		// TODO Auto-generated method stub
		return new JSONSensorDAO();
	}

	@Override
	public DAO getSensorHistoricalDAO() {
		// TODO Auto-generated method stub
		return new JSONSensorHistoricalDAO();
	}

}
