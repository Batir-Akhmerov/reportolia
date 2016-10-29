/**
 * 
 */
package com.reportolia.core.handler.db.relationship;

import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.utils.CoreUtils;

/**
 * The RelationshipInfo class
 *
 * @author Batir Akhmerov
 * Created on Oct 27, 2016
 */
public class RelationshipInfo {
	
	private Long id;
	
	private String label;
	
	private Long tableId;
	private Long columnId;
	
	private DbTable table;
	private DbTableColumn column;
	
	private DbTable linkedTable;
	private DbTableColumn linkedColumn;
	private Long linkedTableId;
	private Long linkedColumnId;
	private Boolean oneToMany;
	private Boolean linkToFilter;
	
	public RelationshipInfo() {
		
	}
	
	public RelationshipInfo(Long tableId, DbTableRelationship rel) {
		this.id = rel.getId();
		this.label = rel.getLabel();
		this.tableId = tableId;
		this.oneToMany = tableId == rel.getParentTableId();
		this.linkToFilter = rel.getLinkToSecurityFilter();
		if (this.oneToMany) {
			this.table = rel.getParentTable();
			this.column = rel.getDbColumnParent();
			this.linkedColumn = rel.getDbColumnChild();
			this.linkedTable = rel.getChildTable();
		}
		else {
			this.table = rel.getChildTable();
			this.column = rel.getDbColumnChild();
			this.linkedColumn = rel.getDbColumnParent();
			this.linkedTable = rel.getParentTable();
		}
	}
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DbTableColumn getColumn() {
		return this.column;
	}
	public void setColumn(DbTableColumn column) {
		this.column = column;
	}
	public Boolean getOneToMany() {
		return this.oneToMany;
	}
	public void setOneToMany(Boolean oneToMany) {
		this.oneToMany = oneToMany;
	}
	public DbTable getLinkedTable() {
		return this.linkedTable;
	}
	public void setLinkedTable(DbTable linkedTable) {
		this.linkedTable = linkedTable;
	}
	public DbTableColumn getLinkedColumn() {
		return this.linkedColumn;
	}
	public void setLinkedColumn(DbTableColumn linkedColumn) {
		this.linkedColumn = linkedColumn;
	}

	public Long getTableId() {
		return this.tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getColumnId() {
		if (CoreUtils.isKeyNull(this.columnId) && this.column != null) {
			return this.column.getId();
		}
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Long getLinkedTableId() {
		if (CoreUtils.isKeyNull(this.linkedTableId) && this.linkedTable != null) {
			return this.linkedTable.getId();
		}
		return this.linkedTableId;
	}

	public void setLinkedTableId(Long linkedTableId) {
		this.linkedTableId = linkedTableId;
	}

	public Long getLinkedColumnId() {
		if (CoreUtils.isKeyNull(this.linkedColumnId) && this.linkedColumn != null) {
			return this.linkedColumn.getId();
		}
		return this.linkedColumnId;
	}

	public void setLinkedColumnId(Long linkedColumnId) {
		this.linkedColumnId = linkedColumnId;
	}

	public DbTable getTable() {
		return this.table;
	}

	public void setTable(DbTable table) {
		this.table = table;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getLinkToFilter() {
		return this.linkToFilter;
	}

	public void setLinkToFilter(Boolean linkToFilter) {
		this.linkToFilter = linkToFilter;
	}

}
