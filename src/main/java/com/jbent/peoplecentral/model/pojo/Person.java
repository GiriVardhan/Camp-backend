package com.jbent.peoplecentral.model.pojo;

import java.io.Serializable;
import java.util.List;

import com.jbent.peoplecentral.permission.Permissionable;

public class Person implements Serializable, Permissionable{

	private static final long serialVersionUID = 3828447528374967718L;
	
	protected long id;
	protected String firstName;
	protected String middleName;
	protected String lastName;
	protected int age;
	protected List<Address> addresses;
	protected List<Phone> phones;
	protected List<Email> emails;
	
	public Person() {}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Override
	public long getPermissionId() {
		return id;
	}

	@Override
	public String getPermissionType() {
		return "Person";
	}
	
}
