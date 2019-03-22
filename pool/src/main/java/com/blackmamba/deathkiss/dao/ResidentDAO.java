package com.blackmamba.deathkiss.dao;

import java.sql.Connection;

import com.blackmamba.deathkiss.entity.Resident;

public class ResidentDAO extends DAO<Resident> {

	public ResidentDAO(Connection con) {
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
	public String find(String jsonString) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean badger(int idResident, int idSensor) {
		// TODO Auto-generated method stub
		// Quand on badge on ajoute une date
		return false;
	}
}
