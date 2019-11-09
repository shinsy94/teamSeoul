package com.views;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUtil;

@WebServlet("/views/*")
public class ViewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		
		// uri에 따른 작업 구분
		if(uri.indexOf("views.do")!=-1) {
			views(req, resp);
		} else if(uri.indexOf("areaList.do")!=-1) {
			areaList(req, resp);
		} else if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		}
	}
	protected void forward(HttpServletRequest req, 	HttpServletResponse resp, String path)
			throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	private void list(HttpServletRequest req, 	HttpServletResponse resp) throws ServletException, IOException {
		ViewsDAO dao = new ViewsDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		int current_page = 1;
		String page = req.getParameter("page");
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		
		int rows = 10;
		int offset = (current_page-1)*rows;
		
		// String keyword = req.getParameter("page");
		String areaCode = req.getParameter("areaCode");
		List<ViewsDTO> list;
		int dataCount = 0;
		
		String list_url = cp+"/views/list.do";
		String article_url = cp+"/views/article.do?page="+current_page;
		
		if(areaCode == null) {
			list = dao.somenailList(offset, rows);
			dataCount = dao.dataCount();
		} else {
			list = dao.somenailList(offset, rows,Integer.parseInt(areaCode));
			dataCount = dao.dataCount(Integer.parseInt(areaCode));
			list_url += "?areaCode="+areaCode;
			article_url +="&areaCode="+areaCode;
		}
		
		int total_page = util.pageCount(rows, dataCount);
		
		String paging = util.paging(current_page, total_page, list_url);
		
		req.setAttribute("page", current_page);
		req.setAttribute("areaCode", areaCode);
		req.setAttribute("list", list);
		req.setAttribute("articleUrl", article_url);
		req.setAttribute("paging", paging);
		req.setAttribute("dataCount", dataCount);
		
		forward(req, resp, "/WEB-INF/views/views/list.jsp");
		
	}
	
	private void views(HttpServletRequest req, 	HttpServletResponse resp) throws ServletException, IOException {		
		ViewsDAO dao = new ViewsDAO();
		List<ViewsDTO> list= dao.areaList();
		req.setAttribute("bigAreaList", list);
		
		
		forward(req, resp, "/WEB-INF/views/views/views.jsp");
	}

	private void areaList(HttpServletRequest req, 	HttpServletResponse resp) throws ServletException, IOException {
		ViewsDAO dao = new ViewsDAO();
		Map<String, String> map= dao.ListAreaCode(req.getParameter("bigCode"));
		
		req.setAttribute("areaMap", map);
		
		forward(req, resp, "/WEB-INF/views/views/areaList.jsp");
	}
	
	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int num = Integer.parseInt(req.getParameter("num"));
		ViewsDAO dao = new ViewsDAO();
		List<ViewsDTO> list = dao.imageList(num);
		
		req.setAttribute("list", list);
		forward(req, resp, "/WEB-INF/views/views/article.jsp");
	}
}
