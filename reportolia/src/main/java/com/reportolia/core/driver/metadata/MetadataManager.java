/**
 * 
 */
package com.reportolia.core.driver.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.reportolia.core.handler.db.DbHandler;
import com.reportolia.core.model.datatype.DataType;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.ListUtils;
import com.reportolia.core.web.controllers.JsonListForm;

/**
 * The MetadataManager class
 *
 * @author Batir Akhmerov
 * Created on Oct 10, 2016
 */
@Component
public class MetadataManager implements MetadataHandler {
	
	public static final String TYPE_TABLE = "TABLE";
	public static final String TYPE_VIEW = "VIEW";
	
	@Resource protected DataSource dataSource;
	@Resource protected DbHandler dbHandler;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	
	
	
	
	
	protected SysTableMapper sysTableMapper = new SysTableMapper();
	protected SysColumnMapper sysColumnMapper = new SysColumnMapper();
	protected SysRelationMapper sysRelationMapper = new SysRelationMapper();
	
	public List<SysTable> getSysTableList() {
		return getSysTableList(null);
	}
	
	public List<SysTable> getSysTableList(String tableNamePattern) {
		Connection connection = null;
		ResultSet result = null;
		String   catalog          = null;
		String   schemaPattern    = null;
		String[] types            = new String[]{TYPE_TABLE, TYPE_VIEW};
		List<SysTable> list = new ArrayList<>();
		try {
			connection = this.dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			result = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			list = mapResults(result, this.sysTableMapper);
			//setColumnsMetadata(databaseMetaData, list);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {
                	// do nothing
                }
            }
        }

		
		return list;
	}
	
	@Transactional
	public void saveSysTableList(JsonListForm<SysTable> json) {
		if (json == null || CollectionUtils.isEmpty(json.getList())) {
			return;
		}
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			
			addSysTables(json.getList(), databaseMetaData);

			List<DbTable> dbTableList = this.tableRepository.findAll();
			
			addPrimaryKeys(dbTableList, databaseMetaData);

			addSysRelationships(dbTableList, databaseMetaData);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {
                	// do nothing
                }
            }
        }
	}
	
	protected void addPrimaryKeys(List<DbTable> dbTableList, DatabaseMetaData databaseMetaData) throws SQLException {
		for (DbTable dbTable: ListUtils.safeList(dbTableList)) {
			ResultSet result = databaseMetaData.getPrimaryKeys(null, null, dbTable.getName());
			List<SysColumn> list = mapResults(result, this.sysColumnMapper);
			for (SysColumn sysColumn: ListUtils.safeList(list)) {
				DbTableColumn dbColumn = this.dbHandler.getColumn(dbTable, sysColumn.getName());
				if (dbColumn != null) {
					dbColumn.setPk(true);
					this.tableColumnRepository.save(dbColumn);
				}
			}
		}
	}
	
	protected void addSysTables(List<SysTable> list, DatabaseMetaData databaseMetaData) throws SQLException {
		for (SysTable sysTable: ListUtils.safeList(list)) {
			if (!sysTable.isSelected()) {
				continue;
			}
			// save dbTable if not exists
			DbTable dbTable = ListUtils.getFirst(this.tableRepository.findByName(sysTable.getName()));
			if (dbTable == null) {
				dbTable = new DbTable();
				dbTable.setName(sysTable.getName());
				dbTable.setLabel(sysTable.getName());
				this.tableRepository.save(dbTable);
			}
			
			// add table columns
			if (CollectionUtils.isEmpty(sysTable.getColumnList())) { // add all columns if no columns were selected
				List<SysColumn> colList = getSysColumnList(sysTable.getName(), databaseMetaData);
				ListUtils.setProperty(colList, "selected", true);
				sysTable.setColumnList(colList);				
			}
			
			Map<String, DataType> dataTypesMap = new HashMap<>();
			saveSysColumnList(dbTable, sysTable.getColumnList(), dataTypesMap);			
		}
	}
	
	protected void addSysRelationships(List<DbTable> dbTableList, DatabaseMetaData databaseMetaData) throws SQLException {
		List<SysRelation> relList = getSysRelationList(databaseMetaData);
		for (SysRelation sysRel: ListUtils.safeList(relList)) {
			DbTable parentDbTable = ListUtils.findByProperty(dbTableList, "name", sysRel.getParentTableName());
			if (parentDbTable == null) {
				continue;
			}
			DbTable childDbTable = ListUtils.findByProperty(dbTableList, "name", sysRel.getChildTableName());
			if (childDbTable == null) {
				continue;
			}
			
			// find pk column
			DbTableColumn parentDbColumn = this.dbHandler.getColumn(parentDbTable, sysRel.getParentColumnName());
			if (parentDbColumn == null) {
				continue;
			}
			
			// find fk column
			DbTableColumn childDbColumn = this.dbHandler.getColumn(childDbTable, sysRel.getChildColumnName());
			if (childDbColumn == null) {
				continue;
			}
			
			// check if relationship already exists
			if (!ListUtils.isEmpty(this.tableRelationshipRepository.findByDbColumnParentAndDbColumnChild(parentDbColumn, childDbColumn))) {
				continue; 
			}
			
			// save new relationship
			DbTableRelationship dbRel = new DbTableRelationship();
			dbRel.setDbColumnParent(parentDbColumn);
			dbRel.setDbColumnChild(childDbColumn);
			this.tableRelationshipRepository.save(dbRel);
		}
	}
	
	
	
	public List<SysColumn> getSysColumnList(String tableName) {
		Connection connection = null;
		List<SysColumn> list = null;
		try {
			connection = this.dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			
			list = getSysColumnList(tableName, databaseMetaData);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
            if (connection != null) {
                try {
                	connection.close();
                } catch (SQLException e) {
                	// do nothing
                }
            }
        }
		return list;
	}
	
	protected List<SysColumn> getSysColumnList(String tableName, DatabaseMetaData databaseMetaData) throws SQLException {
		String   catalog          = null;
		String   schemaPattern    = null;
		String   columnNamePattern = null;
		List<SysColumn> list = new ArrayList<>();
			
		ResultSet result = databaseMetaData.getColumns(catalog, schemaPattern, tableName, columnNamePattern);
		list = mapResults(result, this.sysColumnMapper);
		
		DbTable dbTable = ListUtils.getFirst(this.tableRepository.findByName(tableName));
		List<DbTableColumn> dbColList = this.tableColumnRepository.findByDbTable(dbTable);
		for (DbTableColumn dbCol: ListUtils.safeList(dbColList)) {
			int index = ListUtils.indexByProperty(list, "name", dbCol.getName());
			if (index != -1) {
				list.remove(index);
			}
		}
		ListUtils.setProperty(list, "tableName", tableName);
		
		return list;
	}
	
	protected List<SysRelation> getSysRelationList(DatabaseMetaData databaseMetaData) throws SQLException {
		String   catalog = null;
		String   schema  = null;
		
		List<DbTable> dbTableList = this.tableRepository.findAll();
		List<SysRelation> list = new ArrayList<>();
		
		for (DbTable dbTable: ListUtils.safeList(dbTableList)) {
			ResultSet result = databaseMetaData.getImportedKeys(catalog, schema, dbTable.getName());
			List<SysRelation> tmpList = mapResults(result, this.sysRelationMapper);
			if (!ListUtils.isEmpty(tmpList)) {
				list.addAll(tmpList);
			}
		}	
		return list;
	}
	
	
	protected void saveSysColumnList(DbTable dbTable, List<SysColumn> list, Map<String, DataType> dataTypesMap) {
		if (ListUtils.getSize(list) == 0) {
			return;
		}
		for (SysColumn sysColumn: list) {
			if (!sysColumn.isSelected()) {
				continue;
			}
			String metadataTypeName = sysColumn.getType();
			DataType dataType = dataTypesMap.get(metadataTypeName);
			if (dataType == null) {
				dataType = getByMetadataType(metadataTypeName);
				dataTypesMap.put(metadataTypeName, dataType);
			}
				
			DbTableColumn dbColumn = new DbTableColumn();
			dbColumn.setDbTable(dbTable);
			dbColumn.setDataType(dataType);
			dbColumn.setName(sysColumn.getName());
			dbColumn.setLabel(sysColumn.getName());
			this.tableColumnRepository.save(dbColumn);
		}
	}

	
	protected void setColumnsMetadata(DatabaseMetaData databaseMetaData, List<SysTable> tableList) throws SQLException {
		for (SysTable sysTb : tableList) {
			ResultSet result = databaseMetaData.getColumns(null, null, sysTb.getName(), null);
		    sysTb.setColumnList(mapResults(result, this.sysColumnMapper));
	    }
	}
	
	protected <T extends Object> List<T> mapResults(ResultSet result, RowMapper<T> mapper) throws SQLException {
		if (result == null) {
			return null;
		}
		List<T> list = new ArrayList<>();
		while(result.next()) {
		    list.add(mapper.mapRow(result, 0));
		}
		result.close();
		return list;
	}
	
	protected DataType getByMetadataType(String metadataTypeName) {
		switch(metadataTypeName) {
			case "VARCHAR": return DataType.TXT;
			case "CHAR": return DataType.TXT;
			case "LONGVARCHAR": return DataType.TXT;
			
			case "NUMERIC": return DataType.NUM;
			case "REAL": return DataType.NUM;
			case "FLOAT": return DataType.NUM;
			case "DOUBLE": return DataType.NUM;
			
			case "TINYINT": return DataType.INT;
			case "SMALLINT": return DataType.INT;
			case "INTEGER": return DataType.INT;
			case "BIGINT": return DataType.INT;
			
			case "DATE": return DataType.DATE;
			case "TIME": return DataType.DATE;
			case "TIMESTAMP": return DataType.DATE;
			
			case "BIT": return DataType.BOOL;
			case "BOOLEAN": return DataType.BOOL;
			
			default: throw new RuntimeException("MetaData Type [" + metadataTypeName  + "] is not supported!]");
		}
	}
}
