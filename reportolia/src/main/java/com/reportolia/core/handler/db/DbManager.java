/**
 * 
 */
package com.reportolia.core.handler.db;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reportolia.core.driver.metadata.MetadataHandler;
import com.reportolia.core.handler.db.mapper.DbTableTalationshipToRelationshipInfoMapper;
import com.reportolia.core.handler.db.relationship.RelationshipInfo;
import com.reportolia.core.model.table.DbTable;
import com.reportolia.core.model.table.DbTableColumn;
import com.reportolia.core.model.table.DbTableRelationship;
import com.reportolia.core.repository.table.DbTableColumnRepository;
import com.reportolia.core.repository.table.DbTableRelationshipRepository;
import com.reportolia.core.repository.table.DbTableRepository;
import com.reportolia.core.utils.CoreUtils;
import com.reportolia.core.utils.ExceptionUtilsService;
import com.reportolia.core.utils.ListUtils;
import com.reportolia.core.utils.MessageConstants;
import com.reportolia.core.utils.MessageResourceHandler;
import com.reportolia.core.web.controllers.base.datatable.JsonForm;
import com.reportolia.core.web.controllers.base.datatable.JsonSelectOptionsForm;
import com.reportolia.core.web.controllers.table.column.TableColumnListJsonForm;

/**
 * The DbManager class
 *
 * @author Batir Akhmerov
 * Created on Nov 13, 2015
 */
@Service
public class DbManager implements DbHandler {
	 
	@Resource protected DbTableRepository tableRepository;
	@Resource protected DbTableColumnRepository tableColumnRepository;
	@Resource protected DbTableRelationshipRepository tableRelationshipRepository;
	@Resource protected MetadataHandler metadataHandler;
	@Resource protected ExceptionUtilsService exceptionUtils;
	@Resource protected MessageResourceHandler messageResourceHandler;
	
	private DbTableTalationshipToRelationshipInfoMapper dbToRelationshipInfoMapper = new DbTableTalationshipToRelationshipInfoMapper();
	 
	/*************************************************/
	/** TABLES ***************************************/
	/*************************************************/	 
	public List<DbTable> getTableList(JsonForm form) {
		 List<DbTable> list = this.tableRepository.findAll();
		 return list;
	}
	
	public Page<DbTable> getTableList(JsonSelectOptionsForm form) {
		 Page<DbTable> list = this.tableRepository.findByNameContainsAllIgnoreCase(form.getSearchTerm(), form);
		 return list;
	}
	 
	public List<DbTable> getTableList(String name) {
		 List<DbTable> list = this.tableRepository.findByName(name);
		 return list;
	}
	
	@Transactional
	public DbTable saveTable(DbTable bean) {
		this.exceptionUtils.assertFalse(bean == null, "Table argument is required!");
		this.exceptionUtils.validateRequired(bean.getName(), MessageConstants.FORM_NAME);
		this.exceptionUtils.validateNotEmpty(
			this.metadataHandler.getSysTableList(bean.getName()),
			MessageConstants.ERROR_TB_NOT_EXISTS,
			bean.getName()
		);
		DbTable beanToSave = bean;
		if (bean.isNewBean()) {
			this.exceptionUtils.validateEmpty(
				getTableList(bean.getName()),
				MessageConstants.ERROR_TB_DUPLICATE,
				bean.getName()
			);
		}
		else {
			DbTable oldBean = this.tableRepository.findById(bean.getId());
			this.exceptionUtils.validateFalse(oldBean == null, MessageConstants.ERROR_TB_NOT_EXISTS, bean.getName());
			CoreUtils.copyProperties(bean, oldBean, "label", "description", "secured", "securityFilter");
			beanToSave = oldBean;
		}
		
		beanToSave = this.tableRepository.save(beanToSave);
		return beanToSave;
	}
	
	@Transactional
	public void deleteTable(DbTable  bean) {
		DbTable oldBean = this.tableRepository.findById(bean.getId());
		this.exceptionUtils.validateEmpty(
			getColumnList(oldBean),
			MessageConstants.ERROR_TB_NOT_REMOVABLE,
			oldBean.getName()
		);
		this.tableRepository.delete(oldBean);
	}
	
	/*************************************************/
	/** COLUMNS **************************************/
	/*************************************************/
	
	public List<DbTableColumn> getColumnList(TableColumnListJsonForm form) {
		 DbTable table = this.tableRepository.findById(form.getTableId());
		 return getColumnList(table);
	}
	
	public List<DbTableColumn> getColumnList(DbTable table) {
		 if (table == null) {
			 return null;
		 }
		 if (ListUtils.isEmpty(table.getDbTableColumns())) {
			 table.setDbTableColumns(this.tableColumnRepository.findByDbTable(table));
		 }
		 return table.getDbTableColumns();
	}
	 
	public DbTableColumn getColumn(DbTable table, String columnName) {
		 List<DbTableColumn> list = getColumnList(table);
		 return ListUtils.findByProperty(list, "name", columnName);
	}
	
	@Transactional
	public DbTableColumn saveColumn(DbTableColumn bean, long tableId) {
		this.exceptionUtils.assertFalse(bean == null, "Column argument is required!");
		
		DbTableColumn beanToSave = bean;
		if (bean.isNewBean()) {
			this.exceptionUtils.assertFalse(CoreUtils.isKeyNull(tableId), "Column argument is required!");
			DbTable table = this.tableRepository.findById(tableId);
			this.exceptionUtils.assertTrue(table != null, "Cannot find table [%s]!", tableId);
			
			this.exceptionUtils.validateRequired(bean.getLabel(), MessageConstants.FORM_NAME);
			bean.setName(bean.getLabel());
			this.exceptionUtils.validateEmpty(
					this.tableColumnRepository.findByDbTableIdAndName(tableId, bean.getName()),
				MessageConstants.ERROR_TB_DUPLICATE,
				bean.getName()
			);
			bean.setDbTable(table);
		}
		else {
			DbTableColumn oldBean = this.tableColumnRepository.findById(bean.getId());
			this.exceptionUtils.validateFalse(oldBean == null, MessageConstants.ERROR_ELEMENT_NOT_EXISTS, bean.getName());
			CoreUtils.copyProperties(bean, oldBean, "label");
			beanToSave = oldBean;
		}
		
		beanToSave = this.tableColumnRepository.save(beanToSave);
		return beanToSave;
	}
	
	@Transactional
	public void deleteColumn(DbTableColumn  bean, long tableId) {
		DbTable table = this.tableRepository.findById(tableId);
		DbTableColumn oldBean = this.tableColumnRepository.findById(bean.getId());
		table.getDbTableColumns().remove(oldBean);
		this.tableRepository.save(table);
	}
	
	 
	/*************************************************/
	/** RELATIONSHIPS ********************************/
	/*************************************************/	
	public List<RelationshipInfo> getTableRelationshipInfoList(long  tableId) {
		 List<DbTableRelationship> list = this.tableRelationshipRepository.findByTable(tableId);
		 List<RelationshipInfo> infoList = ListUtils.remap(list, tableId, this.dbToRelationshipInfoMapper);
		 return infoList;
	}
	
	public RelationshipInfo saveTableRelationship(RelationshipInfo info) {
		this.exceptionUtils.assertFalse(CoreUtils.isKeyNull(info.getTableId()), "TableId argument is required!");
			
		DbTableRelationship bean = null;
		if (CoreUtils.isKeyNull(info.getId())) {
			bean = new DbTableRelationship();
		}
		else {
			bean = this.tableRelationshipRepository.findById(info.getId());
		}
		DbTableColumn column = this.tableColumnRepository.findById(info.getColumnId());
		DbTableColumn linkedColumn = this.tableColumnRepository.findById(info.getLinkedColumnId());
		
		this.exceptionUtils.assertFalse(column == null || linkedColumn == null, "Both columns are required for the table relationship!"
		);
		this.exceptionUtils.assertFalse( 
				column.getDbTable().getId() != info.getTableId() && linkedColumn.getDbTable().getId() != info.getTableId()
				, "At least one column should belong to table [%s]!", info.getTableId()
		);
		
		bean.setLabel(info.getLabel());
		bean.setLinkToSecurityFilter(info.getLinkToFilter());
		if (info.getOneToMany()) {
			bean.setDbColumnParent(column);
			bean.setDbColumnChild(linkedColumn);
		}
		else {
			bean.setDbColumnParent(linkedColumn);
			bean.setDbColumnChild(column);
		}
		bean = saveTableRelationship(bean);
		
		return this.dbToRelationshipInfoMapper.map(info.getTableId(), bean);
	}
	
	
	@Transactional
	public DbTableRelationship saveTableRelationship(DbTableRelationship  bean) {
		this.exceptionUtils.assertFalse(
				bean.getDbColumnParent() == null || bean.getDbColumnParent().getId() == null
				|| bean.getDbColumnChild() == null || bean.getDbColumnChild().getId() == null
			, "Relationship must have both Parent and Child Column set!"
		);
		bean.setDbColumnParent(this.tableColumnRepository.findById(bean.getDbColumnParent().getId()));
		bean.setDbColumnChild(this.tableColumnRepository.findById(bean.getDbColumnChild().getId()));
		
		bean = this.tableRelationshipRepository.save(bean);
		return bean;
	}
	
	@Transactional
	public void deleteTableRelationship(RelationshipInfo  info) {
		this.tableRelationshipRepository.delete(info.getId());
	}
	
	@Transactional
	public void deleteTableRelationship(DbTableRelationship  bean) {
		this.tableRelationshipRepository.delete(bean);
	}
	
	
	
	public SpringyDataBean getDataModelJson() {
		 SpringyDataBean data = new SpringyDataBean();
		 List<DbTable> tables = this.tableRepository.findAll();
		 for (DbTable tb: ListUtils.safeList(tables)) {
			 data.addNode(tb);
		 }
		 List<DbTableRelationship> rels = this.tableRelationshipRepository.findAll();
		 for (DbTableRelationship rel: ListUtils.safeList(rels)) {
			 data.addEdge(rel);
		 }
		 
		 return data;
	}
}
