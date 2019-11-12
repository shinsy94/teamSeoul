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


<c:if test="${sessionScope.member.userId=='admin'}">
function deleteYolo(num) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/yolo/delete.do?num="+num+"&${query}";
		location.href=url;
	}
}
</c:if>

//댓글 등록
$(function(){
   $(".btnSendReply").click(function(){
      var num="${dto.num}";
      // var content=$(".boxTA:first").val();
      var $tb = $(this).closest("table");
      var content=$tb.find("textarea").val().trim();
      if(! content) {
         $tb.find("textarea").focus();
         return;
      }
      content = encodeURIComponent(content);
      
      var query="num="+num+"&content="+content;
      var url="<%=cp%>/yolo/insertReply.do";
      $.ajax({
         type:"post"
         ,url:url
         ,data:query
         ,dataType:"json"
         ,success:function(data) {
            $tb.find("textarea").val("");
            
            var state=data.state;
            if(state=="true") {
               listPage(1);
            } 
         }
          ,beforeSend :function(jqXHR) {
             jqXHR.setRequestHeader("AJAX", true);
          }
          ,error:function(jqXHR) {
             if(jqXHR.status==403) {
                login();
                return;
             }
             console.log(jqXHR.responseText);
          }
      });
   });
});

//댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/yolo/listReply.do";
	var query="num=${dto.num}&pageNo="+page;
	
	$.ajax({
		type:"get"
		,url:url
		,data:query
		,success:function(data) {
			$("#listReply").html(data);
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}


//댓글 삭제
$(function(){
	$("body").on("click", ".deleteReply", function(){
		if(! confirm("게시물을 삭제하시겠습니까 ? "))
		    return;
		
		var url="<%=cp%>/yolo/deleteReply.do";
		var replyNum=$(this).attr("data-replyNum");
		var page=$(this).attr("data-pageNo");
		
		$.ajax({
			type:"post"
			,url:url
			,data:{replyNum:replyNum}
			,dataType:"json"
			,success:function(data) {
				listPage(page);
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
});


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
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-bottom: 1px solid #cccccc; border-top: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
				 제목 : ${dto.title}
			    </td>
			    <td>
			    &nbsp;
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       이름 : ${sessionScope.member.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.created} | 조회 ${dto.hitCount}
			    </td>
			</tr>
		
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			      <img alt="" src="<%=cp%>/uploads/notice/${dto.imageFileName}">
			      <br><br>
			      ${dto.content}
			   </td>
			</tr>

			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			       이전글 :
			         <c:if test="${not empty preReadDto}">
			              <a href="<%=cp%>/yolo/article.do?${query}&num=${preReadDto.num}">${preReadDto.title}</a>
			        </c:if>
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			    다음글 :
			         <c:if test="${not empty nextReadDto}">
			              <a href="<%=cp%>/yolo/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.title}</a>
			        </c:if>
			    </td>
			</tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/yolo/update.do?num=${dto.num}&page=${page}';">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="deleteYolo('${dto.num}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/yolo/list.do?${query}';">리스트</button>
			    </td>
			</tr>
			</table>
			
			
			
			
<%-- !!!!!!!!!!!!!!!여기부터 댓글시작!!!!!!!!!!!!!!! --%>	

<c:if test="${replyCount!=0}">
<table style='width: 100%; margin: 10px auto 30px; border-spacing: 0px;'>

<c:forEach var="dto" items="${listReply}">
    <tr height='35' style='background: orange;'>
       <td width='40%' style='padding:5px 5px; border:1px solid #cccccc; border-right:none;' align='left'>
           <span><b>${dto.userId}</b></span>
        </td>
       <td width='50%' style='padding:5px 5px; border:1px solid #cccccc; border-left:none;' align='right'>
           <span>${dto.created}</span> |
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">		   
          <span class="deleteReply" style="cursor: pointer;" data-replyNum='${dto.replyNum}' data-pageNo='${pageNo}'>삭제</span>
</c:if>		   
<c:if test="${sessionScope.member.userId!=dto.userId && sessionScope.member.userId!='admin'}">		   
          <span class="notifyReply">신고</span>
</c:if>
        </td>
    </tr>
    <tr>
        <td colspan='2' valign='top' style='padding:5px 5px;'>
                ${dto.content}
        </td>
  	</tr>

    
</c:forEach>

	  <div>
            <table style='width: 100%; margin: 15px auto 0px; border-spacing: 0px;'>
            <tr height='30'> 
	            <td align='left'>
	            	<span style='font-weight: bold;'>댓글쓰기</span>
	            </td>
            </tr>
            <tr>
               <td style='padding:5px 5px 0px;'>
                    <textarea class='boxTA' style='width:99%; height: 70px;'></textarea>
                </td>
            </tr>
            <tr>
               <td align='right'>
                    <button type='button' class='btn btnSendReply' style='padding:10px 20px;'>댓글 등록</button>
                </td>
            </tr>
            </table>
            
             <div id="listReply"></div>
 	    </div>
     <tr height="40">
         <td colspan='2'>
              ${paging}
         </td>
     </tr>
</table>
</c:if>	
		
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