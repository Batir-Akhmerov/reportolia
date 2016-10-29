<%@ tag body-content="empty" language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="pageTitle" fragment="true" %>
<%@ attribute name="styles" rtexprvalue="true"%>
<%@ attribute name="scriptsBefore" rtexprvalue="true"%>
<%@ attribute name="scripts" rtexprvalue="true"%> <%-- scripts after body --%>

<%@ attribute name="head" fragment="true" %>
<%@ attribute name="scriptBody" fragment="true" %>
<%@ attribute name="body" fragment="true" %> 

<c:set var="_pageTitle"><jsp:invoke fragment="pageTitle" /></c:set>
 
<html>
	<head>
		<title>${_pageTitle}</title>
		
		<jsp:invoke fragment="head" />
		
		<link href="js/reportolia/jquery/ui/1.12.1.brown/jquery-ui.css" rel="stylesheet" type="text/css" />
		<link href="css/reportolia/r3pStyles.css" rel="stylesheet" type="text/css" />
		<%--<link href="js/reportolia/jquery/plugins/jtable.2.4.0/themes/metro/lightgray/jtable.min.css" rel="stylesheet" type="text/css" /> --%>
		<%--<link href="js/reportolia/jquery/plugins/jtable.2.4.0/themes/lightcolor/blue/jtable.min.css" rel="stylesheet" type="text/css" /> --%>
		<link href="js/reportolia/jquery/plugins/jtable.2.4.0/themes/jqueryui/jtable_jqueryui.css" rel="stylesheet" type="text/css" /> 
		<%--<link href="js/reportolia/jquery/plugins/jtable.2.4.0/themes/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" /> --%>
		
		<%-- MINIFY AND MERGE ME --%>				 
		<%--<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/DataTables-1.10.12/css/dataTables.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/Buttons-1.2.2/css/buttons.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/ColReorder-1.3.2/css/colReorder.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/FixedColumns-3.2.2/css/fixedColumns.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/FixedHeader-3.1.2/css/fixedHeader.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/KeyTable-2.1.3/css/keyTable.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/Scroller-1.4.2/css/scroller.jqueryui.css"/>
		<link rel="stylesheet" type="text/css" href="js/reportolia/jquery/plugins/datatable/Select-1.2.0/css/select.jqueryui.css"/> --%>
		
		<script type="text/javascript" src="js/reportolia/jquery/jquery-3.1.1.js"></script>		
		<script type="text/javascript" src="js/reportolia/jquery/ui/1.12.1.brown/jquery-ui.js"></script>
			
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
	
	
	
	<body>
		<h1>${_pageTitle}</h1>
		
		<jsp:invoke fragment="body" />
		
		<script type="text/javascript" src="js/reportolia/form2js.js"></script>
		
		
		<%-- MINIFY AND MERGE ME --%>
		<%--
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/JSZip-2.5.0/jszip.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/pdfmake-0.1.18/build/pdfmake.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/pdfmake-0.1.18/build/vfs_fonts.js"></script>
		
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/DataTables-1.10.12/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/DataTables-1.10.12/js/dataTables.jqueryui.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Buttons-1.2.2/js/dataTables.buttons.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Buttons-1.2.2/js/buttons.jqueryui.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Buttons-1.2.2/js/buttons.colVis.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Buttons-1.2.2/js/buttons.html5.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Buttons-1.2.2/js/buttons.print.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/ColReorder-1.3.2/js/dataTables.colReorder.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/FixedColumns-3.2.2/js/dataTables.fixedColumns.js"></script> 
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/FixedHeader-3.1.2/js/dataTables.fixedHeader.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/KeyTable-2.1.3/js/dataTables.keyTable.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Scroller-1.4.2/js/dataTables.scroller.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/datatable/Select-1.2.0/js/dataTables.select.js"></script> --%>
		
		<script type="text/javascript" src="js/reportolia/jquery/plugins/jtable.2.4.0/jquery.jtable.js"></script>
		<script type="text/javascript" src="js/reportolia/jquery/plugins/floatThead/jquery.floatThead.min.js"></script>
		
		
		<script type="text/javascript" src="js/reportolia/r3p.js"></script>
		<script type="text/javascript" src="js/reportolia/r3pMessagesLoad.go"></script>
		<script type="text/javascript" src="js/reportolia/r3pScripts.js"></script>
		
		
		
		
		<c:forEach items="${scripts}" var="fileName">
			<script type="text/javascript" src="${fn:trim(fileName)}"></script>
		</c:forEach>
		
	</body>	

</html> 