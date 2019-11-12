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

<link href="https://fonts.googleapis.com/css?family=Nanum+Myeongjo&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Jua&display=swap" rel="stylesheet">

<style type="text/css">
.main {
font-family: 'Nanum Myeongjo', serif;
}
.category {
font-family: 'Jua', sans-serif;
}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>


</head>

<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container">
    		<img src="<%=cp%>/resource/images/main_top.jpg" width="1200">
    
    		<div class="note-top main">
    		<br>
				니.서.알은 서울의 아름다운 관광 문화에 대한 애정에서 시작되었습니다.
				서울은 단순히 대한민국의 수도만이 아닌, 깊은 역사와 더불어 특별한 문화가 있는 장소입니다.
				단순한 여행으로 끝나는 것이 아니라 서울을 사랑하는 사람들에게 특별한 추억이 될 수 있도록 하며,더불어 다양한 사람들 간의 참된 소통과 어울림을 통해 친근한 서울 여행이 될 수 있도록 하는 것이 저희의 일입니다.
				니.서.알에는 특별함이 있습니다.
				풍성하고 섬세한 서울의 정보들로 당신의 여행이 풍족할 수 있도록 도와드립니다.
				아름답고 새로운 서울 여행을 꿈꾼다면 저희 니.서.알과 함께 하세요.<br><br>
			</div>
			
			<div class="note-bottom main">
				당신의 특별한 서울여행을 서포트합니다.<br>
				-서울의 모든 관광소개서 "니.서.알"-
			</div>
			
			
			
   			<div class="body-title category" style="width: 100%; text-align: left;">
           		<img src="<%=cp%>/resource/images/notice.png" width="4%">&nbsp;<h3>공지사항 </h3>
      		</div>
            <table>
            	
            	
            <c:forEach begin="1" end="4">	
            	<tr>

            		<td class="title"><a href="<%=cp%>/notice/list.do">title</a></td>
            		<td class="userId" >userId</td>
            		<td class="created" >created</td>
            	
            	</tr>
            </c:forEach>
          <tr>
            		<td>&nbsp;&nbsp;</td>
            		<td>&nbsp;&nbsp;</td>
            		<td style= "float: right; text-align: right;"><a href="<%=cp%>/notice/list.do" style="color: #3e3d3d; ">더보기&gt; </a></td>
            		
            	</tr>
            </table>
             <div class="body-title category" style="width: 100%; text-align: left;">
           		<img src="<%=cp%>/resource/images/view.png" width="4%">&nbsp;<h3>관광지&amp;축제 </h3>
		    </div>
		    <c:forEach begin="1" end="3">	 		
			    <div style="width: 110%; margin: 20px auto 0px;">
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