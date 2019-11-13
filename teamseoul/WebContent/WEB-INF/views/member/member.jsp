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
<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
function memberOk() {
	var f = document.memberForm;
	var str;
	
	str = f.userId.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요.");
		f.userId.focus();
		return;
	}
	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) {
		alert("아이디는 5~10자이며 첫글자는 영문자이어야 합니다.");
		f.userId.focus();
		return;
	}
	f.userId.value = str;
	
	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("비밀번호를 입력하세요.");
		f.userPwd.focus();
		return;
	}
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) {
		alert("비밀번호는 5-10자이고 하나 이상의 숫자나 특수문자를 가져야합니다.");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;
	
	if(str!= f.userPwdCheck.value) {
		alert("비밀번호가 불일치합니다.");
		f.userPwdCheck.focus();
		return;
	}
	
	str = f.userName.value;
	str = str.trim();
	if(!str) {
		alert("이름을 입력하세요.");
		f.userName.focus();
		return;
	}
	f.userName.value = str;
	
	str = f.userBirth.value;
	str = str.trim();
	if(!str) {
		alert("생년월일을 입력하세요[YYYY-MM-DD].");
		f.userBirth.focus();
		return;
	}
	
	str = f.tel1.value;
	str = str.trim();
	if(!str) {
		alert("전화번호를 입력하세요.");
		f.tel1.focus();
		return;
	}
	
	str = f.tel2.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel2.focus();
        return;
    }
	if(!/^(\d+)$/.test(str)) {
		alert("숫자만 가능합니다.");
		f.tel2.focus();
		return;
	}
	str = f.tel3.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel3.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
	
	str = f.email1.value;
	str = str.trim();
	if(!str) {
		alert("이메일을 입력하세요.");
		f.email1.focus();
		return;
	}
	
	str = f.email2.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }	
	
	var mode="${mode}";
	if(mode=="created") {
		f.action = "<%=cp%>/member/member_ok.do";
	} else if(mode=="update") {
		f.action = "<%=cp%>/member/update_ok.do";
	}
	
	f.submit();
}

function changeEmail() {
	var f = document.memberForm;
	
	var str = f.selectEmail.value;
	if(str!="direct") {
		f.email2.value=str;
		f.email2.readOnly = true;
		f.email1.focus();
	} 
	else {
		f.email2.value="";
		f.email2.readOnly = false;
		f.email1.focus();
	}
}

$(function(){
	$("form input[name=userId]").change(function(){
		var $id = $(this);
		var userId = $id.val().trim();
		
		
		if(!/^[a-z][a-z0-9_]{4,9}$/i.test(userId)) { 
			// alert("아이디는 5~10자이며 첫글자는 영문자이어야 합니다.");
			$(this).focus();
			return false;
		}
		
		var url="<%=cp%>/member/userIdCheck.do";
		var query="userId="+userId;
		
		$.ajax({
			type:"post",
			url:url,
			data:query,
			dataType:"json",
			success:function(data) {
				//console.log(data);
				var passed = data.passed;
				if(passed=="true") {
					var s = "<span style='color:blue;font-weight:bold;'>"+userId+"</span> 아이디는 사용 가능합니다.";
					$id.parent().next(".help-block").html(s);
				} else {
					var s = "<span style='color:red;font-weight:bold;'>"+userId+"</span> 아이디는 사용할 수 없습니다.";
					$id.parent().next(".help-block").html(s);
					$id.val("");
					$id.focus();
				}
			},
			error:function(e) {
				console.log(e.responseText);
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
	
	
<div class="container" style="position: relative; top: 155px; z-index: 1;" >
    <div class="body-container">
      <div class="body-title">	
    	<h3><span style="font-family: Webdings">2</span> ${title} </h3>
      </div>
      
      <div>
      	<form name="memberForm" method="post">
      	<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
      		<tr>
			    <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			          <label style="font-weight: 900;">아이디</label>
			    </td>
			    <td style="padding: 0 0 15px 15px;">
			      <p style="text-align: left; margin-top: 1px; margin-bottom: 5px;">
			          <input type="text" name="userId" id="userId" value="${dto.userId}"
                       style="width: 50%;"
                       ${mode=="update" ? "readonly='readonly' ":""}
                       maxlength="15" class="boxTF" placeholder="아이디">
			      </p>
			      <p class="help-block" style="text-align: left;">아이디는 5~10자 이내로 작성해주세요.</p>
			    </td>
			</tr>	
			
			<tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">비밀번호</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="text-align: left; margin-top: 1px; margin-bottom: 5px;">
			            <input type="password" name="userPwd" maxlength="15" class="boxTF"
			                       style="width:50%;" placeholder="비밀번호">
			        </p>
			        <p class="help-block" style="text-align: left;">비밀번호는 5~10자 이내, 숫자또는 특수문자를 포함해야합니다.</p>
			      </td>
			</tr>
			
			<tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">비밀번호 확인</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="text-align: left; margin-top: 1px; margin-bottom: 5px;">
			            <input type="password" name="userPwdCheck" maxlength="15" class="boxTF"
			                       style="width: 50%;" placeholder="비밀번호 확인">
			        </p>
			        <p class="help-block" style="text-align: left;">비밀번호를 한번 더 입력해주세요.</p>
			      </td>
			  </tr>
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">이름</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="text-align: left; margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="userName" value="${dto.userName}" maxlength="30" class="boxTF"
		                       style="width: 50%;"
		                      ${mode=="update" ? "readonly='readonly' ":""}
		                      placeholder="이름">
			        </p>
			      </td>
			  </tr>
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">생년월일</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="text-align: left; margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="userBirth" value="${dto.userBirth}" maxlength="10" 
			                       class="boxTF" style="width: 50%;" placeholder="생년월일">
			        </p>
			        <p class="help-block" style="text-align: left;">생년월일은 YYYY-MM-DD 양식으로 입력해주세요.</p>
			      </td>
			  </tr>
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">이메일</label>
			      </td>
			      <td style="text-align: left; padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <select name="selectEmail" onchange="changeEmail();" class="selectField">
			                <option value="">선 택</option>
			                <option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
			                <option value="daum.net" ${dto.email2=="daum.net" ? "selected='selected'" : ""}>다음</option>
			                <option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>구글</option>
			                <option value="hanmail.com" ${dto.email2=="hanmail.com" ? "selected='selected'" : ""}>한 메일</option>
			                <option value="direct">직접입력</option>
			            </select>
			            <input type="text" name="email1" value="${dto.email1}" size="13" maxlength="30"  class="boxTF">
			            @ 
			            <input type="text" name="email2" value="${dto.email2}" size="13"maxlength="30"  class="boxTF" readonly="readonly">
			        </p>
			      </td>
			  </tr>
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">전화번호</label>
			      </td>
			      <td style="text-align: left; padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <select class="selectField" id="tel1" name="tel1" >
			                <option value="">선 택</option>
			                <option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
			                <option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
			                <option value="016" ${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
			                <option value="017" ${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
			            </select>
			            -
			            <input type="text" name="tel2" value="${dto.tel2}" class="boxTF" maxlength="4">
			            -
			            <input type="text" name="tel3" value="${dto.tel3}" class="boxTF" maxlength="4">
			        </p>
			      </td>
			  </tr>
			  <c:if test="${mode=='created'}">
				  <tr>
				      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
				            <label style="font-weight: 900;">약관동의</label>
				      </td>
				      <td style="text-align: left; padding: 0 0 15px 15px;">
				        <p style="margin-top: 7px; margin-bottom: 5px;">
				             <label>
				                 <input id="agree" name="agree" type="checkbox"
				                      onchange="form.sendButton.disabled = !checked"> <a href="#">이용약관</a>에 동의합니다.
				             </label>
				        </p>
				      </td>
				  </tr>
			  </c:if>
			  			
			</table>
			
			<table style="width:100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			        <button type="button" name="sendButton" class="btn" onclick="memberOk();">${mode=="created"?"회원가입":"정보수정"}</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/';">${mode=="created"?"가입취소":"수정취소"}</button>
			      </td>
			    </tr>
			    <tr height="30">
			        <td align="center" style="color: blue;">${message}</td>
			    </tr>
		    	
			  </table>			  
      		</form>
      		
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