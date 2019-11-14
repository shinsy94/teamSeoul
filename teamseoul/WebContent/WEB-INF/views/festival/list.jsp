<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
	<c:forEach var="dto" items="${list}">
		<div style="width: 45%;  float: left; margin: 10px;">
		<p>
			<input type="hidden" name="seasonCode" value="${dto.seasonCode}">
				<a href="${articleUrl}&num=${dto.num}">
				<img src="<%=cp%>/uploads/festival/${dto.somenailImg}" width="100%" height="300" style="border-radius: 10px;">
				</a>
		</p>
			<p style="text-align: center; font-size: 20px;">${dto.title}</p>
		</div>
	</c:forEach>
<p>${dataCount == 0 ? "서비스 준비중 입니다." : paging}</p>