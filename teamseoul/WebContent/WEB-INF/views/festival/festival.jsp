<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<style type="text/css">
button:focus{
	outline: none;
}
</style>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/festival.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">

function listPage(page,seasonCode) {
	var url = "<%=cp%>/festival/list.do";
	
	
	$.get(url, {page:page, seasonCode:seasonCode}, function(data){
		$(".festival-list").html(data);
	});
}

$(function(){
	listPage(1,0);
});

$(function(){
	$("body").on("click",".seasonCode",function(){
		var seasonCode = $(this).val();
		listPage(1,seasonCode)
	})
});
</script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
	
<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container" >
	    <div class="festival-menu">
	    	<ul>
	    		<li><button class="seasonCode" onclick="javascript:location.href='<%=cp%>/festival/festival.do'">전체보기</button></li>
	    		
	    		<c:forEach var="map" items="${seasonMap}">
	    		<li><button type="button" class="seasonCode" value="${map.key}">${map.value}</button></li>
	    		</c:forEach>
	    	</ul>
	    </div>
	    <div class="festival-list">

	    </div>
	</div>
   
</div>

<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>