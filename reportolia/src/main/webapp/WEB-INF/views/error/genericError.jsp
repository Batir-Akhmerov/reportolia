<%@ page contentType="text/html" %><%@ 
	taglib prefix="html" tagdir="/WEB-INF/tags/html" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://www.springframework.org/tags/form" prefix="form"%><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><html:page icon="mif-table">
	<jsp:attribute name="pageTitle"><html:msg key="app.title" /></jsp:attribute>
	
	<jsp:attribute name="scriptBody">
		
		function onLoad() {
			
		}
		
		function afterLoad() {
			<c:if test="${errorMsg != null}">
				r3p.showError('${errorMsg}');
			</c:if>
			<c:if test="${errorMsg == null}">
		        
		    </c:if>
		}
		
	</jsp:attribute>
	
	
	<jsp:attribute name="body">
		
	</jsp:attribute>
</html:page>
