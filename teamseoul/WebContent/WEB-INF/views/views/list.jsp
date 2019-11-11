<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<table>
	<c:forEach var="dto" items="${list}">
		<tr>
			<td><input type="hidden" name="areaCode" value="${dto.areaCode}">
				<a href="${articleUrl}&num=${dto.num}">
				<img src="<%=cp%>/uploads/views/${dto.somenailImg}" width="40%">
				</a>
			</td>
		</tr>
		<tr>
			<td>
				${dto.title}
			</td>
		</tr>
	</c:forEach>
</table>
<p>${dataCount == 0 ? "서비스 준비중 입니다." : paging}</p>