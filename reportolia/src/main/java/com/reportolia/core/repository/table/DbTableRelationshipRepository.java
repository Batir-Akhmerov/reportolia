package com.reportolia.core.repository.table;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.base.UpdatableRepository;


/**
 * 
 * The DbTableRelationshipRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbTableRelationshipRepository extends UpdatableRepository<DbTableRelationship, Long> {
    
	@Query(
			"select r FROM DbTableRelationship r WHERE r.dbColumnParent.dbTable.id = :tableId"
    )
    public List<DbTableRelationship> findByParentTable(@Param("tableId") long tableId);
	
	//List<DbTableRelationship> findByDbTableRelationshipGroup(DbTableRelationship dbTableRelationshipGroup);
	
	//public List<DbTableRelationship> findByGroupRelationshipId(Long groupRelationshipId);
	
    
}
