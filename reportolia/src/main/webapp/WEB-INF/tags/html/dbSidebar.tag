<%@ 
	tag body-content="empty" language="java" pageEncoding="ISO-8859-1" %><%@ 
	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	attribute name="activeId" rtexprvalue="true"%>

<!-- DB SIDEBAR BEGIN -->	
<ul class="sidebar">
<%--
    <li><a href="#">
        <span class="mif-chart-line icon"></span>
        <span class="title"><spring:message code="reports.title" /></span>
        <span class="counter">0</span>
    </a></li>
     --%>
     
    <li <c:if test="${activeId == 'reportList'}">class="active"</c:if>><a href="#" onclick="openReportList()">
        <span class="mif-table icon"></span>
        <span class="title"><spring:message code="reports.title" /></span>
    </a></li>
     
    <li <c:if test="${activeId == 'tableList'}">class="active"</c:if>><a href="#" onclick="openTableList()">
        <span class="mif-table icon"></span>
        <span class="title"><spring:message code="dbTables.title" /></span>
    </a></li>
    <li <c:if test="${activeId == 'relationshipMap'}">class="active"</c:if>><a href="#" onclick="openRelationshipMap()">
        <span class="mif-tree icon"></span>
        <span class="title"><spring:message code="dataModelMap.title" /></span>
    </a></li>
    <li <c:if test="${activeId == 'variableList'}">class="active"</c:if>><a href="#">
        <span class="mif-cloud icon"></span>
        <span class="title"><spring:message code="variable.title" /></span>
    </a></li>
    <li><a href="#">
        <span class="mif-cogs icon"></span>
        <span class="title"><spring:message code="settings.title" /></span>
    </a></li>
</ul>
<!-- DB SIDEBAR ENDS -->	