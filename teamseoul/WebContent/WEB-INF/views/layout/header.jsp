<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<script type="text/javascript">
//엔터 처리
$(function(){
	   $("input").not($(":button")).keypress(function (evt) {
	        if (evt.keyCode == 13) {
	            var fields = $(this).parents('form,body').find('button,input,textarea,select');
	            var index = fields.index(this);
	            if ( index > -1 && ( index + 1 ) < fields.length ) {
	                fields.eq( index + 1 ).focus();
	            }
	            return false;
	        }
	     });
});
</script>
<div class="header-layout">
    <div class="header-left">
        <p style="margin: 2px;">
            <a href="<%=cp%>/" style="text-decoration: none;">
                <img src="<%=cp%>/resource/images/logo.png" width="300px">
            </a>
        </p>
    </div>
    
    <div class="menu">
	    <ul class="nav" >
	        <li>
	            <a href="<%=cp%>/views/views.do">관광지</a>
	        </li>
				
	        <li>
	            <a href="<%=cp%>/event/festival.do">축제</a>
	        </li>
	
	        <li>
	            <a href="<%=cp%>/event/eventlist.do">이벤트</a>
	        </li>
	
	        <li>
	            <a href="<%=cp%>/yolo/list.do">욜로족</a>
	        </li>
	
	        <c:if test="${sessionScope.member.userId != 'admin'}">
	        <li>
	            <a href="<%=cp%>/member/mypage.do">마이페이지</a>
	        </li>
	        </c:if>
	        
	        <c:if test="${sessionScope.member.userId == 'admin'}">
	        <li>
	            <a href="<%=cp%>/admin/created.do">관리자</a>
	        </li>
	        </c:if>
	        
	    </ul>      
	</div>

    <div class="header-right">
        <div style="padding-top: 20px;">
            <c:if test="${empty sessionScope.member}">
                <a href="<%=cp%>/member/login.do">
					<img src="<%=cp%>/resource/images/login.png" width="30"> 
				</a>
                    &nbsp;&nbsp;
                <a href="<%=cp%>/member/member.do">
					<img src="<%=cp%>/resource/images/join.png" width="40"> 
				</a>
            </c:if>
            
            <c:if test="${not empty sessionScope.member}">
           		<span style="color:#f48e36;">${sessionScope.member.tierName}</span>
                <span style="color:black;">${sessionScope.member.userName}</span>님
                    <a href="<%=cp%>/member/logout.do" style="display: block;">로그아웃</a>
            </c:if>
        </div>
    </div>
</div>


