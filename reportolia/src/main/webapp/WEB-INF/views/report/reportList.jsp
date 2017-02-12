<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page icon="mif-table">
	<jsp:attribute name="pageTitle"><html:msg key="reports.title" /></jsp:attribute>
	
	<jsp:attribute name="scripts">
		js/reportolia/metadata/dlgMetadata.js
	</jsp:attribute>
	
	 
	<jsp:attribute name="scriptBody">
		
		var LBL_IS_SECURED = '<html:msg key="dbTables.isSecured" />';
		
		var FOLDER_ID = 0;
		
		
		function onLoad() {
		
			
			
			var tbConf = {
		        ajax: getReportListUrl,
		        
		        r3pAjaxDelete: 'r3pTableColumnDelete.go?tableId=' + TABLE_ID,
		        
		        r3pNewRow: {folderId: TABLE_ID},
		        columns: [
		        	{data: 'id', r3p: 'KEY'},
		        	{data: 'name', r3pLabel: r3pMsg.LBL_NAME, readOnly: true}
		        ]
		    };
			
			
			self.tblList = r3pDtb.init('reportListDiv', tbConf);
			
		}
		
		function getReportLIstUrl() {
			return 'r3pReportsLoad.go?folderId=' + FOLDER_ID;
		}
		
	</jsp:attribute>
	
	
	<jsp:attribute name="sidebar">
		
	</jsp:attribute>
	
	
	<jsp:attribute name="body">
				 
		<div id="reportListDiv"></div>
	        
	</jsp:attribute>
</html:page>
