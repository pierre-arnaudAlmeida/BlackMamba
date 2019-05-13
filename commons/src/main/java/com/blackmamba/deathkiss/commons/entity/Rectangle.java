package com.blackmamba.deathkiss.commons.entity;

public class Rectangle {

	/**
	 * Different parameters used
	 */
	private int idRectangle = 0;
	private String nameRectangle = "";
	private int idCommonArea = 0;
	
	/**
	 * 
	 * @param idRectangle
	 * @param nameRectangle
	 * @param idCommonArea
	 */
	public Rectangle(int idRectangle, String nameRectangle, int idCommonArea) {
		super();
		this.idRectangle = idRectangle;
		this.nameRectangle = nameRectangle;
		this.idCommonArea = idCommonArea;
	}
	/**
	 * 
	 * @return
	 */
	public int getIdRectangle() {
		return idRectangle;
	}
	/**
	 * 
	 * @param idRectangle
	 */
	public void setIdRectangle(int idRectangle) {
		this.idRectangle = idRectangle;
	}
	/**
	 * 
	 * @return
	 */
	public String getNameRectangle() {
		return nameRectangle;
	}
	/**
	 * 
	 * @param nameRectangle
	 */
	public void setNameRectangle(String nameRectangle) {
		this.nameRectangle = nameRectangle;
	}
	/**
	 * 
	 * @return
	 */
	public int getIdCommonArea() {
		return idCommonArea;
	}
	/**
	 * 
	 * @param idCommonArea
	 */
	public void setIdCommonArea(int idCommonArea) {
		this.idCommonArea = idCommonArea;
	}

}
