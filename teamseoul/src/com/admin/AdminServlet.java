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

import com.event.EventDTO;
import com.festival.FestivalDAO;
import com.festival.FestivalDTO;
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
		if(info==null) { // 嚥≪뮄�젃占쎌뵥占쎈┷筌욑옙 占쎈륫占쏙옙 野껋럩�뒭
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
		}else if(uri.indexOf("deleteFile.do")!=-1) {
			deleteFiles(req,resp);
		}else if (uri.indexOf("eventupdate_ok.do")!=-1) {
			eventUpdateSubmit(req, resp);
		}else if (uri.indexOf("festivalupdate_ok.do")!=-1) {
			festivalUpdateSubmit(req, resp);
		}else if (uri.indexOf("noticeupdate_ok.do")!=-1) {
			noticeUpdateSubmit(req, resp);
		}else if (uri.indexOf("viewsupdate_ok.do")!=-1) {
			viewsUpdateSubmit(req, resp);
		}else if (uri.indexOf("delete_ok.do")!=-1) {
			deleteBoard(req, resp);
		}
	
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");

		forward(req, resp, "/WEB-INF/views/admin/created.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		String table=req.getParameter("table");
		int num = Integer.parseInt(req.getParameter("num"));
		
		String some="";
		String body="";
		if(table.equals("views")) {
			
			ViewsDAO dao=new ViewsDAO();
			List<ViewsDTO> list = dao.readViews(num);
			
			List<String> img=new ArrayList<String>();
			
			AdminDTO dto=new AdminDTO();
			dto.setAreaCode(list.get(0).getAreaCode());
			dto.setContent(list.get(0).getContent());
			dto.setTitle(list.get(0).getTitle());
			dto.setUserId(list.get(0).getUserId());
			
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
			req.setAttribute("bigareaCode", list.get(0).getBigArea());
			req.setAttribute("some", some);
			req.setAttribute("body", body);
			
		}else if(table.equals("festival")) {
			FestivalDAO dao=new FestivalDAO();
			List<FestivalDTO> list = dao.readFestival(num);
			
			List<String> img=new ArrayList<String>();
			
			AdminDTO dto=new AdminDTO();
			dto.setSeasonCode(list.get(0).getSeasonCode());
			dto.setContent(list.get(0).getContent());
			dto.setTitle(list.get(0).getTitle());
			dto.setUserId(list.get(0).getUserId());
			
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
			req.setAttribute("seasonCode", list.get(0).getSeasonCode());
			req.setAttribute("some", some);
			req.setAttribute("body", body);
			
		}else if(table.equals("event")) {
			
			AdminDAO dao=new AdminDAO();
			List<EventDTO> list = dao.readEvent(num);
			
			List<String> img=new ArrayList<String>();
			
			AdminDTO dto=new AdminDTO();
			dto.setEventLink(list.get(0).getEventLink());
			dto.setContent(list.get(0).getContent());
			dto.setTitle(list.get(0).getTitle());
			dto.setUserId(list.get(0).getUserId());
			
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getImageName().contains("some")) {
					
					some=list.get(i).getImageName();
					
				}else if(list.get(i).getImageName().contains("body")) {
					
					body=list.get(i).getImageName();
					
				}else {
					
					img.add(list.get(i).getImageName());
				
				}
			}
			dto.setImageFileName(img);
			req.setAttribute("dto", dto);
			req.setAttribute("some", some);
			req.setAttribute("body", body);
			
		}else {
			
			
		}
		
		req.setAttribute("table", table);
		req.setAttribute("num",num);
		forward(req, resp, "/WEB-INF/views/admin/update.jsp");
	}
	
	protected void createdSub(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cp=req.getContextPath();
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
	
	String cp=req.getContextPath();
	String table=req.getParameter("table");
	AdminDAO dao=new AdminDAO();
	
	req.setAttribute("table", table);
	
	if(table.equals("views")) {
		
		Map<String,String> map=dao.ListAreaCode(req.getParameter("bigareaCode"));
		
		req.setAttribute("map", map);
		
	}else if(table.equals("festival")) {
		req.setAttribute("table", table);
		Map<String,String> map=dao.ListAreaCode(req.getParameter("seasonCode"));
		req.setAttribute("seasonCode",req.getParameter("seasonCode"));
		req.setAttribute("map", map);
		
	}
	

	forward(req,resp,"/WEB-INF/views/admin/update_sub.jsp");
		
	}
	
	protected void viewscreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"views";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // 占쎈쨨占쎈쐭揶쏉옙 鈺곕똻�삺占쎈릭筌욑옙 占쎈륫占쎌몵筌롳옙
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     占쎌뵠占쎈선占쎈튊 占쎈솁占쎌뵬占쎌뵠 占쎈씜嚥≪뮆諭� 揶쏉옙占쎈뮟占쎈릭�⑨옙 request�몴占� 占쎌뵠占쎌뒠占쎈릭占쎈연 
		//     占쎈솁占쎌뵬沃섎챸苑ｇ몴占� 占쎄퐜野껓옙 獄쏆룇�뱽 占쎈땾 占쎈씨占쎈뼄.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		if(mreq.getFile("someNail_upload")!=null&&mreq.getFile("body_upload")!=null) {
			
			
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
	
			// 占쏙옙占쎌삢
			dao.insertView(dto);
		
			resp.sendRedirect(cp+"/views/list.do");
		}
	}	

	protected void festivalcreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"festival";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // 占쎈쨨占쎈쐭揶쏉옙 鈺곕똻�삺占쎈릭筌욑옙 占쎈륫占쎌몵筌롳옙
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     占쎌뵠占쎈선占쎈튊 占쎈솁占쎌뵬占쎌뵠 占쎈씜嚥≪뮆諭� 揶쏉옙占쎈뮟占쎈릭�⑨옙 request�몴占� 占쎌뵠占쎌뒠占쎈릭占쎈연 
		//     占쎈솁占쎌뵬沃섎챸苑ｇ몴占� 占쎄퐜野껓옙 獄쏆룇�뱽 占쎈땾 占쎈씨占쎈뼄.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		if(mreq.getFile("someNail_upload")!=null&&mreq.getFile("body_upload")!=null) {
			
			
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
	
			// 占쏙옙占쎌삢
			dao.insertFestival(dto);
		
			resp.sendRedirect(cp+"/festival/list.do");
		}
	}
	protected void eventcreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"event";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // 占쎈쨨占쎈쐭揶쏉옙 鈺곕똻�삺占쎈릭筌욑옙 占쎈륫占쎌몵筌롳옙
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     占쎌뵠占쎈선占쎈튊 占쎈솁占쎌뵬占쎌뵠 占쎈씜嚥≪뮆諭� 揶쏉옙占쎈뮟占쎈릭�⑨옙 request�몴占� 占쎌뵠占쎌뒠占쎈릭占쎈연 
		//     占쎈솁占쎌뵬沃섎챸苑ｇ몴占� 占쎄퐜野껓옙 獄쏆룇�뱽 占쎈땾 占쎈씨占쎈뼄.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		if(mreq.getFile("someNail_upload")!=null&&mreq.getFile("body_upload")!=null) {
			
			
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
			resp.sendRedirect(cp+"/event/eventlist.do");
		
	}
	protected void noticecreatedSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"notice";
		
		File f=new File(pathname);
		
		if(! f.exists()) { // 占쎈쨨占쎈쐭揶쏉옙 鈺곕똻�삺占쎈릭筌욑옙 占쎈륫占쎌몵筌롳옙
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
		// <form enctype="multipart/form-data"....
		//     占쎌뵠占쎈선占쎈튊 占쎈솁占쎌뵬占쎌뵠 占쎈씜嚥≪뮆諭� 揶쏉옙占쎈뮟占쎈릭�⑨옙 request�몴占� 占쎌뵠占쎌뒠占쎈릭占쎈연 
		//     占쎈솁占쎌뵬沃섎챸苑ｇ몴占� 占쎄퐜野껓옙 獄쏆룇�뱽 占쎈땾 占쎈씨占쎈뼄.
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
	
			// 占쏙옙占쎌삢
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
		
				// 占쏙옙占쎌삢
				dao.insertNotice(dto);;
		
				resp.sendRedirect(cp+"/notice/list.do");
		
	}
	
	protected void deleteFiles(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		
		String table=req.getParameter("table");
		String imageFileName=req.getParameter("imageFileName");
		int num=Integer.parseInt(req.getParameter("num"));
		
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+table;
		
		FileManager.doFiledelete(pathname,imageFileName);
		
		AdminDAO dao=new AdminDAO();
		
		dao.deleteVEF(num, table, imageFileName);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out= resp.getWriter();
		
		out.print("삭제가 완료되었습니다.");
		
		
		
	}	
	
	protected void viewsUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String cp=req.getContextPath();
			HttpSession session=req.getSession();
			SessionInfo info=(SessionInfo)session.getAttribute("member");
	
			String root=session.getServletContext().getRealPath("/");
			
			pathname=root+File.separator+"uploads"+File.separator+"views";
			
			File f=new File(pathname);
			
			if(! f.exists()) { 
				f.mkdirs();
			}		
			
			AdminDAO dao=new AdminDAO();
			AdminDTO dto=new AdminDTO();
		
			
			String encType="utf-8";
			int maxSize=5*1024*1024;
			
			MultipartRequest mreq=new MultipartRequest(
					req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());
			
			String table=mreq.getParameter("table");
			
				List<String> files=new ArrayList<String>();
				Enumeration<?> e = mreq.getFileNames();
				while(e.hasMoreElements()) {
					String paramName = (String)e.nextElement();
					
					if(mreq.getFile(paramName) == null) {
						continue;
						}
					
					if(paramName.equals("someNail_upload")==true) {
						
						FileManager.doFiledelete(pathname, mreq.getParameter("orisome"));
						dao.deleteVEF(Integer.parseInt(mreq.getParameter("num")),table, mreq.getParameter("orisome"));
						
						String someNail_upload= FileManager.doFilerenameSomeNail(pathname,mreq.getFilesystemName(paramName));
						
						files.add(someNail_upload);
						
					}else if(paramName.equals("body_upload")==true) {
					
						FileManager.doFiledelete(pathname,mreq.getParameter("oribody"));
						dao.deleteVEF(Integer.parseInt(mreq.getParameter("num")),table, mreq.getParameter("oribody"));
						
						String body_upload=FileManager.doFilerenameBody(pathname, mreq.getFilesystemName(paramName));
						
						files.add(body_upload);
					
					}else {
						
						String fileNames=FileManager.doFilerename(pathname,mreq.getFilesystemName(paramName));
						
						files.add(fileNames);
					
					}
				
				}	
				dto.setImageFileName(files);
				dto.setNum(Integer.parseInt(mreq.getParameter("num")));
				dto.setUserId(info.getUserId());
				dto.setTitle(mreq.getParameter("title"));
				dto.setContent(mreq.getParameter("content"));
				dto.setAreaCode(Integer.parseInt(mreq.getParameter("areaCode")));
				// 占쏙옙占쎌삢
				dao.updateViews(dto);
			

				resp.sendRedirect(cp+"/views/list.do");
	
	}
	
	protected void festivalUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"festival";
		
		File f=new File(pathname);
		
		if(! f.exists()) { 
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
	
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		String table=mreq.getParameter("table");
		
			List<String> files=new ArrayList<String>();
			Enumeration<?> e = mreq.getFileNames();
			while(e.hasMoreElements()) {
				String paramName = (String)e.nextElement();
				
				if(mreq.getFile(paramName) == null) {
					continue;
					}
				
				if(paramName.equals("someNail_upload")==true) {
					
					FileManager.doFiledelete(pathname, mreq.getParameter("orisome"));
					dao.deleteVEF(Integer.parseInt(mreq.getParameter("num")),table, mreq.getParameter("orisome"));
					
					String someNail_upload= FileManager.doFilerenameSomeNail(pathname,mreq.getFilesystemName(paramName));
					
					files.add(someNail_upload);
					
				}else if(paramName.equals("body_upload")==true) {
				
					FileManager.doFiledelete(pathname,mreq.getParameter("oribody"));
				
					dao.deleteVEF(Integer.parseInt(mreq.getParameter("num")),table, mreq.getParameter("oribody"));
					
					String body_upload=FileManager.doFilerenameBody(pathname, mreq.getFilesystemName(paramName));
					
					files.add(body_upload);
				
				}else {
					
					String fileNames=FileManager.doFilerename(pathname,mreq.getFilesystemName(paramName));
					
					files.add(fileNames);
				
				}
			
			}	
			dto.setImageFileName(files);
			dto.setNum(Integer.parseInt(mreq.getParameter("num")));
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
			dto.setAreaCode(Integer.parseInt(mreq.getParameter("season")));
			// 占쏙옙占쎌삢
			dao.updateFestival(dto);

			resp.sendRedirect(cp+"/festival/list.do");
	}
	
	protected void eventUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"festival";
		
		File f=new File(pathname);
		
		if(! f.exists()) { 
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		
	
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=new MultipartRequest(
				req, pathname, maxSize, encType,
				new DefaultFileRenamePolicy());
		
		String table=mreq.getParameter("table");	
	
			List<String> files=new ArrayList<String>();
			Enumeration<?> e = mreq.getFileNames();
			while(e.hasMoreElements()) {
				String paramName = (String)e.nextElement();
				
				if(mreq.getFile(paramName) == null) {
					continue;
					}
				
				if(paramName.equals("someNail_upload")==true) {
					
					FileManager.doFiledelete(pathname, mreq.getParameter("orisome"));
					dao.deleteVEF(Integer.parseInt(mreq.getParameter("num")),table, mreq.getParameter("orisome"));
					
					String someNail_upload= FileManager.doFilerenameSomeNail(pathname,mreq.getFilesystemName(paramName));
					
					files.add(someNail_upload);
					
				}else if(paramName.equals("body_upload")==true) {
				
					FileManager.doFiledelete(pathname,mreq.getParameter("oribody"));
				
					dao.deleteVEF(Integer.parseInt(mreq.getParameter("num")),table, mreq.getParameter("oribody"));
					
					String body_upload=FileManager.doFilerenameBody(pathname, mreq.getFilesystemName(paramName));
					
					files.add(body_upload);
				
				}else {
					
					String fileNames=FileManager.doFilerename(pathname,mreq.getFilesystemName(paramName));
					
					files.add(fileNames);
				
				}
			
			}	
			dto.setImageFileName(files);
			dto.setNum(Integer.parseInt(mreq.getParameter("num")));
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
			dto.setEventLink(mreq.getParameter("eventLink"));
			// 占쏙옙占쎌삢
			dao.updateEvent(dto);


			resp.sendRedirect(cp+"/event/eventlist.do");
	}
	
	protected void noticeUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		pathname=root+File.separator+"uploads"+File.separator+"notice";
		
		File f=new File(pathname);
		
		if(! f.exists()) { 
			f.mkdirs();
		}		
		
		AdminDAO dao=new AdminDAO();
		AdminDTO dto=new AdminDTO();
		
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
	
			MultipartRequest mreq=new MultipartRequest(
					req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());
						
					
			if(mreq.getFile("notice_upload")!=null) { 
				
				FileManager.doFiledelete(pathname, req.getParameter("saveFileName"));
				
				dto.setSaveFileName(mreq.getFilesystemName("notice_upload"));
				
				dto.setOriginalFileName(mreq.getOriginalFileName("notice_upload"));
				
				dto.setFilesize(mreq.getFile("notice_upload").length());
					
				}	
			
			dto.setUserId(info.getUserId());
			dto.setTitle(mreq.getParameter("title"));
			dto.setContent(mreq.getParameter("content"));
		
			dao.updateNotice(dto);
		
			resp.sendRedirect(cp+"/notice/list.do");
	}
	
	protected void deleteBoard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		//占쎈솁占쎌뵬 占쏙옙占쎌삢占쎈막 野껋럥以�
		String root=session.getServletContext().getRealPath("/");
		
		String table=req.getParameter("table");
		
		pathname=root+File.separator+"uploads"+File.separator+table;
		
		
		int num=Integer.parseInt(req.getParameter("num"));
		
		AdminDAO dao= new AdminDAO();
		
		if(!table.equals("notice")) {
			List<String> list=dao.readFiles(table, num);
			for(String img:list) {
				FileManager.doFiledelete(pathname, img);
			}
			
			dao.deleteBoards(table, num);
			
			if(table.equals("event")) {
				resp.sendRedirect(cp+"/"+table+"/eventlist.do");
			}else {
				resp.sendRedirect(cp+"/"+table+"/list.do");
			}
		}else {
			FileManager.doFiledelete(pathname, req.getParameter("saveFileName"));
			dao.deleteBoards(table, num);
			resp.sendRedirect(cp+"/notice/list.do");
		}
		
		

		
	}
	
	


}
