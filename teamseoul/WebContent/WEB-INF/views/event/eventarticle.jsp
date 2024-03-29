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

<link href="https://fonts.googleapis.com/css?family=Jua&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Nanum+Brush+Script&display=swap" rel="stylesheet">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

function eventupdate(num) {
	<c:if test="${sessionScope.member.userId==dto.userId}">
	    var url="<%=cp%>/admin/updateForm.do?num="+num+"&table=event";
	    location.href=url;
	</c:if>
	<c:if test="${sessionScope.member.userId!=dto.userId}">
	   alert("게시물을 수정할 수  없습니다.");
	</c:if>
	}

function eventdelete(num) {
<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
    if(confirm("게시물을 삭제 하시겠습니까 ?")) {
    	 var url="<%=cp%>/admin/delete_ok.do?num="+num+"&table=event";
    	 location.href=url;
    }	
</c:if>
<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
    alert("게시물을 삭제할 수  없습니다.");
</c:if>
}


</script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
<div class="body-title Jua" style="width: 65%; margin: 5px auto; text-align: left;">
	<h3 style= "width: 30%; text-align: left; margin: 10px; font-size: xx-large;">${dto.title}</h3>
	</div>
	
		<div style="width: 64%; margin: 5px auto; text-align: left; font-size: x-large;">
			${dto.content}
		</div>
	

	<div class="body-title" style="width: 60%; margin: 5px auto; text-align: left;">
<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
     	<tr style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
        	<td width="40%" align="right" style="padding-right: 10px; border-spacing: 0px; border-collapse: collapse;">
			     작성일 : ${dto.created}
			</td>
		</tr>
	</table>
    </div>	
	    <div style="width: 80%; margin: 10px auto 0px; text-align: center;">
	    	<a href="${dto.eventLink}">
	    		<img src="<%=cp%>/uploads/event/${dto.imageName}" width="70%" style="margin: 0px 10px;">
	    	</a>

			<div class="note-bottom">
			<br><br>
			</div>

	   <table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px; ">
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="eventupdate('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="eventdelete('${dto.num}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/event/eventlist.do?page=${page}';">리스트</button>
			    </td>
			</tr>
			</table>
			</div>
	    </div>
	    

<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>