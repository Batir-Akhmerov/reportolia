/**
 * 
 */
package com.reportolia.core.handler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.repository.table.DbTableRepository;

/**
 * The DbManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Component
public class DbManager implements DbHandler {
	 
	 @Resource protected DbTableRepository tableRepository;
	 @Resource protected DbTableColumnRepository tableColumnRepository;
	 @Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	 
	 public List<DbTable> getTableList(String name) {
		 List<DbTable> list = this.tableRepository.findByName(name);
		 return list;
	 }
	 
	 public List<DbTableRelationship> getTableChildRelationshipList(long  tableId) {
		 List<DbTableRelationship> list = this.tableRelationshipRepository.findByParentTable(tableId);
		 return list;
	 }
}
