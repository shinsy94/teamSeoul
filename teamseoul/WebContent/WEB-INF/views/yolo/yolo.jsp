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
<title>DoYouKnowSeoul</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<link href="https://fonts.googleapis.com/css?family=Jua&display=swap" rel="stylesheet">

<style type="text/css">
.category {
font-family: 'Jua', sans-serif;
}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
function searchList() {
	var f=document.searchForm;
	f.submit();
}
</script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container" style="position: relative; top: 155px; z-index: 1;">
    <div class="body-container"> 
		<div class="body-title category" style="width: 100%; text-align: left;">
            <img src="<%=cp%>/resource/images/yolo.png" width="3%" style="margin: 0px 10px;"><h3>욜로족</h3>
        </div>
        
        <div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="left" width="50%">
			          ${dataCount}개(${page}/${total_page} 페이지)
			      </td>
			      <td align="right">
			          &nbsp;
			      </td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="center" bgcolor="#004B58" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <th width="60" style="color: white;">번호</th>
			      <th style="color: white;">제목</th>
			      <th width="100" style="color: white;">작성자</th>
			      <th width="120" style="color: white;">작성일</th>
			      <th width="60" style="color: white;">조회수</th>
			  </tr>
						  
			 
			 <c:forEach var="dto" items="${listAttention}">
			  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td><span style="display: inline-block; padding: 1px 3px; background: #ED4C00; color: #FFFFFF;">공지</span></td>
			      <td align="left" style="padding-left: 10px;">
			           <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
			      </td>
			      <td>${dto.userId}</td>
			      <td>${dto.created}</td>
			      <td>${dto.hitCount}</td>
			     
			  </tr>
			  </c:forEach>
			  
			 <c:forEach var="dto" items="${list}">
			  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td>${dto.listNum}</td>
			      <td align="left" style="padding-left: 10px;">
			           <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
			      </td>
			      <td>${dto.userId}</td>
			      <td>${dto.created}</td>
			      <td>${dto.hitCount}</td>
			      
			  </tr>
			  </c:forEach>
			</table>
			 
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			        ${dataCount==0?"등록된 게시글이 없습니다.":paging}
				</td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
			   <tr height="40">
			      <td align="left" width="100">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/yolo/list.do';">새로고침</button>
			      </td>
			      <td align="center">
			          <form name="searchForm" action="<%=cp%>/yolo/list.do" method="post">
			              <select name="condition" class="selectField">
			                  <option value="title">제목</option>
			                  <option value="userId">작성자</option>
			                  <option value="content">내용</option>
			                  <option value="created">등록일</option>
			            </select>
			            <input type="text" name="keyword" class="boxTF">
			            <button type="button" class="btn" onclick="searchList()">검색</button>
			        </form>
			      </td>
			      <td align="right" width="100">
			      	<c:if test="${sessionScope.member.userId == 'admin' }">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/yolo/created.do';">글올리기</button>
					</c:if>			     
			      </td>
			   </tr>
			</table>

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