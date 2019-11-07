<%@page import="com.views.ViewsDAO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page  contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

	request.setCharacterEncoding("UTF-8");

	String bigareaCode=request.getParameter("bigareaCode");
	ViewsDAO dao=new ViewsDAO();
	Map<String,String> map=dao.ListAreaCode(bigareaCode);
	
	StringBuilder sb=new StringBuilder();
	
	sb.append("<span id='areaCode'><select name='areaCode'>");
	for(String key : map.keySet()){
		sb.append("<option value='"+key+"'>"+map.get(key)+"</option>");
	}
	sb.append("</select></span>");
	
	String areaCode=sb.toString();
%>
<%=areaCode%>
