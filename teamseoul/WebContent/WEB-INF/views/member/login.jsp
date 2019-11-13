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

<style type="text/css">
.lbl {
   position:absolute; 
   margin-left:15px; margin-top: 17px;
   color: #999999; font-size: 11pt;
}
.loginTF {
  width: 340px; height: 35px;
  padding: 5px;
  padding-left: 15px;
  border:1px solid #999999;
  color:#333333;
  margin-top:5px; margin-bottom:5px;
  font-size:14px;
  border-radius:4px;
}
</style>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript">
function bgLabel(ob, id) {
    if(!ob.value) {
	    document.getElementById(id).style.display="";
    } else {
	    document.getElementById(id).style.display="none";
    }
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/login_ok.do";
    f.submit();
}
</script>

</head>
<body>

<div class="header" style="position: fixed; z-index: 2;">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container Jua" style="position: relative; top: 155px; z-index: 1;" >
	<img src="<%=cp%>/resource/images/key.png" width="35">&nbsp;&nbsp;<span style="font-size: 50px;">로그인</span>
	    <div style="margin: 30px auto 0px; width:360px; font-size:20px;">
			<form name="loginForm" method="post" action="">
			  <table style="margin: 15px auto; width: 360px; border-spacing: 0px;">
			  <tr align="center" height="60"> 
			      <td> 
	                <label for="userId" id="lblUserId" class="lbl" style="font-size:20px;">아이디</label>
			        <input type="text" name="userId" id="userId" class="loginTF" maxlength="15"
			                   tabindex="1"
	                           onfocus="document.getElementById('lblUserId').style.display='none';"
	                           onblur="bgLabel(this, 'lblUserId');">
			      </td>
			  </tr>
			  <tr align="center" height="60"> 
			      <td>
			        <label for="userPwd" id="lblUserPwd" class="lbl" style="font-size:20px;">패스워드</label>
			        <input type="password" name="userPwd" id="userPwd" class="loginTF" maxlength="20" 
			                   tabindex="2"
	                           onfocus="document.getElementById('lblUserPwd').style.display='none';"
	                           onblur="bgLabel(this, 'lblUserPwd');">
			      </td>
			  </tr>
			  <tr align="center" height="65" > 
			      <td>
			        <button type="button" onclick="sendLogin();" class="btnConfirm Jua" style="font-size:20px; background-color: #004B58;">로그인</button>
			      </td>
			  </tr>

			  <tr align="center" height="45">
			      <td>
			       		<a href="<%=cp%>/">아이디찾기</a>&nbsp;&nbsp;&nbsp;
			       		<a href="<%=cp%>/">패스워드찾기</a>&nbsp;&nbsp;&nbsp;
			       		<a href="<%=cp%>/member/member.do">회원가입</a>
			      </td>
			  </tr>
			  
			  <tr align="center" height="40" >
			    	<td><span style="color: blue;">${message}</span></td>
			  </tr>
			  
			  </table>
			</form>           
		</div>

	</div>

<div class="footer" style="position: relative; top:300px;">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>