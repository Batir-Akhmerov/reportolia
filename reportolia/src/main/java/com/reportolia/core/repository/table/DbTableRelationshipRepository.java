package com.reportolia.core.repository.table;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;


/**
 * 
 * The DbTableRelationshipRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbTableRelationshipRepository extends UpdatableRepository<DbTableRelationship, Long> {
    
	@Query(
			"select r FROM DbTableRelationship r WHERE r.dbColumnParent.dbTable.id = :tableId OR r.dbColumnChild.dbTable.id = :tableId"
    )
    public List<DbTableRelationship> findByTable(@Param("tableId") Long tableId);
	
	@Query(
			"select r FROM DbTableRelationship r WHERE r.dbColumnParent.dbTable.id = :tableId"
    )
    public List<DbTableRelationship> findByParentTableId(@Param("tableId") Long tableId);
	
	@Query(
			"select r FROM DbTableRelationship r WHERE r.dbColumnChild.dbTable.id = :tableId AND r.linkToSecurityFilter = true"
    )
    public List<DbTableRelationship> findSecuredByChildTable(@Param("tableId") Long tableId);
	
	List<DbTableRelationship> findByDbColumnParentAndDbColumnChild(DbTableColumn dbColumnParent,DbTableColumn dbColumnChild);
	
	//List<DbTableRelationship> findByDbTableRelationshipGroup(DbTableRelationship dbTableRelationshipGroup);
	
	//public List<DbTableRelationship> findByGroupRelationshipId(Long groupRelationshipId);
	
    
}
