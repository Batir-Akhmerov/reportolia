<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<ul>
	<li><a href="#tabTables" onclick="openTableList()"><spring:message code="dbTables.title" /></a></li>
	<li><a href="#tabVariables"><spring:message code="variable.title" /></a></li>
</ul>