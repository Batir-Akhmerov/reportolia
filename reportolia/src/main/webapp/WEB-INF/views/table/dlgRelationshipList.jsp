<%@ page contentType="text/html" %> 
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	var TITLE_PAGE = '<spring:message code="dbTable.relationship.manage.instructions" />',
		TITLE_CURRENT_TABLE = '',
		TH_COLUMN = '<spring:message code="form.column.name" />',
		TH_LINKED_TABLE = '<spring:message code="dbTable.relationship.linkedTable.name" />',
		TH_LINKED_COLUMN = '<spring:message code="dbTable.relationship.linkedColumn.name" />',
		LBL_LINK_TO_FILTER = '<spring:message code="dbTable.relationship.isLinkToFilter.name" />',
		LBL_LINK_TO_FILTER_INFO = '<spring:message code="dbTable.relationship.isLinkToFilter.info" />',
		LBL_ONE_TO_MANY = '<spring:message code="dbTable.relationship.oneToMany" />',
		LBL_MANY_TO_ONE = '<spring:message code="dbTable.relationship.manyToOne" />'
		
			
		;

</script>
	
<div id="relationshipList"></div>
	