<%@ 
	tag body-content="empty" language="java" pageEncoding="ISO-8859-1" %><%@ 
	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	attribute name="activeId" rtexprvalue="true"%>

<!--  SIDEBAR BEGIN -->	

	<nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
		<ul class="nav nav-pills flex-column">
			<li class="nav-item">
				<a class="nav-link <c:if test="${activeId == 'reportList'}">active</c:if>" href="#" onclick="openReportList()"><spring:message code="reports.title" /></a>
			</li>
			<li class="nav-item">
				<a class="nav-link <c:if test="${activeId == 'tableList'}">active</c:if>" href="#" onclick="openTableList()"><spring:message code="dbTables.title" /></a>
			</li>
			<li class="nav-item">
				<a class="nav-link <c:if test="${activeId == 'relationshipMap'}">active</c:if>" href="#" onclick="openRelationshipMap()"><spring:message code="dataModelMap.title" /></a>
			</li>
			<li class="nav-item">
				<a class="nav-link <c:if test="${activeId == 'variableList'}">active</c:if>" href="#" onclick="openVariableList()"><spring:message code="variable.title" /></a>
			</li>
		</ul>
	
	</nav>
<%-- 
<ul class="sidebar">

     
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
--%>
<!--  SIDEBAR ENDS -->	