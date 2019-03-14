package com.pds.blackmamba.daofactory;

import com.pds.blackmamba.daofactory.jsondao.JSONDAOFactory;

public abstract class AbstractDAOFactory {
	
	public abstract DAO getBadgerDAO();
	public abstract DAO getCommonAreaDAO();
	public abstract DAO getEmployeeDAO();
	public abstract DAO getManageDAO();
	public abstract DAO getMonitorDAO();
	public abstract DAO getReportDAO();
	public abstract DAO getResidentDAO();
	public abstract DAO getSensorDAO();
	public abstract DAO getSensorHistoricalDAO();
	
	public static AbstractDAOFactory getFactory(FactoryType type) {
		if(type.equals(type.DAO_FACTORY))
			return new  DAOFactory();
		if(type.equals(type.JSON_DAO_FACTORY))
			return new JSONDAOFactory();
		return null;
	}
}
