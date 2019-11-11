<%@ page contentType="text/html; charset=UTF-8"%>
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
<title>Insert title here</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container">
			
   	<div class="body-title" style="width: 100%; text-align: left;">
     	<h3>마이페이지</h3>
    </div>
        <table>
            	         	
        <c:forEach begin="1" end="5">	
            	<tr>
            		<td class="subject"><a href="#">안녕하세요. 니서알입니다.</a></td>
            		<td class="user" >서울대통령</td>
            		<td class="created" >2000-10-01</td>
            	</tr>
            </c:forEach>
          <tr>
            		<td>&nbsp;&nbsp;</td>
            		<td>&nbsp;&nbsp;</td>
            		<td style= "float: right; text-align: right;"><a href="<%=cp%>/notice/list.do" style="color: #3e3d3d; ">더보기 &gt; </a></td>        		
            	</tr>
            </table>
             <div class="body-title" style="width: 100%; text-align: left;">
           		<h3>관광지&amp;축제 </h3>
		    </div>
		    <c:forEach begin="1" end="3">	 		
			    <div style="width: 100%; margin: 10px auto 0px;">
			    	<img src="<%=cp%>/resource/images/main_3.jpg" width="30%" style="margin: 0px 10px;">
			    	<img src="<%=cp%>/resource/images/main_2.jpg" width="30%" style="margin: 0px 10px;">
			    	<img src="<%=cp%>/resource/images/main_3.jpg" width="30%" style="margin: 0px 10px;">
			    </div>
		    </c:forEach>
    </div>

</div>




<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>