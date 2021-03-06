<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page>
	<jsp:attribute name="pageTitle"><html:msg key="dbTable.title" />: ${fn:escapeXml(dbTable.name)}</jsp:attribute>
	<jsp:attribute name="bodyTitle"><html:msg key="dbTable.title" />: <span class="text-primary">${fn:escapeXml(dbTable.name)}</span></jsp:attribute>
	<jsp:attribute name="topMenuId">tableList</jsp:attribute>
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
		
		function onLoad() {
			
		}
		
		function saveMe() {
			$('#beanForm')[0].submit();
		}
		
	</jsp:attribute>
	
	<jsp:attribute name="breadcrumbs">
		<li class="breadcrumb-item"><a href="#" onclick="openTableList()"><spring:message code="dbTables.title" /></a></li>
		<li class="breadcrumb-item active" aria-current="page"><a href="#" onclick="openTable(${dbTable.id})">${dbTable.name}</a></li>
	</jsp:attribute>
	
	<jsp:attribute name="buttons">
		<button onclick="saveMe()" type="button" class="btn btn-primary"><html:msg key="button.save"/></button> 
	</jsp:attribute>
	
	<jsp:attribute name="body">
		
		
		<form:form method="POST" id="beanForm" action="r3pTableSave.go" commandName="dbTable">
			<form:hidden path="id" />
			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label"><html:msg key="form.label.label" /></label>
				<div class="col-sm-10">
					<form:input path="label" class="form-control"/>	
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 col-form-label"><html:msg key="form.description.label" /></label>
				<div class="col-sm-10">
					<form:textarea path="description" class="form-control" />
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col-sm-2">					
				</div>
				<div class="col-sm-10">
					<div class="form-check">
						<form:checkbox path="secured" id="chkSecured" class="form-check-input"/>
						<label class="form-check-label" for="chkSecured"><html:msg key="dbTables.isSecured" /></label>
					</div>
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col-sm-2">					
				</div>
				<div class="col-sm-10">
					<div class="form-check">
						<form:checkbox path="securityFilter" id="chkSecured" class="form-check-input"/>
						<label class="form-check-label" for="chkSecured"><html:msg key="dbTables.hasSecurityFilter" /></label>
					</div>
				</div>
			</div>

			
		</form:form>
	</jsp:attribute>
	
	
</html:page>
