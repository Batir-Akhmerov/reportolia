<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page icon="mif-table">
	<jsp:attribute name="pageTitle"><html:msg key="dbTables.title" /></jsp:attribute>
	
	<jsp:attribute name="scripts">
		js/reportolia/metadata/dlgMetadata.js
	</jsp:attribute>
	
	<jsp:attribute name="scriptBody">
		
		var MSG_RETRIEVE = '<html:msg key="msg.confirm.metadata.retrieve" />',
			BTN_RETRIEVE = '<html:msg key="dbTables.button.retrieveFromDb" />',
			BTN_ADD_MANUALLY = '<html:msg key="dbTables.button.addManually" />'
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
		        r3pAjaxSave: 'r3pTableSave.go',
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
				retrieveFromDb();
			</c:if>
			<c:if test="${!isTableListEmpty}">
		        loadTableList();
		    </c:if>
		}
		
		function loadTableList() {
			self.tblList.ajax.reload();
		}
		
		function retrieveFromDb() {
			var btnConf = {
				yesBtnLabel: BTN_RETRIEVE,
				yesBtnClass: 'success',
				noBtnLabel: BTN_ADD_MANUALLY
			};
			r3p.showConfirm(MSG_RETRIEVE, btnConf)
				.then(function(confirmed){
					openDlgMetadata(loadTableList);
				});
		}
	</jsp:attribute>
	
	<jsp:attribute name="sidebar">
		<html:dbSidebar activeId="tableList" />
	</jsp:attribute>
	
	<jsp:attribute name="body">
		<button class="button primary" onclick="createTable()"><span class="mif-plus"></span> <html:msg key="button.create"/></button>
		<button class="button warning" onclick="openDlgMetadata(loadTableList)"><span class="mif-loop2"></span> <html:msg key="dbTables.button.retrieveFromDb"/></button>
		
		
		<hr class="thin bg-grayLighter">
		
		<div id="tableListDiv"></div> 
	        
	</jsp:attribute>
</html:page>
