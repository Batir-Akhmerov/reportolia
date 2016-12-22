<!DOCTYPE html><%@ 
	tag body-content="empty" language="java" pageEncoding="ISO-8859-1" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	
	attribute name="pageTitle" fragment="true" %><%@ 
	attribute name="icon" rtexprvalue="true"%><%@ 
	attribute name="styles" rtexprvalue="true"%><%@ 
	attribute name="scriptsBefore" rtexprvalue="true"%><%@ 
	attribute name="scripts" rtexprvalue="true"%><%@ 
	attribute name="head" fragment="true" %><%@ 
	attribute name="scriptBody" fragment="true" %><%@ 
	attribute name="body" fragment="true" %><%@ 
	attribute name="sidebar" fragment="true" %><%@ 
	attribute name="breadcrumbs" fragment="true" %><c:set var="_pageTitle"><jsp:invoke fragment="pageTitle" /></c:set
><c:set var="_homeUrl">/reportolia</c:set
><html>
	<head>
		<title>${_pageTitle}</title>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	
		<jsp:invoke fragment="head" />
		
		
		<link href="js/reportolia/metro-ui-css/css/metro.css" rel="stylesheet" type="text/css" />
		<link href="js/reportolia/metro-ui-css/css/metro-icons.css" rel="stylesheet" type="text/css" />
		<link href="js/reportolia/metro-ui-css/css/metro-responsive.css" rel="stylesheet" type="text/css" />
		<link href="js/reportolia/metro-ui-css/css/metro-schemes.css" rel="stylesheet" type="text/css" />
		
		<link href="css/reportolia/r3pStyles.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="js/reportolia/metro-ui-css/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="js/reportolia/metro-ui-css/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="js/reportolia/metro-ui-css/js/select2.min.js"></script>
		<script type="text/javascript" src="js/reportolia/metro-ui-css/js/metro.js"></script>
		
			
		<c:forEach items="${styles}" var="fileName">
			<link rel="stylesheet" type="text/css" href="${fn:trim(fileName)}" />
		</c:forEach>
		
		<c:forEach items="${scriptsBefore}" var="fileName">
			<script type="text/javascript" src="${fn:trim(fileName)}"></script>
		</c:forEach>
		
		<script>
			$(document).ready(function(){
				if (self.beforeInit) self.beforeInit();
				if (self.onLoad) self.onLoad();
			});
		
			$(window).on('load', function () {
				if (self.afterLoad) self.afterLoad();
				if (self.afterDomReady) self.afterDomReady();
			});
			
			<jsp:invoke fragment="scriptBody" />
		</script>
	</head>
	
<body class="bg-steel">
    <html:header />
    <div class="page-content">
        <div class="flex-grid no-responsive-future" style="height: 100%;">
            <div class="row" style="height: 100%">
            	 <c:if test="${sidebar != null}">
	                <div class="cell size-x200" id="cell-sidebar" style="background-color: #71b1d1; height: 100%">
	                    <jsp:invoke fragment="sidebar" />
	                </div>
	            </c:if>
                <div class="cell auto-size padding20 bg-white" id="cell-content">
					<c:if test="${breadcrumbs != null}">
						<ul class="breadcrumbs padding10 no-padding-top no-padding-left">
							<li><a href="${_homeUrl}"><span class="icon mif-home"></span></a></li>
							<jsp:invoke fragment="breadcrumbs" />
						</ul>
					</c:if>
                    <h1 class="text-light">${_pageTitle} <span class="${icon} place-right"></span></h1>                    
                    <hr class="thin bg-grayLighter">
                    
                    <jsp:invoke fragment="body" />
                    
                </div>
            </div>
        </div>
    </div>	
	

		<script type="text/javascript" src="js/reportolia/form2js.js"></script>
		
		<script type="text/javascript" src="js/reportolia/r3pMessagesLoad.go"></script>
		<script type="text/javascript" src="js/reportolia/r3p.js"></script>
		<script type="text/javascript" src="js/reportolia/r3pDtb.js"></script>
		<script type="text/javascript" src="js/reportolia/r3pScripts.js"></script>
		
		<c:forEach items="${scripts}" var="fileName">
			<script type="text/javascript" src="${fn:trim(fileName)}"></script>
		</c:forEach>
		
	</body>	

</html> 