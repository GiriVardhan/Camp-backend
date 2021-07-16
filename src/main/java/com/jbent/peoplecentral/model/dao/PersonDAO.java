package com.jbent.peoplecentral.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.jbent.peoplecentral.exception.SqlParseException;
import com.jbent.peoplecentral.model.pojo.Address;
import com.jbent.peoplecentral.model.pojo.Email;
import com.jbent.peoplecentral.model.pojo.Person;
import com.jbent.peoplecentral.model.pojo.PersonComplete;
import com.jbent.peoplecentral.model.pojo.Phone;

//import com.jbent.peoplecentral.model.aspectized.pojo.Person;

public interface PersonDAO {

	public List<Map<String, Object>> getPersonByField(Person person);

	@Transactional(rollbackFor = Exception.class)
//	@Transactional(readOnly=true)
	@PreAuthorize("(#person.id == 0 and hasRole('ROLE_USER')) or hasPermission(#person, write)")
//	@PreAuthorize("hasRole('ROLE_USER')")
	public PersonComplete savePerson(Person person, List<Address> addresses, List<Email> emails, List<Phone> numbers) throws SQLException, SqlParseException;
	
}
