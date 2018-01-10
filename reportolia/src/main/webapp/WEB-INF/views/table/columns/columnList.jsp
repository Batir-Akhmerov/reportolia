<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page icon="mif-table">
	<jsp:attribute name="pageTitle"><html:msg key="dbTable.column.list.title" /></jsp:attribute>
	
	<jsp:attribute name="scripts"></jsp:attribute>
	
	<jsp:attribute name="scriptBody">
		
		var LBL_IS_SECURED = '<html:msg key="dbTables.isSecured" />',
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
			
		var TABLE_ID = ${dbTable.id};
		
		function onLoad() {
		
			
			
			var tbConf = {
		        ajax: 'r3pTableColumnsLoad.go?tableId=' + TABLE_ID,
		        r3pAjaxSave: 'r3pTableColumnSave.go?tableId=' + TABLE_ID,
		        r3pAjaxDelete: 'r3pTableColumnDelete.go?tableId=' + TABLE_ID,
		        r3pGetFormTitle: function(rowData) {
		        	return r3pMsg.LBL_COLUMN;
		        },
		        r3pNewRow: {tableId: TABLE_ID},
		        columns: [
		        	{data: 'id', r3p: 'KEY'},
		        	{data: 'name', r3pLabel: r3pMsg.LBL_NAME, readOnly: true},
		            {data: 'label', r3pLabel: r3pMsg.LBL_LABEL},
		            {data: 'dataType', r3pLabel: r3pMsg.LBL_DATA_TYPE, readOnly: true},
		            {data: 'pk', r3pLabel: r3pMsg.LBL_PK, r3p: 'LBL_CHECK', readOnly: true, visible: false, searchable: false},
		            {data: 'calculated', r3pLabel: r3pMsg.LBL_CALCULATED, r3p: 'LBL_CHECK', readOnly: true, visible: false, searchable: false},
		            {data: 'notCorrelated', r3pLabel: r3pMsg.LBL_NOT_CORRELATED, r3p: 'LBL_CHECK', readOnly: true, visible: false, searchable: false}
		            
		        ]
		    };
			
			
			self.tblList = r3pDtb.init('columnListDiv', tbConf);
			
		}
		
	</jsp:attribute>
	
	<jsp:attribute name="breadcrumbs">
		<li><a href="#" onclick="openTableList()"><spring:message code="dbTables.title" /></a></li>
		<%-- <li><a href="#" onclick="openTable(${dbTable.id})">${dbTable.name}</a></li> --%>
		<li>${dbTable.name}</li> 
		<li><a href="#" onclick="openTableColumns(${dbTable.id})"><spring:message code="dbTable.column.list.name" /></a></li>
	</jsp:attribute>
	
	<jsp:attribute name="body">
				 
		<div id="columnListDiv"></div>
	        
	</jsp:attribute>
</html:page>
