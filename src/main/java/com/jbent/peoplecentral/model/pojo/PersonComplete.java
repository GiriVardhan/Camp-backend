package com.jbent.peoplecentral.model.pojo;

import java.util.List;

import com.jbent.peoplecentral.permission.Permissionable;

public class PersonComplete extends Person implements Permissionable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8787392466430254625L;
	
	protected List<Address> addresses;
	protected List<Phone> phones;
	protected List<Email> emails;
	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}
	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	/**
	 * @return the phones
	 */
	public List<Phone> getPhones() {
		return phones;
	}
	/**
	 * @param phones the phones to set
	 */
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	/**
	 * @return the emails
	 */
	public List<Email> getEmails() {
		return emails;
	}
	/**
	 * @param emails the emails to set
	 */
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	
}
