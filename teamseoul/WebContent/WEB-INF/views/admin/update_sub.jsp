<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
</script>
</head>
<body>
	<c:if test="${table=='views'}">
<span><select id='bigarea' name='bigarea' onchange='loc(this);' >
		<option value='1'>서울북부</option>
		<option value='2'>서울동부</option>
		<option value='3'>서울서부</option>
		<option value='4'>서울남부</option></select>
		<select id='areaCode' name='areaCode'>
		<c:forEach var="ke" items="${map}"  >
			<option value='${ke.key}'>${ke.value}</option>
		</c:forEach>
	</select>
</span>
	</c:if>
	
	<c:if test="${table=='festival'}">
<span><select id='season' name='season' >
		<option value='1'>봄</option>
		<option value='2'>여름</option>
		<option value='3'>가을</option>
		<option value='4'>겨울</option>
	  </select></span>
	</c:if>
</body>
</html>