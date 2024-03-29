﻿<%@ page contentType="text/html; charset=UTF-8" %>
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
<link rel="stylesheet" href="<%=cp%>/resource/css/views.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">


$(function(){
	$("body").on("click",".bigArea",function(){
		var $ul = $(this).next();
		
		var isVis = $ul.is(":visible");
		var url = "<%=cp%>/views/areaList.do";
		var query = "bigCode="+$(this).val();
		
		// 현재 width %로 동적으로 해놔서 데이터 불러오면 화면 떨림 현상 있음.
		$.ajax({// 함수에 객체를 넘긴다
			type:"GET",
			url:url,
			data:query,
			success:function(data){
				 $ul.html(data);
				 if(isVis){
					 $ul.slideUp().hide(1000);
				} else {
					 $ul.slideDown().show(1000);
				}
			},
			error:function(e){
				console.log(e.responseText);
			}
		});
		
		
		
	});
});

function listPage(page,areaCode) {
	var url = "<%=cp%>/views/list.do";
	
	
	$.get(url, {page:page, areaCode:areaCode}, function(data){
		$(".views-list").html(data);
	});
}

$(function(){
	listPage(1,0);
});

$(function(){
	$("body").on("click",".areaName",function(){
		var areaCode = $(this).parent().children("input").val();
		
		listPage(1,areaCode)
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
	    <div class="views-menu" style="min-height: 400px;">
	    	<ul>
	    		<li><button onclick="javascript:location.href='<%=cp%>/views/views.do'" style="font-size:18px; font-weight: bold;">전체보기</button>
	    			<ul>	
			    		<c:forEach var="dto" items="${bigAreaList}">
			    		<li><button type="button" class="bigArea" value="${dto.areaCode}">◆&nbsp;${dto.local}</button>
			    			<ul class="subArea" style="display: none;">
			    			
			    			</ul>
			    		</li>
			    		</c:forEach>
	    			</ul>
	    		</li>
	    	</ul>
	    </div>
	    <div class="views-list">

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