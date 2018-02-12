<%@ 
	tag body-content="empty" language="java" pageEncoding="ISO-8859-1" %><%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@
	taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ 
	
	attribute name="key" %><c:set var="msg"><spring:message code="${key}" /></c:set
	><c:set var="msg">${fn:escapeXml(msg)}</c:set><c:set var="msg">${fn:replace(msg,"&lt;br/&gt;","<br/>")}</c:set>${msg}