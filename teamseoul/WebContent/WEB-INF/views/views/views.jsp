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
	$("body").on("click","#bigArea",function(){
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
				
			},
			error:function(e){
				console.log(e.responseText);
			}
		});
		
		if(isVis){
			 $ul.hide();
		} else {
			 $ul.show();
		}
		
	});
});

$(function(){
	
	
});

$(function(){
	$("body").on("click",".areaName",function(){
		var areaNum = $(this).parent().children("input").val();
		alert(areaNum);
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
	    		<li><a href="<%=cp%>/views/views.do">전체보기</a></li>
	    		
	    		<c:forEach var="dto" items="${bigAreaList}">
	    		<li><button type="button" id="bigArea" value="${dto.areaCode}">${dto.local}</button>
	    			<ul style='display: none;' class="subArea">
	    			
	    			</ul>
	    		</li>
	    		</c:forEach>
	    	</ul>
	    </div>
	    <div class="views-list">
    		<a href="<%=cp%>/views/article.do">
    			<img src="<%=cp%>/uploads/views/2019110818343436408098638100.png" width="40%">
    		</a>	

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