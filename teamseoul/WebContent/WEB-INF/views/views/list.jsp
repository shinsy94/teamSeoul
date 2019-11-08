<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>

<c:forEach begin="1" end="2">
	<input type="hidden" name="areaCode" value="${dto.areaCode}">
	<img src="<%=cp%>/uploads/views/20191108170135115255978836000.png" width="40%">
</c:forEach>