package com.reportolia.core.repository.table;

import java.util.List;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.repository.base.UpdatableRepository;


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
    
}
