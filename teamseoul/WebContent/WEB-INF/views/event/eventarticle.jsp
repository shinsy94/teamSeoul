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
<div class="body-title" style="width: 60%; margin: 10px auto; text-align: center;">
	<h3 style= "width: 60%; text-align: center; margin: 10px; font-size: x-large;">${dto.title}</h3>
	
<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
     	<tr style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
        	<td width="40%" align="right" style="padding-right: 10px; border-spacing: 0px; border-collapse: collapse;">
			      ${dto.created}
			</td>
		</tr>
	</table>
    </div>	
	    <div style="width: 80%; margin: 10px auto 0px;">
	    	<a href="https://korean.visitkorea.or.kr/detail/event_detail.do?cotid=30f1fbac-3b7c-4bff-8c0a-53abbe0c3892">
	    		<img src="<%=cp%>/uploads/event/${dto.imageFileName}" width="70%" style="margin: 0px 10px;">
	    	</a>
	    <div class="note-top">
    		<br>
				e스포츠의 진수를 느껴보고 싶다면?<br>
				전 세계 e스포츠 마니아들을 위해 인기 게임과 유명 프로게이머를 직접 만날 수 있는 특별한 경험을 제공합니다. e스포츠의 생생한 역사를 전시한 e스포츠 명예의 전당을 견학하고,
				국내 최대 규모 e스포츠 상설 경기장에서 펼쳐지는 프로게이머들의 박진감 넘치는 e스포츠 경기를 직접 관람할 수 있습니다.<br><br>
				○ 신청대상 : 서울을 방문하는 외국인 관광객이라면 누구나 가능<br>
				○ 기간 : 2019년 4월~12월 월 2회 (경기 일정에 따라 유동적)<br>
				- 세부 일정은 다소 변동될 수 있으며, 상세 일정은 신청 페이지에서 확인 가능<br>
				○ 장소 : 에스플렉스센터(서울특별시 마포구 매봉산로 31)<br>
				지하철 6호선·경의중앙선·공항철도 디지털미디어시티역 9번 출구에서 도보 13분<br>
				○ 주요내용<br>
				OGN e스포츠 스타디움 및 e스포츠 명예의 전당 견학(40분)+ e스포츠 경기 관람(120분~경기 종료시까지)<br>
				○ 정원 : 15명<br>
				○ 언어 : 한국어, 영어<br>
				○ 참가비 : 무료<br>
				○ 신청방법 : 아래 링크 접속 후 신청서 제출<br>
				☞ https://goo.gl/forms/bz3msBSmXwpNYcaS2 <br><br>
				○ e스포츠 스타디움 투어 참가 관련 주의사항<br>
				- 경기장 일부 구역은 관계자 외 입장이 엄격히 제한되므로, 반드시 가이드의 안내에 따라주시기 바랍니다.<br>
				- 모든 경기는 생방송 또는 녹화방송으로 중계되므로, 방송 촬영에 방해되는 어떠한 행동도 금합니다. 일부 경기의 경우 장내 사진 및 영상 촬영이 제한될 수 있습니다.<br>
				- 경기장 입장 시 음식물 반입을 금합니다.<br>
				- 온라인 게임 참여등급에 따라 일부 경기의 경우 미성년자의 출입이 제한될 수 있습니다.<br>
				- 당일 현장에서 입장권을 분실하는 경우 입장이 제한될 수 있습니다.<br><br>
				○ 프로그램 신청 및 안내<br>
				- e스포츠 경기 시즌에 따라 운영 일정이 다소 유동적으로 변경될 수 있습니다.<br>
				- 프로그램 신청은 운영일 2~4주 전부터 신청이 가능합니다.<br>
				- 참가 신청서 제출(1회)은 본인 포함 최대 2인 이내로 제한합니다.<br>
				- 프로그램 신청자가 15명을 넘을 경우, 추첨을 통해 참가자를 선정합니다.<br>
				- 참가자로 선정된 신청자에게는 프로그램 예정일 3~7일 전 확정 메일이 발송됩니다. 선정되지 않은 신청자에게는 별도로 메일이 발송되지 않습니다.<br>
				- 원활한 프로그램 운영을 위하여, 참가 확정 후 부득이하게 불참해야 할 경우 반드시 운영사무국(hallyuseoul@gmail.com)에 알려주시기 바랍니다.<br><br>
				○ 문의처 : 한류 체험프로그램 운영사무국(한국어, 영어)<br>
				- 운영시간 : 월요일~금요일 10:00~16:00(한국시간 기준, 공휴일은 휴무)<br>
				- 전화번호 : +82-2-790-3994 / E-mail : hallyuseoul@gmail.com.<br><br>
			</div>
			
			<div class="note-bottom">
				
			</div>

	   <table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px; ">
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="updatePhoto('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="deletePhoto('${dto.num}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/event/eventlist.do?page=${page}';">리스트</button>
			        
			    </td>
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