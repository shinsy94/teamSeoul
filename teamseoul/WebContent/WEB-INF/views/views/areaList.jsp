<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>

<c:forEach var="map" items="${areaMap}">
	<li><input type="hidden" name="areaCode" value="${map.key}">
		<a href="#" class="areaName">${map.value}</a>
	</li>
</c:forEach>
