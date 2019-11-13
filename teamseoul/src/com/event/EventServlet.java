package com.event;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUtil;

@WebServlet("/event/*")
public class EventServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		if(uri.indexOf("eventlist.do")!=-1) {
			eventList(req, resp);
		} else if(uri.indexOf("eventarticle.do")!=-1) {
			eventarticle(req, resp);
		} 
	}

	protected void forward(HttpServletRequest req, 	HttpServletResponse resp, String path)
			throws ServletException, IOException {
		// �������� ���� �޼ҵ�
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	private void eventList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		EventDAO dao=new EventDAO();
		MyUtil util=new MyUtil();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		
		int dataCount=dao.dataCount();
		
		int rows=4;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page)
			current_page=total_page;
		
		int offset=(current_page-1)*rows;

		List<EventDTO> list=dao.listEvent(offset, rows);
		
		// ����¡ ó��
		String eventlistUrl=cp+"/event/eventlist.do";
		String eventarticleUrl = cp + "/event/eventarticle.do?page="+current_page;
		String paging=util.paging(current_page, total_page, eventlistUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("eventarticleUrl", eventarticleUrl);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("total_page", total_page);
		
		forward(req, resp, "/WEB-INF/views/event/eventlist.jsp");
	}

	private void eventarticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �Խù� ����
		String cp=req.getContextPath();
		
		EventDAO dao=new EventDAO();
		
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		EventDTO dto=dao.readEvent(num);
		if(dto == null) {
			resp.sendRedirect(cp+"/event/eventlist.do?page="+page);
			return;
		}
		
		dto.setContent(dto.getContent().replace("\n", "<br>"));
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/event/eventarticle.jsp");
	}
	
}
