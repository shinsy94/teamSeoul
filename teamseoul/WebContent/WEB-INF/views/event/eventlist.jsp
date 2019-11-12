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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
<div class="body-title Jua" style="width: 65%; margin: 10px auto; text-align: left;">
           		<img src="<%=cp%>/resource/images/event.jpg" width="3%" style="margin: 0px 10px;">
           		<h3 style="width: 10%; margin: 10px auto; text-align: left;"> 두유 노 이벤트~? </h3>
    </div>

	 <div style="width: 80%; margin: 10px auto 0px; ">
	    <table>
	    <c:forEach var="dto" items="${list}">
	    <a href="<%=cp%>/event/eventarticle.do?num=${dto.num}&page=${page}">
	    	<img src="<%=cp%>/uploads/event/${dto.imageName}" width="80%" height=300px style="margin: 0px 10px; padding: 5px;">
	    </a>
	    </c:forEach>
	    </table>
	    </div>

			<div>
			<table style="width:100%; border-spacing:0px; margin-bottom: 20px">
              <tr height="50">
                 <td align="center" colspan='2'>
                   ${dataCount==0?"등록된 게시물이 없습니다.":paging}
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