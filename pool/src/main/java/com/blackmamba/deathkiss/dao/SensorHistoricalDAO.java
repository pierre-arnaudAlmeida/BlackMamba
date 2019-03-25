package com.blackmamba.deathkiss.dao;

import java.sql.Connection;

import com.blackmamba.deathkiss.entity.SensorHistorical;

public class SensorHistoricalDAO extends DAO<SensorHistorical> {

	public SensorHistoricalDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(String jsonString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String jsonString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String jsonString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String read(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readAll(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

}
