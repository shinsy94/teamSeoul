<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
function op(obj){
	var v=obj.value;
	if(v=="views"){
		$(function() {	
		  $("#sea").remove();
		  $("#area").remove();
		  var urls="<%=cp%>/admin/viewscreated_sub.do";
		  $.get(urls,{bigareaCode:"1"},function(data){
		  $("#sel").after("<span id='area'><select id='bigarea' name='bigarea' onchange='loc(this);'>"
			+"<option value='1'>서울북부</option><option value='2'>서울동부</option>"
			+"<option value='3'>서울서부</option><option value='4'>서울남부</option></select>"+data+"</span>");
			
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
	

	if(v=="views"||v=="festival"||v=="event"){
		$(function() {
			$("#hidden3").css("display", "none"); 
			$("#hidden1").css("display", ""); 	
			$("#hidden2").css("display", ""); 
		});
	}else {
		$("#hidden3").css("display", ""); 
		$("#hidden1").css("display", "none"); 
		$("#hidden2").css("display", "none"); 
		}
	
}
function loc(obj){
	var bigareaCode=obj.value;
	var url="<%=cp%>/admin/viewscreated_sub.do";
	
	
	$.get(url,{bigareaCode:bigareaCode},function(data){
		$("#areaCode").remove();
		$("#bigarea").after(data);
	});
	
}

function check() {
	
	var cre=document.getElementById("createds");
	
	
	if(cre.title.value==""){
		alert("제목을 입력해 주세요.");
		$("#title").focus();
		return;
	}
	
	
	if(cre.sel.value=="subject"){
		alert("게시글 테이블을 설정해 주세요.");
		$("#sel").focus();
		return;
	}
	
	
	if(cre.content.value==""){
		alert("내용을 입력해 주세요");
		$("#sel").focus();
		return;
	}
	
	var mode="${mode}";
	

	  if(mode=="created"&&cre.sel.value=="views"){
		  
		  cre.action="<%=cp%>/admin/viewscreated_ok.do";
		
	  	 }else if(mode=="update"&&cre.sel.value=="views"){
  		
	  		cre.action="<%=cp%>/viewsadmin/update_ok.do";
			   
	  	 }else if(mode=="created"&&cre.sel.value=="event"){
	  		
	  		cre.action="<%=cp%>/admin/eventupdate_ok.do";
			   
	 	 }else if(mode=="update"&&cre.sel.value=="event"){
	  		
	 		cre.action="<%=cp%>/admin/eventupdate_ok.do";
			   
	  	 }else if(mode=="created"&&cre.sel.value=="festival"){
	  		
	  		cre.action="<%=cp%>/admin/festivalupdate_ok.do";
			   
	  	 }else if(mode=="update"&&cre.sel.value=="festival"){
	  		
	  		cre.action="<%=cp%>/admin/festivalupdate_ok.do";
			   
	     }else if(mode=="created"&&cre.sel.value=="notice"){
		  		
	    	cre.action="<%=cp%>/admin/noticeupdate_ok.do";
			   
	     }else if(mode=="update"&&cre.sel.value=="notice"){
	  		
	    	cre.action="<%=cp%>/admin/noticeupdate_ok.do";
			   
	   		}

	cre.submit();
	
}
</script>
</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container">
		<form id="createds" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>제목 : </td>
				<td><input type="text" id="title" name="title"></td>
			</tr>

			<tr>
				<td>게시판 선택 :</td>
				<td> 
					<select id="sel" name="selectTable" onchange="op(this);">
						<option value="subject">게시글 테이블 선택</option>
						<option value="views">관광</option>
						<option value="festival">축제</option>
						<option value="event">이벤트</option>
						<option value="notice">공지사항</option>
					</select>
				</td>
			</tr>
		
		
			<tr>
				<td>글 본문 : </td>
				<td><textarea id="content" name="content" style="resize: none;"></textarea></td>
			</tr>
	
			
			<tr id="hidden1" style="display: none;">
				<td>썸네일 이미지</td>
				<td><input type='file' name='someNail_upload' accept='image/*'></td>
			<tr>
			
			<tr id="hidden2" style="display: none;">
				<td>본문 이미지</td>
				<td><input type='file' name='body_upload' accept='image/*'></td>
			</tr>
			
			<tr id="hidden3" style="display: none;">
				<td>첨부 파일</td>
				<td><input type='file' name='notice_upload'></td>
			</tr>
			
			<tr>
			
				<td><button type="button" onclick="check();">글 올리기</button> <button>돌아가기</button></td>
		
		
			</tr>
			</table>
		</form>
	</div>
</div>

<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>