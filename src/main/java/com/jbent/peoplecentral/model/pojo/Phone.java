/**
 * 
 */
package com.jbent.peoplecentral.model.pojo;

/**
 * @author jasontesser
 *
 */
public class Phone extends PhoneType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3640981132321533371L;

	private long id;
	private int international_code;
	private int area_code;
	private int number;
	
	public Phone() {}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the international_code
	 */
	public int getInternational_code() {
		return international_code;
	}

	/**
	 * @param internationalCode the international_code to set
	 */
	public void setInternational_code(int internationalCode) {
		international_code = internationalCode;
	}

	/**
	 * @return the area_code
	 */
	public int getArea_code() {
		return area_code;
	}

	/**
	 * @param areaCode the area_code to set
	 */
	public void setArea_code(int areaCode) {
		area_code = areaCode;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
}
