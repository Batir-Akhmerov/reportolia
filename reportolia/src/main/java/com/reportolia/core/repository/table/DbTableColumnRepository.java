package com.reportolia.core.repository.table;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reportolia.base.repository.UpdatableRepository;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;


/**
 * 
 * The DbTableColumnRepository class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
public interface DbTableColumnRepository extends UpdatableRepository<DbTableColumn, Long> {
    
	public List<DbTableColumn> findByName(String name);
    
    @Query(
			"select c FROM DbTableColumn c WHERE c.dbTable.id = :tableId ORDER BY c.name"
    )
    public List<DbTableColumn> findByDbTableId(@Param("tableId") Long tableId);
    
    @Query(
			"select c FROM DbTableColumn c WHERE c.dbTable.id = :tableId AND c.name = :name ORDER BY c.name"
    )
    public List<DbTableColumn> findByDbTableIdAndName(@Param("tableId") Long tableId, @Param("name") String name);
    
    public List<DbTableColumn> findByDbTable(DbTable dbTable);
        
    public List<DbTableColumn> findByDbTableAndPk(DbTable dbTable, Boolean pk);
    
}
