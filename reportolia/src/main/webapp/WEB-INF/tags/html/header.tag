<%@ 
	tag body-content="empty" language="java" pageEncoding="ISO-8859-1" %><%@ 
	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	attribute name="activeId" rtexprvalue="true"%>
	
<!-- HEADER BEGIN -->

<header>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<a class="navbar-brand" href="/reportolia"><spring:message code="app.title" /></a>
		<button class="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
	
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item <c:if test="${activeId == 'home'}">active</c:if>">
					<a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item <c:if test="${activeId == 'reportList'}">active</c:if>">
					<a class="nav-link" href="#" onclick="openReportList()"><spring:message code="reports.title" /></a>
				</li>
				<li class="nav-item <c:if test="${activeId == 'tableList'}">active</c:if>">
					<a class="nav-link" href="#" onclick="openTableList()"><spring:message code="dbTables.title" /></a>
				</li>
				<li class="nav-item <c:if test="${activeId == 'dataModelMap'}">active</c:if>">
					<a class="nav-link" href="#" onclick="openRelationshipMap()"><spring:message code="dataModelMap.title" /></a>
				</li>
				<li class="nav-item <c:if test="${activeId == 'help'}">active</c:if>">
					<a class="nav-link" href="#">Help</a>
				</li>
			</ul>
			<%--
			<form class="form-inline mt-2 mt-md-0" wtx-context="2B709C27-E36C-426D-8A8F-B37B03865FAB">
				<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" wtx-context="775C147E-7FE6-46B4-AA2E-1A3913CDE218">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>
			 --%>
		</div>
	</nav>
</header>
<%-- 
<div class="app-bar fixed-top darcula" data-role="appbar">
	<a class="app-bar-element branding" href="/reportolia"><spring:message code="app.title" /></a>
	<span class="app-bar-divider"></span>
	<ul class="app-bar-menu">
	    <li data-flexorderorigin="0" data-flexorder="1"><a href="">Dashboard</a></li>
	    <li data-flexorderorigin="1" data-flexorder="2"><a href="#" onclick="openReportList()"><spring:message code="reports.title" /></a></li>
	    <li data-flexorderorigin="2" data-flexorder="3">
	        <a href="" class="dropdown-toggle"><spring:message code="database.title" /></a>
	        <ul class="d-menu" data-role="dropdown">
	            <li><a href="#" onclick="openTableList()"><spring:message code="dbTables.title" /></a></li>
	            <li><a href="#" onclick="openRelationshipMap()"><spring:message code="dataModelMap.title" /></a></li>
	            <li><a href="#" onclick="openVariableList()"><spring:message code="variable.title" /></a></li>
	            <li class="divider"></li>
	            <li>
	                <a href="" class="dropdown-toggle">Reopen</a>
	                <ul class="d-menu" data-role="dropdown">
	                    <li><a href="">Project 1</a></li>
	                    <li><a href="">Project 2</a></li>
	                    <li><a href="">Project 3</a></li>
	                    <li class="divider"></li>
	                    <li><a href="">Clear list</a></li>
	                </ul>
	            </li>
	        </ul>
	    </li>
	    <li data-flexorderorigin="3" data-flexorder="4"><a href=""><spring:message code="security.title" /></a></li>
	    <li data-flexorderorigin="4" data-flexorder="5"><a href="">System</a></li><li data-flexorderorigin="4" data-flexorder="5" class="">
	        <a href="" class="dropdown-toggle">Help</a>
	        <ul class="d-menu" data-role="dropdown">
	            <li><a href="">ChatOn</a></li>
	            <li><a href="">Community support</a></li>
	            <li class="divider"></li>
	            <li><a href="">About</a></li>
	        </ul>
	    </li>
	</ul>

    <div class="app-bar-element place-right">
        <span class="dropdown-toggle"><span class="mif-cog"></span> User Name</span>
        <div class="app-bar-drop-container padding10 place-right no-margin-top block-shadow fg-dark" data-role="dropdown" data-no-close="true" style="width: 220px">
            <h2 class="text-light">Quick settings</h2>
            <ul class="unstyled-list fg-dark">
                <li><a href="" class="fg-white1 fg-hover-yellow">Profile</a></li>
                <li><a href="" class="fg-white2 fg-hover-yellow">Security</a></li>
                <li><a href="" class="fg-white3 fg-hover-yellow">Exit</a></li>
            </ul>
        </div>
    </div>
<div class="app-bar-pullbutton automatic" style="display: none;"></div><div class="clearfix" style="width: 0;"></div><nav class="app-bar-pullmenu hidden flexstyle-app-bar-menu" style="display: none;"><ul class="app-bar-pullmenubar app-bar-menu hidden" style="display: none;"></ul></nav></div>
--%>
<!-- HEADER END -->