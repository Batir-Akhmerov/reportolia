<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page icon="mif-table">
	<jsp:attribute name="pageTitle"><html:msg key="dbTable.column.list.title" /></jsp:attribute>
	
	<jsp:attribute name="scripts">
		js/reportolia/metadata/dlgMetadata.js
	</jsp:attribute>
	
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
		
		function onLoad() {
			/*
			var tbConf = {
		        ajax: 'r3pTablesLoad.go',
		        r3pAjaxSave: 'r3pTableSave.go',
		        r3pAjaxDelete: 'r3pTableDelete.go',
		        r3pAjaxOpen: 'r3pTableShow.go',
		        columns: [
		        	{data: 'id', r3p: 'KEY'},
		            {data: 'label', r3pLabel: r3pMsg.LBL_LABEL},
		            {data: 'secured', r3p: 'LBL_CHECK', r3pLabel: LBL_IS_SECURED},
		            {data: 'securityFilter', r3p: 'LBL_CHECK', r3pLabel: LBL_HAS_SECURITY_FILTER}
		        ]
		    };
			
			
			self.tblList = r3pDtb.init('tableListDiv', tbConf);
			*/
		}
		
	</jsp:attribute>
	
	<jsp:attribute name="breadcrumbs">
		<li><a href="#" onclick="openTableList()"><spring:message code="dbTables.title" /></a></li>
		<li><a href="#" onclick="openTable(${dbTable.id})">${dbTable.name}</a></li>
		<li><a href="#" onclick="openTableColumns(${dbTable.id})"><spring:message code="dbTable.column.list.name" /></a></li>
	</jsp:attribute>
	
	<jsp:attribute name="body">
		<button class="button primary" onclick="createTable()"><span class="mif-plus"></span> <html:msg key="button.create"/></button>
		<button class="button success" onclick="pushMessage('success')"><span class="mif-save"></span> <html:msg key="button.save"/></button>
		<button class="button warning" onclick="openDlgMetadata(loadTableList)"><span class="mif-loop2"></span> <html:msg key="dbTables.button.retrieveFromDb"/></button>
		
		
		<hr class="thin bg-grayLighter">
		<form:form method="POST" commandName="dbTable">
		
			<label><html:msg key="dbTable.title" /></label>
			<div class="input-control text">
				<form:input path="name" />
			</div> 
		</form:form>
	        
	</jsp:attribute>
</html:page>
