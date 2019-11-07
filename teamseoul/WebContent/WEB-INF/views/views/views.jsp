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

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
$(function(){
	$("body").on("click","#northMenu",function(){
		var $ul = $(this).next();
		var isVis = $ul.is(":visible");
		
		if(isVis){
			$ul.hide(1000);
		} else {
			$ul.show(1000);
		}
		
	});
});
</script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
	
<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container" >
	    <div class="views-menu">
	    	<ul>
	    		<li><a href="<%=cp%>/views/views.do">전체보기</a></li>
	    		<li><a href="#" id="northMenu">북부</a>
	    			<ul style='display: none;'>
	    				<li>도봉구</li>
	    				<li>노원구</li>
	    				<li>강북구</li>
	    				<li>성북구</li>
	    				<li>은평구</li>
	    				<li>종로구</li>
	    				<li>중랑구</li>
	    			</ul>
	    		</li>
	    		<li><a href="#">남부</a></li>
	    		<li><a href="#">서부</a></li>
	    		<li><a href="#">동부</a></li>
	    	</ul>
	    </div>
	    <div class="views-list">
	    <c:forEach begin="1" end="2">
	    	<img src="<%=cp%>/resource/images/main_3.jpg" width="40%">
	    	<img src="<%=cp%>/resource/images/main_3.jpg" width="40%">
	    </c:forEach>
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