<%@ page contentType="text/html" %> 
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html:page pageTitle="Report List" >
	<jsp:attribute name="scriptBody">
		
		function onLoad() {
			//alert('on load');
		}
		function afterLoad() {
			//alert('after load');
		}
		
	</jsp:attribute>
	<jsp:attribute name="body">
		
		<h1> 
			Report List  
		</h1>
		
		<P>  The time on the server is ${serverTime}. </P>
		Tables:<br/>
		<c:out value="${tableList}" />
		<c:if test="${tableList != null}">
			<c:forEach items="${tableList}" var="tb">
				<c:out value="${tb.name}" />
			</c:forEach> 
		</c:if>
		
		<br/><br/><br/>
		
		<form:select path="command.stateId">
            <form:option value="0" label="Select" />
            <form:options items="${stateList}" itemValue="id" itemLabel="name" />
        </form:select>
		
	</jsp:attribute>
</html:page>
