<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
Tables:<br/>
<c:out value="${tableList}" />
<c:if test="${tableList != null}">
	<c:forEach items="${tableList}" var="tb">
		<c:out value="${tb.name}" />
	</c:forEach> 
</c:if>
</body>
</html>
