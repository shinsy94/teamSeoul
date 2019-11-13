<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

.in{
	text-align: center;
	font-size: 25px;
	background: #004B58;  
	color: white;
	outline:none;
	height: 70px ;
	width: 200px;
	border:none;
	font-size: 25px;
	cursor:pointer;
	transition:900ms ease all;
	margin: 8px;
	outline: none;
}



textarea:focus {

outline:none;
}

textarea{
	width: 100%;
	height: 90%;
	resize: none;
	border: none;
    font-size: 30px;
    wrap=hard;
    overflow-y: hidden;
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


</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
var fileNum=${fn:length(dto.imageFileName)};
var aId="admin_aId";
var acode=1;
var fileNum2=0;

$(document).ready(function() {
    var table="${table}";
	
var url="<%=cp%>/admin/update_sub.do";
	$("select[name=selectTable]").val(table);
	if(table=="views"){
		
		$.get(url,{table:table,bigareaCode:"${bigareaCode}",areaCode:"${dto.areaCode}"},function(data){
			
			 $("#sel").after(data);
			 $("#bigarea").val("${bigareaCode}");
			 $("#areaCode").val("${dto.areaCode}");

		});
	}else if(table=="festival"){
		$.get(url,{table:table,seasonCode:"${seasonCode}"},function(data){
			$("#sel").after(data);
			$("#season").val("${seasonCode}")

		});
	}else if(table=="event"){
		$("#hidden").css("display", "");
	}else{
		$("#hidden3").css("display", "");
	}
	
   
});



function loc(obj){
	var bigareaCode=obj.value;
	var url="<%=cp%>/admin/created_sub.do";
	
	
	$.get(url,{bigareaCode:bigareaCode},function(data){
		$("#areaCode").remove();
		$("#bigarea").after(data);
	});
	
}

function updatecheck() {
	
	var cre=document.getElementById("updated");
	
	var tit=cre.title.value;
	
	var seltable="${table}";
	
	
	if(tit.trim()==""){
		alert("제목을 입력해 주세요.");
		$("#title").focus();
		return;
	}
	
	
	var conts=cre.content.value;
	
	if(conts.trim()==""){
		alert("내용을 입력해 주세요");
		$("#content").focus();
		return;
	}
	
	if(seltable=="event"&&cre.eventLink.value.trim()==""){
		alert("이벤트링크 주소를 입력해 주세요");
		return;
	}
	
	if((seltable=="views"||seltable=="festival"||seltable=="event")&&cre.someNail_upload.value!=""){
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(cre.someNail_upload.value)){
			alert('이미지 파일만 업로드가 가능합니다.');
			f.upload.focus();
			return;
		}
	}
	
	if((seltable=="views"||seltable=="festival"||seltable=="event")&&cre.body_upload.value!=""){
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(cre.body_upload.value)){
			alert('이미지 파일만 업로드가 가능합니다.');
			f.upload.focus();
			return;
		}
	}
	
	

	  if(seltable=="views"){
		
		  cre.action="<%=cp%>/admin/viewsupdate_ok.do";
		 
	  	 }else if(seltable=="event"){
	  
	  		cre.action="<%=cp%>/admin/eventupdate_ok.do";
			   
	 	 }else if(seltable=="festival"){
	
	  		cre.action="<%=cp%>/admin/festivalupdate_ok.do";
			   
	  	 }else if(seltable=="notice"){
	    	cre.action="<%=cp%>/admin/noticeupdate_ok.do";
			   
	     }

	cre.submit();
	
}

function fileMore(){
	if((fileNum+fileNum2)==5){
		alert("파일은 5개까지만 추가 하실 수 있습니다.");
		return;
	}
	
		$("#addFiles").append("<tr><td class='trs'><h3>추가 이미지</h3></td><td class='ccc' >"
		+"<input type='file' id='upload"+(fileNum)+"' name='upload"+(fileNum)+"' >"
		+"<button type='button' id='"+aId+(fileNum)+"' onclick='deleteFile(this);'>삭제</button></td></tr>");
		fileNum++;

}

function deleteFile(as){
	var dele="#"+$(as).attr('id');
	$(function() {
		$(dele).closest("tr").remove();
		fileNum2--;
	});
}
	

function someupdate(){
	$("#hidden1").css("display", ""); 
	$("#upfile1").css("display", "none");
}

function bodyupdate(){
	$("#hidden2").css("display", ""); 
	$("#upfile2").css("display", "none");
	
}
function someupdateDel(){
	$("#hidden1").val("");
	$("#hidden1").css("display", "none"); 
	$("#upfile1").css("display", "");
}
function bodyupdateDel(){
	$("#hidden2").val("");
	$("#hidden2").css("display", "none"); 
	$("#upfile2").css("display", "");
}

function notiupdate(){
	$("#notice_upload").css("display", ""); 
	$("#notifile").css("display", "none");
}
function notiupdateDel(){
	$("#notice_upload").val("");
	$("#notice_upload").css("display", "none"); 
	$("#notifile").css("display", "");
}

function removeFile(obj){
	var name=$("input[name="+obj+"]").val();
	
	if(confirm(name+" 파일을 삭제하시겠습니까?\n삭제된 파일은 복구가 불가능합니다.")){
		var delfurl="<%=cp%>/admin/deleteFile.do";
		$.get(delfurl,{table:"${table}",num:"${num}",imageFileName:name},function(data){
			alert(data);
			$("input[name="+obj+"]").closest("tr").remove();
		});
	}
}
</script>
</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div style="position: relative; top: 155px; z-index: 1; clear:both; width:100%;  margin: 0px auto; text-align: center;" >
    <div class="body-title" style="width: 60%; margin: 10px auto; text-align: left;"><h3>글쓰기</h3></div> 
    	<div>
		<form id="updated" method="post" enctype="multipart/form-data">
		<table style="border-collapse: collapse;border-spacing: 0;">
  
			<tr >
				<td class="trs"><h3>제목</h3> </td>
				<td class="ccc" >
				<textarea  id="title" name="title" maxlength="25"
				style="padding-top: 30px; padding-left: 20px; ">${dto.title}</textarea></td>
			</tr>
  
			<tr >
				<td class="trs" ><h3>게시판</h3></td>
				<td  class="ccc"> 
					<span id="sel">
					  <input class="in" readonly="readonly"  name="selectTable" onchange="op(this);" value="${table=='event' ? '이벤트':(table=='views' ? '관광':(table=='notice' ? '공지':'축제'))}">
					
					</span>
				</td>

			</tr>
		
		
			<tr>
				<td class="trs"><h3>글 본문</h3> </td>
				<td  class="ccc">
					<textarea id="content" name="content" maxlength="1000"  style="height: 600px; padding-left: 20px;padding-top: 30px;">${dto.content}</textarea></td>
			</tr>
		<c:if test="${table=='event'}">
			<tr id="hidden" style="display: none;">
				<td class="trs"><h3>이벤트 링크</h3></td>
				<td  class="tdMidle"><input type="text" name='eventLink' value="${dto.eventLink}" ></td>
			</tr>
		</c:if>
		<c:if test="${table!='notice'}">	
			<tr id="upfile1">
				<td  class='trs'>
					<h3>썸네일 이미지</h3>
				</td>
				
				<td class="ccc">
					<input name="orisome" readonly="readonly" type="text" value="${some}"><a style="float:right;padding-top: 20px;padding-right:5px" href="javascript:someupdate();">수정</a>
				</td>			
			</tr>
			<tr id="hidden1" style="display: none;">
				<td class="trs"><h3>썸네일 수정</h3></td>
				<td  class="ccc">
				<input type='file' id="someNail_upload" name="someNail_upload" accept='image/*'><a style="color: blue;" href="javascript:fileMore();">더 올리기</a><a style="float:right;padding-top: 20px;padding-right:5px" href="javascript:someupdateDel();">취소</a></td>
			<tr>
									
			<tr id="upfile2" >
				<td class='trs'>
			
					<h3>본문 이미지</h3>
				</td>
				
				<td class="ccc">
					<input name="oribody" readonly="readonly" type="text" value="${body}"><a style="float:right;padding-top: 20px;padding-right:5px" href="javascript:bodyupdate();">수정</a>
				</td>
			</tr>
			<tr id="hidden2" style="display: none;">
				<td class="trs">
					<h3>본문 수정</h3>
				</td>
				<td  class="ccc">
				<input type='file' id="body_upload" name='body_upload' accept='image/*'><a style="float:right;padding-top: 20px;padding-right:5px" href="javascript:bodyupdateDel();">취소</a></td>
			</tr>
				
			<c:forEach var="fi" items="${dto.imageFileName}" varStatus="status">
				<tr>
					<td class='trs'>
						<h3>추가된 이미지</h3>
					</td>  
					<td class="ccc">	
						<input name="upfile${status.count}" readonly="readonly" type="text" value="${fi}"><a style="float:right;padding-top: 20px;padding-right:5px"  href="javascript:removeFile('upfile${status.count}');">삭제</a>
					</td>
				</tr>			
			</c:forEach>
		</c:if>
			
		<c:if test="${table=='notice'}">
			<tr id="hidden3" >
				<td class="trs">
					<h3>첨부 파일</h3>
				</td>
				<td  class="ccc">
					<input type='text' readonly="readonly" name='notifile' value="${dto.originalFileName}"><a style="float:right;padding-top: 20px;padding-right:5px" href="javascript:notiupdate();">삭제</a>
				</td>
			</tr>
			
			<tr id="nohedden" style="display: none;">
				<td class="trs">
					<h3>첨부 파일</h3>
				</td>
				
				<td  class="ccc">
					<input type="file"  id="notice_upload" name='notice_upload'><a style="float:right;padding-top: 20px;padding-right:5px" href="javascript:notiupdateDel();">취소</a>
				</td>
			</tr>
		</c:if>
			</table>
			<table id="addFiles" style="border-collapse: collapse;border-spacing: 0;margin: 0px auto;"></table>
			<br>
			<button class="bb" type="button" onclick="updatecheck();">수정완료</button> <button class="bb">취소</button>
			
			<input type="hidden" name="originalFileName" value="${originalFileName}">
			<input type="hidden" name="saveFileName" value="${saveFileName}">
			<input type="hidden" name="fileSize" value="${fileSize}">
			<input type="hidden" name="num" value="${num}">
			<input type="hidden" name="table" value="${table}">
		</form>
	</div>
</div>

<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>