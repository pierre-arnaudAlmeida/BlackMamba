package com.blackmamba.deathkiss.dao;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.blackmamba.deathkiss.entity.Message;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class MessageDAO extends DAO<Message> {

	private static final Logger logger = LogManager.getLogger(MessageDAO.class);
	
	public MessageDAO(Connection con) {
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
