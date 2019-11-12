<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
	<c:forEach var="dto" items="${list}">
			<div style="width: 40%; float: left;">
				<p>
					<input type="hidden" name="areaCode" value="${dto.areaCode}">
					<a href="${articleUrl}&num=${dto.num}">
					<img src="<%=cp%>/uploads/views/${dto.somenailImg}" width="100%" style="border-radius: 10px;">
					</a>
				</p>
				<p>${dto.title}</p>
			</div>
	</c:forEach>
<p>${dataCount == 0 ? "서비스 준비중 입니다." : paging}</p>