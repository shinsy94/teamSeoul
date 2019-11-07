<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
function op(obj){
	var v=obj.value;
	if(v=="views"){
		$(function() {	
		  $("#sea").remove();
		  
		  var urls="created_sub.jsp";
		  $.get(urls,{bigareaCode:"1"},function(data){
		  $("#sel").after("<span id='area'><select id='bigarea' name='bigarea' onchange='loc(this);'>"
			+"<option value='1'>서울북부</option><option value='2'>서울동부</option>"
			+"<option value='3'>서울서부</option><option value='4'>서울남부</option></select></span>"+data);
			
		  });
			$("#selectTable").focus();
		});
	}else if(v=="festival"){
		$(function() {	
			$("#area").remove();
			
			$("#sel").after("<span id='sea'><select id='season' name='season'>"
					+"<option value='1'>봄</option><option value='2'>여름</option>"
					+"<option value='3'>가을</option><option value='4'>겨울</option></select></span>");
	
			
			$("#sel").focus();
		});
	}else{
		$(function() {	
			$("#sea").remove();
			$("#area").remove();
		});
	}
}
function loc(obj){
	var bigareaCode=obj.value;
	var url="created_sub.jsp";
	
	
	$.get(url,{bigareaCode:bigareaCode},function(data){
		$("#areaCode").remove();
		$("#bigarea").after(data);
	});
	
}

function check() {
	var chktitle = document.getElementById("title").value;
	
	if(chktitle==null){
		alert("제목을 입력해 주세요.");
		$("#title").focus();
		return;
	}
	
	var chksel=document.getElementById("sel").value;
	
	if(chksel=="subject"){
		alert("게시글 테이블을 설정해 주세요.");
		$("#sel").focus();
		return;
	}
}
</script>
</head>
<body>

<div>

<p>제목 : <input type="text" id="title"></p>



<span>게시판 선택 : <select id="sel" name="selectTable" onchange="op(this);">
	<option value="subject">게시글 테이블 선택</option>
	<option  value="views">관광</option>
	<option  value="festival">축제</option>
	<option  value="notice">공지사항</option>
	</select>
</span>
<br>
<p>글 본문 : <textarea name="content" style="resize: none;"></textarea></p>
<p><input type="file" name="originalFileName"></p>
<button type="button" onclick="check();">글 올리기</button> <button>돌아가기</button>
</div>


</body>
</html>