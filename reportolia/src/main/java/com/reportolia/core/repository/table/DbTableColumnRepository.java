package com.reportolia.core.repository.table;

import java.util.List;

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
    
    List<DbTableColumn> findByName(String name);
    
    List<DbTableColumn> findByDbTable(DbTable dbTable);
    
    List<DbTableColumn> findByDbTableAndPk(DbTable dbTable, Boolean pk);
    
}
