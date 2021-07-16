/**
 * 
 */
package com.jbent.peoplecentral.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.jbent.peoplecentral.model.pojo.Address;
import com.jbent.peoplecentral.model.pojo.Email;
import com.jbent.peoplecentral.model.pojo.PersonComplete;
import com.jbent.peoplecentral.model.pojo.Phone;

/**
 * @author jasontesser
 *
 */
public class PersonCompleteMapper extends PersonComplete implements RowMapper<PersonComplete> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415289097960421961L;

	@Override
	public PersonComplete mapRow(ResultSet rs, int rownum) throws SQLException {
		if(rs.getLong("id") > 0){
			setId(rs.getLong("id"));
			setFirstName(rs.getString("first_name"));
			setMiddleName(rs.getString("middle_name"));
			setLastName(rs.getString("last_name"));
		}
		if(rs.getLong("email_id") > 0){
			Email email = new Email();
			email.setId(rs.getLong("email_id"));
			email.setTypeId(rs.getLong("email_type"));
			email.setEmail(rs.getString("email"));
			if(emails == null){
				emails = new ArrayList<Email>();
			}
			emails.add(email);
		}
		if(rs.getLong("address_id") > 0){
			Address address = new Address();
			address.setId(rs.getLong("address_id"));
			address.setAddress1(rs.getString(rs.getString("address1")));
			address.setAddress2(rs.getString(rs.getString("address2")));
			address.setAddress3(rs.getString(rs.getString("address3")));
			address.setCity(rs.getString(rs.getString("city")));
			address.setState(rs.getString(rs.getString("state")));
			address.setZip(rs.getLong("zip"));
			address.setType(rs.getLong("address_type"));
			address.setCurrent(rs.getBoolean("current"));
			if(addresses == null){
				addresses = new ArrayList<Address>();
			}
			addresses.add(address);
		}
		
		if(rs.getLong("phone_id") > 0){
			Phone phone = new Phone();
			phone.setId(rs.getLong("phone_id"));
			phone.setTypeId(rs.getLong("phone_type"));
			phone.setInternational_code(rs.getInt("international_code"));
			phone.setArea_code(rs.getInt("area_code"));
			phone.setNumber(rs.getInt("number"));
			if(phones == null){
				phones = new ArrayList<Phone>();
			}
			phones.add(phone);
		}
		
		return this;
	}

}
