/**
 * 
 */
package com.jbent.peoplecentral.model.manager;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.pojo.EntityType;
import com.jbent.peoplecentral.model.pojo.Import;
import com.jbent.peoplecentral.model.pojo.ImportEntitiesFailedRow;



/**
 * @author RaviT
 *
 */
public interface ImportManager {
	
	/** @param reader
	 * @param entityType
	 * @param uid
	 * returns void
	 * @throws DataException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void saveBulkEntities(CsvReader reader,EntityType entityType,String uid,List<EntityType> relatedEntityTypes) throws InterruptedException ,DataException;
	
	/** @param importID
	 * returns the ImportResults
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	public Import  fetchImportResults(String importID) throws DataException;

	/** @param importID
	 * returns the FailedImportResults
	 * @throws DataException
	 */
	@Transactional(readOnly=true)
	public List<ImportEntitiesFailedRow>  fetchImportFailed(String importID) throws DataException;

	
	
}
