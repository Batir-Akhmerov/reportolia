/**
 * 
 */
package com.reportolia.core.handler.sql;

import org.springframework.stereotype.Component;

/**
 * The MetadataManager class
 *
 * @author Batir Akhmerov
 * Created on Oct 10, 2016
 */
@Component
public class SqlExecutorManager implements SqlExecutorHandler {
	
	/*
	
	public static final String TYPE_TABLE = "TABLE";
	public static final String TYPE_VIEW = "VIEW";
	
	@Resource protected DataSource dataSource;
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	
	
	
	
	protected SysTableMapper sysTableMapper = new SysTableMapper();
	protected SysColumnMapper sysColumnMapper = new SysColumnMapper();
	
	
	
	public List<SysTable> getSysTableList()  {
		Connection connection = null;
		ResultSet result = null;
		String   catalog          = null;
		String   schemaPattern    = null;
		String   tableNamePattern = null;
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
		
		for (SysTable sysTable: json.getList()) {
			if (!sysTable.isSelected()) {
				continue;
			}
			DbTable dbTable = ListUtils.getFirst(this.tableRepository.findByName(sysTable.getName()));
			if (dbTable == null) {
				dbTable = new DbTable();
				dbTable.setName(sysTable.getName());
				dbTable.setLabel(sysTable.getName());
				this.tableRepository.save(dbTable);
			}
			if (CollectionUtils.isEmpty(sysTable.getColumnList())) { // add all columns if no columns were selected
				List<SysColumn> colList = getSysColumnList(sysTable.getName());
				ListUtils.setProperty(colList, "selected", true);
				sysTable.setColumnList(colList);				
			}
			
			Map<String, DataType> dataTypesMap = new HashMap<>();
			saveSysColumnList(dbTable, sysTable.getColumnList(), dataTypesMap);			
		}
	}
	
	
	
	public List<SysColumn> getSysColumnList(String tableName) {
		Connection connection = null;
		ResultSet result = null;
		String   catalog          = null;
		String   schemaPattern    = null;
		String   columnNamePattern = null;
		List<SysColumn> list = new ArrayList<>();
		try {
			connection = this.dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			
			result = databaseMetaData.getColumns(catalog, schemaPattern, tableName, columnNamePattern);
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
	*/
	/*
	public void saveSysColumnList(JsonListForm<SysColumn> json) {
		if (json == null || CollectionUtils.isEmpty(json.getList())) {
			return;
		}
		Map<String, DataType> dataTypesMap = new HashMap<>();
		String tableName = json.getList().get(0).getTableName();
		DbTable dbTable = ListUtils.getFirst(this.tableRepository.findByName(tableName));
		saveSysColumnList(dbTable, json.getList(), dataTypesMap);
	}
	
	*/
	/*
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
	*/
}
