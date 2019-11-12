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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container">			
   	
   	<div>
     	<h3>마이페이지</h3>
    </div>
        <table>        		
            <tr>
            	<td class="member" id="userId">${dto.userId}</td>
            	<td class="member" id="userName">${dto.userName}</td>
            	<td class="member" id="userTel">${dto.userTel}</td>
            	<td class="member" id="userEmail">${dto.userEmail}</td>
            	<td class="member" id="userBirth">${dto.userBirth}</td>
            	<td class="member" id="yoloCount">${dto.yoloCount}</td>
            	<td class="member" id="tierCode">${dto.tierCode}</td>
            </tr>
          	<tr>
            	<td>&nbsp;&nbsp;</td>
            	<td>&nbsp;&nbsp;</td>
            	<td style= "float: right; text-align: right;"><a href="<%=cp%>/notice/list.do" style="color: #3e3d3d; ">더보기 &gt; </a></td>        		
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