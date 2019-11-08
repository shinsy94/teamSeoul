package com.yolo;


import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.util.MyUtil;

@WebServlet("/yolo/*")
public class YoloServlet extends HttpServlet {
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
	
	private SessionInfo loginUser(HttpServletRequest req) throws ServletException {
		SessionInfo info = null;
		HttpSession session=req.getSession();
		info = (SessionInfo)session.getAttribute("member");
		return info;
	}
	
	private String getFilePathname(HttpServletRequest req) throws ServletException {
		String s=null;
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/");
		s = root+"uploads"+File.separator+"notice";
		return s;
	}
	
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		}
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		YoloDAO dao=new YoloDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		int rows = 10;
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page !=null) {
			current_page = Integer.parseInt(page);
		}
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="title";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "UTF-8");
		}
		int dataCount;
		if(keyword.length()==0)
			dataCount = dao.dataCount();
		else
			dataCount = dao.dataCount(condition, keyword);
		
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page)
			current_page = total_page;
		
		int offset = (current_page-1)*rows;
		if(offset<0) offset = 0;
		
		List<YoloDTO> list;
		if(keyword.length()==0)
			list = dao.listYolo(offset, rows);
		else
			list = dao.listYolo(offset, rows, condition, keyword);
		
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int listNum, n=0;
		for(YoloDTO dto : list) {
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum);
			try {
					Date date=sdf.parse(dto.getCreated());
					
				}catch (Exception e){
				}
				dto.setCreated(dto.getCreated().substring(0, 10));
				n++;
		}
		
		
	      List<YoloDTO> listAttention = null;
	      if(current_page == 1) {
	    	  listAttention = dao.listAttention();
	         for(YoloDTO dto : listAttention) {
	            dto.setCreated(dto.getCreated().substring(0,10));
	         }
	      }
		
		String query = "rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="
					+URLEncoder.encode(keyword, "UTF-8");
		}
		String listUrl = cp+"/yolo/list.do?"+query;
		String articleUrl = cp+"/yolo/article.do?page="+current_page+"&"+query;
				
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("listAttention", listAttention);
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		
		
		
		forward(req, resp, "/WEB-INF/views/yolo/yolo.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/yolo/list.do");
			return;
		}
		
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/yolo/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/yolo/list.do");
			return;
		}
		
		String pathname=getFilePathname(req);
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		String encType="UTF-8";
		int maxSize = 5 * 1024 * 1024;
		
		MultipartRequest mreq;
		
		mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());
		
		YoloDAO dao = new YoloDAO();
		YoloDTO dto = new YoloDTO();
		
		dto.setUserId(info.getUserId());
		if(mreq.getParameter("attention")!=null) {
			dto.setAttention(Integer.parseInt(mreq.getParameter("attention")));
		}
		
		dto.setTitle(mreq.getParameter("title"));
		dto.setContent(mreq.getParameter("content"));
		
		if(mreq.getFile("upload")!=null) {
			dto.setImageFileName(mreq.getFilesystemName("upload"));
		}
		dao.insertyolo(dto);
		
		resp.sendRedirect(cp+"/yolo/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SessionInfo info = loginUser(req);
				
		YoloDAO dao=new YoloDAO();
		String cp=req.getContextPath();
				
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
				
		int num=Integer.parseInt(req.getParameter("num"));
		String page=req.getParameter("page");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="title";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");

		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
				
		
		dao.updateHitCount(num);
		
		
		YoloDTO dto=dao.readYolo(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/yolo/list.do?"+query);
			return;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		
		YoloDTO preReadDto = dao.preReadYolo(dto.getNum(), condition, keyword);
		YoloDTO nextReadDto = dao.nextReadYolo(dto.getNum(), condition, keyword);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/yolo/article.jsp");
	}

	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));
		
		YoloDAO dao=new YoloDAO();
		YoloDTO dto=dao.readYolo(num);
		if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/yolo/list.do?page"+page);
			return;
		}
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/yolo/created.jsp");
	}
	
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		String encType="UTF-8";
		int maxfilesize = 5 * 1024 * 1024;
		
		YoloDAO dao = new YoloDAO();
		YoloDTO dto = new YoloDTO();
		
		String pathname = getFilePathname(req);
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		MultipartRequest mreq;
		
		mreq = new MultipartRequest(req, pathname, maxfilesize, encType, new DefaultFileRenamePolicy());
		
		dto.setUserId(info.getUserId());
		if(mreq.getParameter("attention")!=null) {
			dto.setAttention(Integer.parseInt(mreq.getParameter("attention")));
		}
		
		dto.setTitle(mreq.getParameter("title"));
		dto.setNum(Integer.parseInt(mreq.getParameter("num")));
		dto.setUserId(info.getUserId());
		dto.setContent(mreq.getParameter("content"));
		dto.setImageFileName(mreq.getParameter("imageFileName"));
		
		if(mreq.getFile("upload")!=null) {		
			if(dto.getImageFileName().length()!=0) {
					
			FileManager.doFiledelete(pathname, dto.getImageFileName());
			}
			dto.setImageFileName(mreq.getFilesystemName("upload"));
		}
		
		String page=mreq.getParameter("page");
		dao.updateYolo(dto);
		resp.sendRedirect(cp+"/yolo/list.do?page="+page);
	}
		
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
			
		}
		String page=req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="title";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		String pathname = getFilePathname(req);
		YoloDAO dao=new YoloDAO();
		YoloDTO dto=dao.readYolo(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/yolo/list.do?"+query);
			return;
		}
		
		if(dto.getImageFileName()!=null) {
			FileManager.doFiledelete(pathname, dto.getImageFileName());
		}
		
		dao.deleteYolo(num, info.getUserId());
		
		resp.sendRedirect(cp+"/yolo/list.do?"+query);
	}
		
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		String pathname = getFilePathname(req);
		YoloDAO dao = new YoloDAO();
		YoloDTO dto = dao.readYolo(num);
		
		if(dto!=null && dto.getImageFileName()!=null) {
			FileManager.doFiledelete(pathname, dto.getImageFileName());
			dto.setImageFileName("");

			dao.updateYolo(dto);
		}
	
		resp.sendRedirect(cp+"/yolo/update.do?num="+num+"&page="+page);
	}

}
