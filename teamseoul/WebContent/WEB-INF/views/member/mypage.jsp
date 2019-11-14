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
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>



</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container">

   		<div class="body-title" style="width: 45%; float: left;">
   		  
   			<img src="<%=cp%>/resource/images/mymem.jpg" width="6%">&nbsp;&nbsp;<h3>회원정보</h3>
        <table style="width: 80%; float: left; text-align: left;  border-radius: 10px;">   
       	        	  		
            	<tr>
            		<td style="text-align: right;"><label>아이디</label></td>
            		<td class="member" id="userId">${userDto.userId}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>이름</label></td>
          	  		<td class="member" id="userName">${userDto.userName }</td>
          	  	</tr>
          	  	<tr>
          	  		<td style="text-align: right;"><label>연락처</label></td>
            		<td class="member" id="userTel">${userDto.userTel}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>이메일</label></td>
            		<td class="member" id="userEmail">${userDto.userEmail}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>생년월일</label></td>
            		<td class="member" id="userBirth">${userDto.userBirth}</td>
            	</tr>
            	
          		<tr>
            		<td>&nbsp;&nbsp;</td>
            		<td>&nbsp;&nbsp;</td>
            	 <c:if test="${not empty sessionScope.member}">
            		<td style= "float: center; text-align: right;"><a href="<%=cp%>/member/pwd.do?mode=update">정보수정&gt; </a></td>
            	 </c:if>			
            	</tr>
            </table>
            </div>
            
            
        <div class="body-title" style="width: 45%; float: left;">
            <img src="<%=cp%>/resource/images/myfav.png" width="6%">&nbsp;&nbsp;<h3>즐겨 찾기 </h3>
			 		
			<div style="width: 90%; margin: 10px auto 0px;">
				<table>
				  
			    	<tr>
			    		<td><a href:"#">즐겨찾기 내용</a></td>
			    		<td></td>
			   		</tr>
			   	  
			    </table>
			</div>
		    
		    
   		</div>
   		
   		
   		<div class="body-title" style="width: 90%; float: left;">
   			<img src="<%=cp%>/resource/images/mytxt.png" width="4%">&nbsp;&nbsp;<h3>내가 쓴 글</h3>
   			<div style="width: 80%; margin: 10px auto 0px;">
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="center" bgcolor="#004B58" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <th width="60" style="color: white;">번호</th>
			      <th style="color: white;">제목</th>
			      <th width="100" style="color: white;">작성자</th>
			      <th width="120" style="color: white;">작성일</th>
			      <th width="60" style="color: white;">조회수</th>
			  </tr>
						  
			 
			 <c:forEach var="vo" items="${yList}" varStatus="status">
			  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td>${yList.size()-status.index}</td>
			      <td align="left" style="padding-left: 10px;">
			           <a href="<%=cp%>/yolo/article.do?num=${vo.num}&page=1&condition=y.userId&keyword=${sessionScope.member.userId}">${vo.title}</a>
			      </td>
			      <td>${vo.userId}</td>
			      <td>${vo.created}</td>
			      <td>${vo.hitCount}</td>
			  </tr>
			  </c:forEach>
			  <c:if test="${yList.size() >= 5 }">
			      <tr align="right">
			          <td colspan="5"><a href="<%=cp%>/yolo/list.do?condition=y.userId&keyword=${sessionScope.member.userId}">더보기</a></td>
			      </tr>
			  </c:if>
			  <c:if test="${yList.size() == 0 }">
			      <tr align="center">
			          <td colspan="5">등록된 게시물이 없습니다.</td>
			      </tr>
			  </c:if>
			  
            </table>
   				
   			</div>
   	</div>
   </div>
   
</div>


<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>