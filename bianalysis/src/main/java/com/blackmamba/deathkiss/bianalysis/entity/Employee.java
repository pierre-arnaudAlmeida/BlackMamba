package com.blackmamba.deathkiss.bianalysis.entity;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Employee {

	/**
	 * Different parameters used
	 */
	private int idEmployee = 0;
	private String lastnameEmployee = "";
	private String nameEmployee = "";
	private String password = "";
	private String poste = "";

	/**
	 * Constructor
	 * 
	 * @param idEmployee
	 * @param lastnameEmployee
	 * @param nameEmployee
	 * @param password
	 * @param poste
	 */
	public Employee(int idEmployee, String lastnameEmployee, String nameEmployee, String password, String poste) {
		super();
		this.idEmployee = idEmployee;
		this.lastnameEmployee = lastnameEmployee;
		this.nameEmployee = nameEmployee;
		this.password = password;
		this.poste = poste;
	}

	/**
	 * Constructor
	 */
	public Employee() {
	}

	/**
	 * @return the idEmployee
	 */
	public int getIdEmployee() {
		return idEmployee;
	}

	/**
	 * @param idEmployee the idEmployee to set
	 */
	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	/**
	 * @return the lastnameEmployee
	 */
	public String getLastnameEmployee() {
		return lastnameEmployee;
	}

	/**
	 * @param lastnameEmployee the lastnameEmployee to set
	 */
	public void setLastnameEmployee(String lastnameEmployee) {
		this.lastnameEmployee = lastnameEmployee;
	}

	/**
	 * @return the nameEmployee
	 */
	public String getNameEmployee() {
		return nameEmployee;
	}

	/**
	 * @param nameEmployee the nameEmployee to set
	 */
	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the poste
	 */
	public String getPoste() {
		return poste;
	}

	/**
	 * @param poste the poste to set
	 */
	public void setPoste(String poste) {
		this.poste = poste;
	}
}