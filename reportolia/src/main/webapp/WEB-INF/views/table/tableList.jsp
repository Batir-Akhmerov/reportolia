<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page>
	<jsp:attribute name="pageTitle"><html:msg key="dbTables.title" /></jsp:attribute>	
	<jsp:attribute name="topMenuId">tableList</jsp:attribute>
	<jsp:attribute name="scripts"></jsp:attribute>
	
	<jsp:attribute name="scriptBody">
		
		var MSG_RETRIEVE = '<html:msg key="msg.confirm.metadata.retrieve" />',
			MSG_BLANK_DATA_MODEL = '<html:msg key="msg.confirm.metadata.blankModel" />',
			
			LBL_IS_SECURED = '<html:msg key="dbTables.isSecured" />',
			LBL_HAS_SECURITY_FILTER = '<html:msg key="dbTables.hasSecurityFilter" />';
		
		<%-- metadata dialog constants --%>	
		var DLG_METADATA_TITLE = '<html:msg key="sysTable.title" />',
			DLG_METADATA_INSTRUCTIONS = '<html:msg key="sysTable.instructions" />',
			COL_TYPE = '<html:msg key="form.type.name" />',
			COL_SCHEMA = '<html:msg key="form.schema.name" />',
			COL_COLUMNS = '<html:msg key="form.columns.name" />',
			COL_DATA_TYPE = '<html:msg key="form.dataType.name" />',
			COL_LENGTH = '<html:msg key="form.length.name" />',
			INFO_OPEN_COLUMNS = '<html:msg key="info.open.columns" />',
			MSG_ADD_SELECTED = '<html:msg key="msg.addSelected" />';
		
		function onLoad() {
			var tbConf = {
		        ajax: 'r3pTablesLoad.go',
		        r3pAjaxSave: 'r3pTableSaveAjax.go',
		        r3pAjaxDelete: 'r3pTableDelete.go',
		        r3pGetFormTitle: function(rowData) {
		        	return rowData.name;
		        },
		        columns: [
		        	{data: 'id', r3p: 'KEY'},
		        	{data: 'name', visible: false, searchable: false},
		            {data: 'label', r3pLabel: r3pMsg.LBL_LABEL,
		            	render: function(data, type, full, meta){
		            		return r3p.tmplLinkHint(data, '#', 'openTableColumns('+full.id+')', INFO_OPEN_COLUMNS);
				    	}
		            },
		            {data: 'secured', r3p: 'LBL_CHECK', r3pLabel: LBL_IS_SECURED},
		            {data: 'securityFilter', r3p: 'LBL_CHECK', r3pLabel: LBL_HAS_SECURITY_FILTER}
		        ]
		    };
			
			
			self.tblList = r3pDtb.init('tableListDiv', tbConf);
		}
		
		function afterLoad() {
			 <c:if test="${isTableListEmpty}">
				retrieveFromDb(MSG_BLANK_DATA_MODEL);
			</c:if>
			
		}
		
		function loadTableList() {
			self.tblList.ajax.reload();
		}
		
		function retrieveFromDb(msgPrefix) {
			if (r3p.isBlank(msgPrefix)) msgPrefix = ''; 
			r3p.showConfirm(msgPrefix + MSG_RETRIEVE)
				.then(function(confirmed){
					openDbMetadataPopulator(FWD_TABLES);
				}
			);
			
		}
	</jsp:attribute>
	
	
	<jsp:attribute name="buttons">
		<button onclick="retrieveFromDb()" type="button" class="btn btn-outline-danger" title="<html:msg key="dbTables.button.retrieveFromDb.title"/>"><html:msg key="dbTables.button.retrieveFromDb"/></button> 
	</jsp:attribute>
	
	<jsp:attribute name="body">		
				
		<div id="tableListDiv"></div> 
	        
	</jsp:attribute>
</html:page>
