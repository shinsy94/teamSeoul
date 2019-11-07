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
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 90px; z-index: 1;" >
<div class="body-title" style="width: 60%; margin: 10px auto; text-align: left;">
           		<h3>메이플스토리 이벤트 </h3>
    </div>
    <c:forEach begin="1" end="1">	 		
	    <div style="width: 80%; margin: 10px auto 0px;">
	    		<img src="<%=cp%>/resource/images/story01.jpg" width="70%" style="margin: 0px 10px;">

	    <div class="note-top">
    		<br>
				니.서.알은 서울의 아름다운 관광 문화에 대한 애정에서 시작되었습니다.
				서울은 단순히 대한민국의 수도만이 아닌, 깊은 역사와 더불어 특별한 문화가 있는 장소입니다.
				단순한 여행으로 끝나는 것이 아니라 서울을 사랑하는 사람들에게 특별한 추억이 될 수 있도록 하며,참된 소통과 어울림이 있는 친근한 서울 여행이 될 수 있도록 하는 것이 저희의 일입니다.
				니.서.알에는 특별함이 있습니다.
				풍성하고 섬세한 서울의 정보들로 당신의 여행이 풍족하도록 도와드립니다.
				아름답고 새로운 서울여행을 꿈꾼다면 저희 니.서.알과 함께 하세요.<br><br>
			</div>
	   <table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
	   
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn" onclick="updatePhoto('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="deletePhoto('${dto.num}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/photo/list.do?page=${page}';">리스트</button>
			    </td>
			</tr>
			</table>
			
	    </div>
	    
    </c:forEach>
	</div>
<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>