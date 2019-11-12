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
<link rel="stylesheet" href="<%=cp%>/resource/css/views.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
//댓글 등록
$(function(){
	$(".btnSendReply").click(function(){
		
		<c:if test="${empty sessionScope.member}">
			alert("댓글을 작성할려면 로그인이 필요합니다.");
			location.href="<%=cp%>/member/login.do";
		</c:if>
		
		var num="${list.get(0).num}";
		// var content=$(".boxTA:first").val();
		var $tb = $(this).closest("table");
		var content=$tb.find("textarea").val().trim();
		if(! content) {
			$tb.find("textarea").focus();
			return;
		}
		content = encodeURIComponent(content);
		
		var query="num="+num+"&content="+content;
		var url="<%=cp%>/views/insertReply.do";
		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				$tb.find("textarea").val("");
				
				var state=data.state;
				if(state=="true") {
					listPage(1);
				} 
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
});


// 댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/views/listReply.do";
	var query="num=${list.get(0).num}&pageNo="+page;
	
	$.ajax({
		type:"get"
		,url:url
		,data:query
		,success:function(data) {
			$("#listReply").html(data);
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}

//게시물 공감 추가
function sendFavorite(num) {
	var msg="게시물을 즐겨찾기 하시겠습니까?";
	if(! confirm(msg))
		return;
	
	<c:if test="${empty sessionScope.member}">
	alert("즐겨찾기를 하려면 로그인이 필요합니다.");
	location.href="<%=cp%>/member/login.do";
	</c:if>
	
	var url="<%=cp%>/views/favorite.do";
	$.ajax({
		type:"post"
		,url:url
		,data:{num:num}
		,dataType:"json"
		,success:function(data) {
			var state=data.state;
			if(state=="true") {
				alert("즐겨찾기가 완료되었습니다 ^^ ");
			} else if(state=="false") {
				alert("이미 즐겨찾기한 게시물 입니다!!");
			}
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}


// 이미지 슬라이드

$(document).ready(function(){
	var $slide = $(".slide").find("ul");
	
	var $slideWidth = $slide.children().outerWidth();
	var $slideHeight = $slide.children().outerHeight();
	var $slideLength = $slide.children().length;
	var rollingId;
	
	rollingId = setInterval(function() {rollingStart();}, 2000);
	
	$slide.mouseover(function(){
		
		clearInterval(rollingId);
		$(this).css("cursor","pointer");
	});
	
	$slide.mouseout(function(){
		rollingId = setInterval(function() {rollingStart();},2000);
		$(this).css("cursor","default");
	});
	
	function rollingStart(){
		$slide.css("width",$slideWidth * $slideLength + "px");
		$slide.css("height", $slideHeight + "px");
		
		$slide.animate({left: -$slideWidth + $slideWidth + "px"}, 1500, function(){
			$(this).append("<li>"+$(this).find("li:first").html()+"</li>");
			$(this).find("li:first").remove();
			$(this).css("left",0);
		});
	}
});
</script>
</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
	
<div class="container" style="position: relative; top: 155px; z-index: 1;" >
	
    <div class="body-container">
    	<div class="body-title" style="width: 100%; text-align: left;">
       		<h3>${list.get(0).title}</h3>
       		<img src="<%=cp%>/resource/images/star.png" width="30" align="right" onclick="sendFavorite(${list.get(0).num});">
   		</div>
   		
    <table>
    <tr>
	    <td width="50%">
	    ${list.get(0).content}
	    </td>
	    <td width="50%">
	    	<div class="slide">
	    		<ul>
	    		<c:forEach var="dto" items="${list}">
	    		<li>
	    			<img src="<%=cp%>/uploads/views/${dto.imageFileName}" width="500" >
	    		</li>
	    		</c:forEach>
	    		</ul>
	    	</div>
    	</td>
    	</tr>
    </table>
    		<hr>
    		<c:if test="${sessionScope.member.userId == 'admin'}">
    		<div class="article_btn">
	    		<div class="adminBtn" style="float: left;">   
	    			<button onclick="javascript:location.href='<%=cp%>/admin/updateForm.do?num=${list.get(0).num}&table=views'">수정</button>
	    			<button>삭제</button>
	    		</div>
	    		<div class="listBtn" style="text-align: right;">
	    			<button onclick="javascript:location.href='<%=cp%>/views/views.do?page=${page}&areaCode=${areaCode}'">리스트</button>
	    		</div>
    		</div>
    		</c:if>
    		
    		  <div>
            <table style='width: 100%; margin: 15px auto 0px; border-spacing: 0px;'>
            <tr height='30'> 
	            <td align='left'>
	            	<span style='font-weight: bold;' >댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span>
	            </td>
            </tr>
            <tr>
               <td style='padding:5px 5px 0px;'>
                    <textarea class='boxTA' style='width:99%; height: 70px;'></textarea>
                </td>
            </tr>
            <tr>
               <td align='right'>
                    <button type='button' class='btn btnSendReply' style='padding:10px 20px;'>댓글 등록</button>
                </td>
            </tr>
            </table>
            
            <div id="listReply"></div>
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