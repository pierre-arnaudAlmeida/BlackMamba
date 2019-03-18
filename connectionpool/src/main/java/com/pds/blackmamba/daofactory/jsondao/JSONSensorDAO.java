package com.pds.blackmamba.daofactory.jsondao;

import com.pds.blackmamba.bean.Sensor;
import com.pds.blackmamba.daofactory.DAO;

public class JSONSensorDAO extends DAO<Sensor>{

	@Override
	public boolean create(Sensor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Sensor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Sensor obj) {
		// TODO Auto-generated method stub
		//on transmet une variable de type boolean qui spécifie si on modifie les informations ou bien juste l'etat
		//quand on modifie un capteur on peut dire de qu'employee a gerer ou surveillé le capteur
		return false;
	}

	@Override
	public Sensor find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean save(int id, boolean sensorState, boolean sensorNextState) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean report(int idEmployee, int idSensor) {
		// TODO Auto-generated method stub
		// Quand une fenetre popup s'ouvre pour prevenir d'un alerte, on utilise la methode report() en fonction de la
		// ou les personnes qui ont reçu la notification
		return false;
	}

}
