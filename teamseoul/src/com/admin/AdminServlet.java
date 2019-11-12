package com.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
import com.util.FileManager;
import com.views.ViewsDAO;
import com.views.ViewsDTO;


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
		if(info==null) { // 濡쒓렇�씤�릺吏� �븡�� 寃쎌슦
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if (uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		}else if (uri.indexOf("viewscreated_ok.do")!=-1) {
			viewscreatedSubmit(req, resp);
		}else if (uri.indexOf("created_sub.do")!=-1) {
			createdSub(req, resp);
		}else if (uri.indexOf("eventcreated_ok.do")!=-1) {
			eventcreatedSubmit(req, resp);
		}else if (uri.indexOf("festivalcreated_ok.do")!=-1) {
			festivalcreatedSubmit(req, resp);
		}else if (uri.indexOf("noticecreated_ok.do")!=-1) {
			noticecreatedSubmit(req, resp);
		}else if(uri.indexOf("updateForm.do")!=-1) {
			updateForm(req,resp);
		}else if(uri.indexOf("update_sub.do")!=-1) {
			updateSub(req,resp);
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
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String table=req.getParameter("table");
		int num = Integer.parseInt(req.getParameter("num"));
		if(table.equals("views")) {
			
			ViewsDAO dao=new ViewsDAO();
			System.out.println(num);
			List<ViewsDTO> list = dao.readViews(num);
			
			List<String> img=new ArrayList<String>();
			
			System.out.println(list.size());
			AdminDTO dto=new AdminDTO();
			dto.setAreaCode(list.get(0).getAreaCode());
			dto.setContent(list.get(0).getContent());
			dto.setTitle(list.get(0).getTitle());
			dto.setUserId(list.get(0).getUserId());
			
			String some="";
			String body="";
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getImageFileName().contains("some")) {
					
					some=list.get(i).getImageFileName();
					
				}else if(list.get(i).getImageFileName().contains("body")) {
					
					body=list.get(i).getImageFileName();
					
				}else {
					
					img.add(list.get(i).getImageFileName());
				
				}
			}
			dto.setImageFileName(img);
			req.setAttribute("dto", dto);
			req.setAttribute("some", some);
			req.setAttribute("body", body);
			req.setAttribute("bigareaCode", list.get(0).getBigArea());
			req.setAttribute("table", table);
			req.setAttribute("num",num);
		}else if(table.equals("festival")) {
			
		}
		
		forward(req, resp, "/WEB-INF/views/admin/update.jsp");
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
	
protected void updateSub(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String table=req.getParameter("table");
	AdminDAO dao=new AdminDAO();
	if(table.equals("views")) {
		
		Map<String,String> map=dao.ListAreaCode(req.getParameter("bigareaCode"));
		
		req.setAttribute("table", table);
	
		req.setAttribute("bigareaCode", req.getParameter("bigareaCode"));
	
		req.setAttribute("areaCode",req.getParameter("areaCode"));
		
		req.setAttribute("map", map);
		
	}else if(table.equals("festival")) {
		req.setAttribute("table", table);
		req.setAttribute("seasonCode",req.getParameter("seasonCode"));
	}
	

	forward(req,resp,"/WEB-INF/views/admin/update_sub.jsp");
		
	}
	
	protected void viewscreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//�뙆�씪 ���옣�븷 寃쎈줈
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"views";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // �뤃�뜑媛� 議댁옱�븯吏� �븡�쑝硫�
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     �씠�뼱�빞 �뙆�씪�씠 �뾽濡쒕뱶 媛��뒫�븯怨� request瑜� �씠�슜�븯�뿬 
		//     �뙆�씪誘명꽣瑜� �꽆寃� 諛쏆쓣 �닔 �뾾�떎.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		if(mreq.getFile("someNail_upload")!=null||mreq.getFile("body_upload")!=null) {
			
			
			List<String> files=new ArrayList<String>();
			Enumeration<?> e = mreq.getFileNames();
			while(e.hasMoreElements()) {
				String paramName = (String)e.nextElement();
				
				if(mreq.getFile(paramName) == null) {
					continue;
					}
				
				if(paramName.equals("someNail_upload")==true) {
					
					String someNail_upload= FileManager.doFilerenameSomeNail(pathname,mreq.getFilesystemName(paramName));
					
					files.add(someNail_upload);
					
				}else if(paramName.equals("body_upload")==true) {
				
					String body_upload=FileManager.doFilerenameBody(pathname, mreq.getFilesystemName(paramName));
					
					files.add(body_upload);
				
				}else {
					
					String fileNames=FileManager.doFilerename(pathname,mreq.getFilesystemName(paramName));
					
					files.add(fileNames);
				
				}
			
			}	
			dto.setImageFileName(files);
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
			dto.setAreaCode(Integer.parseInt(mreq.getParameter("areaCode")));
	
			// ���옣
			dao.insertView(dto);
		
		resp.sendRedirect(cp);
		}
	}	

	protected void festivalcreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//�뙆�씪 ���옣�븷 寃쎈줈
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"festival";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // �뤃�뜑媛� 議댁옱�븯吏� �븡�쑝硫�
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     �씠�뼱�빞 �뙆�씪�씠 �뾽濡쒕뱶 媛��뒫�븯怨� request瑜� �씠�슜�븯�뿬 
		//     �뙆�씪誘명꽣瑜� �꽆寃� 諛쏆쓣 �닔 �뾾�떎.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		if(mreq.getFile("someNail_upload")!=null||mreq.getFile("body_upload")!=null) {
			
			
			List<String> files=new ArrayList<String>();
			Enumeration<?> e = mreq.getFileNames();
			while(e.hasMoreElements()) {
				String paramName = (String)e.nextElement();
				
				if(mreq.getFile(paramName) == null) {
					continue;
					}
				
				if(paramName.equals("someNail_upload")==true) {
					
					String someNail_upload= FileManager.doFilerenameSomeNail(pathname,mreq.getFilesystemName(paramName));
					
					files.add(someNail_upload);
					
				}else if(paramName.equals("body_upload")==true) {
				
					String body_upload=FileManager.doFilerenameBody(pathname, mreq.getFilesystemName(paramName));
					
					files.add(body_upload);
				
				}else {
					
					String fileNames=FileManager.doFilerename(pathname,mreq.getFilesystemName(paramName));
					
					files.add(fileNames);
				
				}
			
			}	
			dto.setImageFileName(files);
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
			dto.setSeasonCode(Integer.parseInt(mreq.getParameter("season")));
	
			// ���옣
			dao.insertFestival(dto);
		
		resp.sendRedirect(cp);
		}
	}
	protected void eventcreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//�뙆�씪 ���옣�븷 寃쎈줈
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"event";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // �뤃�뜑媛� 議댁옱�븯吏� �븡�쑝硫�
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     �씠�뼱�빞 �뙆�씪�씠 �뾽濡쒕뱶 媛��뒫�븯怨� request瑜� �씠�슜�븯�뿬 
		//     �뙆�씪誘명꽣瑜� �꽆寃� 諛쏆쓣 �닔 �뾾�떎.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		if(mreq.getFile("someNail_upload")!=null||mreq.getFile("body_upload")!=null) {
			
			
			List<String> files=new ArrayList<String>();
			Enumeration<?> e = mreq.getFileNames();
			while(e.hasMoreElements()) {
				String paramName = (String)e.nextElement();
				
				if(mreq.getFile(paramName) == null) {
					continue;
					}
				
				if(paramName.equals("someNail_upload")==true) {
					
					String someNail_upload= FileManager.doFilerenameSomeNail(pathname,mreq.getFilesystemName(paramName));
					
					files.add(someNail_upload);
					
				}else if(paramName.equals("body_upload")==true) {
				
					String body_upload=FileManager.doFilerenameBody(pathname, mreq.getFilesystemName(paramName));
					
					files.add(body_upload);
				
				}else {
					
					String fileNames=FileManager.doFilerename(pathname,mreq.getFilesystemName(paramName));
					
					files.add(fileNames);
				
				}
			
			}	
			dto.setImageFileName(files);
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
			dto.setEventLink(mreq.getParameter("eventLink"));
			
			dao.insertEvent(dto);
		}
		resp.sendRedirect(cp);
		
	}
	protected void noticecreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//�뙆�씪 ���옣�븷 寃쎈줈
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"notice";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // �뤃�뜑媛� 議댁옱�븯吏� �븡�쑝硫�
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     �씠�뼱�빞 �뙆�씪�씠 �뾽濡쒕뱶 媛��뒫�븯怨� request瑜� �씠�슜�븯�뿬 
		//     �뙆�씪誘명꽣瑜� �꽆寃� 諛쏆쓣 �닔 �뾾�떎.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
	
			// ���옣
			MultipartRequest mreq=new MultipartRequest(
					req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());
						
					
			if(mreq.getFile("notice_upload")!=null) { 
				dto.setSaveFileName(mreq.getFilesystemName("notice_upload"));
				dto.setOriginalFileName(mreq.getOriginalFileName("notice_upload"));
				dto.setFilesize(mreq.getFile("notice_upload").length());
					
				}	
			
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
		
				// ���옣
				dao.insertNotice(dto);;
		
		resp.sendRedirect(cp);
		
	}
	

	/*

	
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
