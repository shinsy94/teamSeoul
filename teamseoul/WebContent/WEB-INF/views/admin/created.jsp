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
<style type="text/css">


table{
	width:50%;
	margin: 0px auto;
    border-collapse: collapse;
    border-spacing: 0;
}

tr{
box-sizing: border-box;
}

td{
box-sizing: border-box;
} 

.trs{
height: 50px;
width: 15%;
font-size:18px;
border-bottom: 1px solid #004B58;
text-align: center;
}


textarea:focus {

outline:none;
}
select{
height: 70px ;
width: 200px;
text-align-last:center;
border:none;
font-size: 25px;
cursor:pointer;
transition:600ms ease all;
margin: 8px;

}
select:hover{
background: #004B58;  
color: white;

}
select:focus{
outline:none;
}

select::-ms-expand {
display: none;
}

textarea{
	width: 100%;
	height: 90%;
	resize: none;
	border: none;
    font-size: 30px;
    wrap=hard;
    overflow-y: auto;;
    box-sizing: border-box;
 
}


.bb{
  background: #004B58;  
  color:white;
  border:none;
  position:relative;
  height:40px;
  font-size:1.5em;
  padding:0 2em;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.bb:hover{
  background:white;
  color:black;
}
.bb:before,.bb:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: black;
  transition:400ms ease all;
}
.bb:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}
.bb:hover:before,.bb:hover:after{
  width:100%;
  transition:800ms ease all;
}
span{
	float: left;
	
}
.ccc{
	width: 45%;
	height: 30px;
	border-bottom: 1px solid #004B58;
}

.ccc:hover{
	border: 1px solid #004B58;
	transition:500ms ease all;
}
  
option{
	text-align: center;
}

input{
	border: none;
	width: 270px;  
	height: 50px;
	margin: 3px;
	float: left;
}



.delButton{
	width: 50px;
	height: 30px;
	background: #004B58;
	color: white;
	font-size: 14px;
	float: right;
	outline:none;
	border:none;
	margin-top: 12px;
	margin-right: 10px;
}
.delButton:hover{

  font-weight:bold;
  
}

.addButton{
	width: 70px;
	height: 40px;
	background: #004B58;
	color: white;
	font-size: 14px;
	float: right;
	outline:none;
	border:none;
}
.addButton:hover{

  font-weight:bold;
}

.addButton{
	width: 70px;
	height: 40px;
	background: #004B58;
	color: white;
	font-size: 14px;
	float: right;
	outline:none;
	border:none;
}
.addButton:hover{

  font-weight:bold;
}



</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
var fileNum=1;
var aId="admin_aId";
var acode=1;
var fileNum2=0;
function op(obj){
	var v=obj.value;
	if(v=="views"){
		$(function() {	
		  $("#bigarea").parent().remove();
		  $("#season").remove();
		  var urls="<%=cp%>/admin/created_sub.do";
		  $.get(urls,{bigareaCode:"1"},function(data){
		  $("#sel").after("<span><select id='bigarea' name='bigarea' onchange='loc(this);'>"
			+"<option value='1'>서울북부</option><option value='2'>서울동부</option>"
			+"<option value='3'>서울서부</option><option value='4'>서울남부</option></select> "+data+"</span>");
			
		  });
			$("#selectTable").focus();
		});
	}else if(v=="festival"){
		$(function() {	
			$("#bigarea").parent().remove();
			
			$("#sel").after("<span><select id='season' name='season'>"
					+"<option value='1'>봄</option><option value='2'>여름</option>"
					+"<option value='3'>가을</option><option value='4'>겨울</option></select></span>");
	
			
			$("#sel").focus();
		});
	}else{
		$(function() {	
			$("#season").parent().remove();
			$("#bigarea").parent().after().remove();
		});
	}
	

	if(v=="views"||v=="festival"||v=="event"){
		$(function() {
			$("#hidden3").css("display", "none"); 
			$("#imgTable").css("display", ""); 	
			$(".addButton").css("display", "");
		});
	}else if(v=="subject"){
		$("#hidden3").css("display", "none"); 
		$("#imgTable").css("display", "none");
		$(".addButton").css("display", "none");
		
	 }else {
		$("#hidden3").css("display", ""); 
		$("#imgTable").css("display", "none");
		$(".addButton").css("display", "none");
		
		}
	
	if(v=="event"){
		$("#hidden").css("display", "");
	}else{
		$("#hidden").css("display", "none");
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
	
	var tit=cre.title.value;
	
	
	if(tit.trim()==""){
		alert("제목을 입력해 주세요.");
		$("#title").focus();
		return;
	}
	
	var selecdata=cre.selectTable.value;
	if(selecdata=="subject"){
		alert("게시글 테이블을 설정해 주세요.");
		$("#sel").focus();
		return;
	}
	
	var conts=cre.content.value;
	
	if(conts.trim()==""){
		alert("내용을 입력해 주세요");
		$("#content").focus();
		return;
	}

	if((selecdata=="views"|selecdata=="festival"|selecdata=="event")&&cre.someNail_upload.value==""){
		alert("썸네일 사진을 올려주세요");
		return;
	}
	
	if(selecdata=="views"|selecdata=="festival"|selecdata=="event"&&cre.body_upload.value==""){
		alert("본문 사진을 올려주세요");
		return;
	}
	
	if(selecdata=="event"&&cre.eventLink.value.trim()==""){
		alert("이벤트링크 주소를 입력해 주세요");
		return;
	}
	if(selecdata=="views"||selecdata=="festival"||selecdata=="event"){
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(cre.someNail_upload.value)||
				! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(cre.body_upload.value)) {
			alert('이미지 파일만 업로드가 가능합니다.');
			f.upload.focus();
			return;
		}
	}
	

	  if(selecdata=="views"){
		  $("#hidden3").remove();
		  cre.action="<%=cp%>/admin/viewscreated_ok.do";
		
	  	 }else if(selecdata=="event"){
	  		 $("#hidden3").remove();
	  		cre.action="<%=cp%>/admin/eventcreated_ok.do";
			   
	 	 }else if(selecdata=="festival"){
	 		 $("#hidden3").remove();
	  		cre.action="<%=cp%>/admin/festivalcreated_ok.do";
			   
	  	 }else if(selecdata=="notice"){
	  		 $("#imgTable").remove();
	    	cre.action="<%=cp%>/admin/noticecreated_ok.do";
			   
	     }

	cre.submit();
	
}

function fileMore(){
	if((fileNum+fileNum2)==6){
		alert("파일은 5개까지만 추가 하실 수 있습니다.");
		return;
	}
	$(function() {
		$("#imgTable").append("<tr><td class='trs'><h3>추가 이미지</h3></td><td class='ccc' >"
		+"<input type='file' id='upload"+(fileNum)+"' name='upload"+(fileNum)+"' >"
		+"<button class='delButton' style='background:#bf0e0e;' type='button' id='"+aId+(fileNum)+"' onclick='deleteFile(this);'>삭제</button></td></tr>");
		fileNum++;
	});
}

function deleteFile(as){
	var dele="#"+$(as).attr('id');
	$(dele).closest("tr").remove();
	fileNum2--;
}
	
function update(){
	var crt=document.getElementById("createds");
	crt.action="<%=cp%>/admin/updateForm.do";
	crt.submit();
}

</script>
</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div style=" position: relative; top: 155px; z-index: 1; clear:both; width:100%; margin: 0px auto; text-align: center;" >
    <div class="body-title" style="width: 50%; margin: 10px auto; text-align: left;"><h3>글쓰기</h3></div> 
    	<div>
		<form id="createds" method="post" enctype="multipart/form-data">
		<table style="border-collapse: collapse;border-spacing: 0;">
  
			<tr>
				<td class="trs"><h3>제목</h3> </td>
				<td class="ccc" >
				<textarea placeholder="    제목을 입력해 주세요" id="title" name="title" maxlength="25"
				style="padding-top: 30px; padding-left: 20px; "></textarea></td>
			</tr>

			<tr >
				<td class="trs" ><h3>게시판</h3></td>
				<td  class="ccc"> 
					<span id="sel">
					  <select  name="selectTable" onchange="op(this);">
						<option value="subject">선택</option>
						<option value="views">관광</option>
						<option value="festival">축제</option>
						<option value="event">이벤트</option>
						<option value="notice">공지사항</option>
					</select>
					</span>
				</td>

			</tr>
		
		
			<tr>
				<td class="trs"><h3>글 본문</h3> </td>
				<td  class="ccc">
					<textarea  placeholder="  본문내용을 입력해 주세요" id="content" name="content" maxlength="1000"  style="height: 600px; padding-left: 20px;padding-top: 30px;"></textarea></td>
			</tr>
			
			<tr id="hidden" style="display: none;">
				<td class="trs"><h3>이벤트 링크</h3></td>
				<td class="ccc" ><input type="text" name='eventLink' ></td>
			</tr>
			
			<tr id="hidden3" style="display: none;">
				<td class="trs"><h3>첨부 파일</h3></td>
				<td  class="ccc">
				<input type='file' id="notice_upload" name='notice_upload'></td>
			</tr>	
			
						
			</table>  
			
			<table id="imgTable" style="border-collapse: collapse;border-spacing: 0; display: none;">

				<tr>
					<td class="trs"><h3>썸네일 이미지</h3></td>
					<td  class="ccc">
						<input type='file' id="someNail_upload" name="someNail_upload" accept='image/*'></td>

				<tr>
			
				<tr>
					<td class="trs"><h3>본문 이미지</h3></td>
					<td  class="ccc">
						<input type='file' id="body_upload" name='body_upload' accept='image/*'></td>
				</tr>
			
			</table>
			<br>
			<div class="body-title" style="width: 50%; margin: 10px auto;">
			<button class="bb" type="button" onclick="check();">업로드</button> <button class="bb" onclick="update();">취소</button>
			<button type="button" style="display: none;" class="addButton"  onclick="fileMore();">파일 추가</button></div>
		</form>
	</div>
</div>

<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>