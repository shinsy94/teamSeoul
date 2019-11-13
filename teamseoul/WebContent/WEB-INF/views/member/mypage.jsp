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
   		  
   			<img src="<%=cp%>/resource/images/mymem.jpg" width="6%"><h3>회원정보</h3>
       	        	
        	<table style="width: 80%; float: left; text-align: left;  border-radius: 10px;">   
        	 <c:forEach  items="#">    		
            	<tr>
            		<td style="text-align: right;"><label>아이디</label></td>
            		<td class="member" id="userId">${dto.userId}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>이름</label></td>
          	  		<td class="member" id="userName">${dto.userName }</td>
          	  	</tr>
          	  	<tr>
          	  		<td style="text-align: right;"><label>연락처</label></td>
            		<td class="member" id="userTel">${dto.userTel}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>이메일</label></td>
            		<td class="member" id="userEmail">${dto.userEmail}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>생년월일</label></td>
            		<td class="member" id="userBirth">${dto.userBirth}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>욜로카운트</label></td>
            		<td class="member" id="yoloCount">${dto.yoloCount}</td>
            	</tr>
            	<tr>
            		<td style="text-align: right;"><label>유저등급</label></td>
            		<td class="member" id="tierCode">${dto.tierCode}</td>
            	</tr>
        	</c:forEach>
          		<tr>
            		<td>&nbsp;&nbsp;</td>
            		<td>&nbsp;&nbsp;</td>
            		<td style= "float: center; text-align: right;"><a href="#" style="color: #3e3d3d; ">정보수정&gt; </a></td>		
            	</tr>
            </table>
            </div>
            
            
        <div class="body-title" style="width: 45%; float: left;">
            <img src="<%=cp%>/resource/images/myfav.png" width="6%"><h3>즐겨 찾기 </h3>
			 		
			<div style="width: 90%; margin: 10px auto 0px;">
				<table>
			    	<tr>
			    		<td><a href:"#">즐겨찾기 내용</a></td>
			   		</tr>
			    </table>
			</div>
		    
		    
   		</div>
   		
   		
   		<div class="body-title" style="width: 90%; float: left;">
   			<img src="<%=cp%>/resource/images/mytxt.png" width="4%"><h3>내가 쓴 글</h3>
   			<div style="width: 80%; margin: 10px auto 0px;">
   				<table>
            		<tr>
            			<td class="title"><a href="<%=cp%>/notice/list.do">title</a></td>
            			<td class="userId" >userId</td>
            			<td class="created" >created</td>
            		</tr>
         	<tr>
            		<td>&nbsp;&nbsp;</td>
            		<td>&nbsp;&nbsp;</td>
            		<td style= "float: right; text-align: right;"><a href="<%=cp%>/yolo/list.do">더보기&gt; </a></td>
            		
            </tr>
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