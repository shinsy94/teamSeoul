<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>

<c:forEach var="dto" items="${list}">
	<input type="hidden" name="areaCode" value="${dto.areaCode}">
	<a href="${articleUrl}&num=${dto.num}">
	<img src="<%=cp%>/uploads/views/${dto.somenailImg}" width="40%">
	</a>
</c:forEach>

<p>${dataCount == 0 ? "데이터가 없습니다." : paging}</p>