/**
 * 
 */
package com.jbent.peoplecentral.model.pojo;

/**
 * @author jasontesser
 *
 */
public class Address extends AddressType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1484399858088427067L;

	private long id;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String state;
	private long zip;
	private long type;
	private boolean current;
	
	public Address() {}

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
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the current
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}

	/**
	 * @return the zip
	 */
	public long getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(long zip) {
		this.zip = zip;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getType() {
		return type;
	}
	
}
