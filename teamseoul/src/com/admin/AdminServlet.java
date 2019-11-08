package com.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet{
	private String pathname;
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	

	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		//파일 저장할 경로
		String root=session.getServletContext().getRealPath("/");
		pathname=root+File.separator+"uploads"+File.separator+"views";
		File f=new File(pathname);
		if(! f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}
		if (uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		}else if (uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		}else if (uri.indexOf("created_sub.do")!=-1) {
			createdSub(req, resp);
		}
		/* else if (uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if (uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		}  else if (uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}  else if (uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		}  
		
		
		*/
	}
	
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");

		forward(req, resp, "/WEB-INF/views/admin/created.jsp");
	}
	
	protected void createdSub(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String bigareaCode=req.getParameter("bigareaCode");
		AdminDAO dao=new AdminDAO();
		Map<String,String> map=dao.ListAreaCode(bigareaCode);
	
		StringBuilder sb=new StringBuilder();
		
		sb.append("<select id='areaCode' name='areaCode'>");
		for(String key : map.keySet()){
			sb.append("<option value='"+key+"'>"+map.get(key)+"</option>");
		}
		sb.append("</select>");
		
		String areaCode=sb.toString();
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out= resp.getWriter();
		
		out.print(areaCode); 

		
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     이어야 파일이 업로드 가능하고 request를 이용하여 
		//     파라미터를 넘겨 받을 수 없다.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		
			dto.setUserId("admin");
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
			dto.setAreaCode(Integer.parseInt(mreq.getParameter("areaCode")));
			String files[]=new String[] {"킹갓제네럴엠페러","충무공신장팀승연"};
			dto.setImageFileName(files);
	
			// 저장
			dao.insertView(dto);
		
		resp.sendRedirect(cp);
	}
		
	/*
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	//	SessionInfo info = loginUser(req);
		
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
	}

	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
	}
	
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
	  }
	*/
	}
