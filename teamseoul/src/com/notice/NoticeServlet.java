package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/notice/*")
public class NoticeServlet extends HttpServlet {
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
		}  else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		}  else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		} else if(uri.indexOf("download.do")!=-1) {
			download(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao=new NoticeDAO();
		MyUtil util=new MyUtil();		
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="title";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword,"utf-8");
		}
		
		int rows = 10; // 한페이지 표시할 데이터 개수
		int dataCount, total_page;
		
		if(keyword.length()!=0)
			dataCount= dao.dataCount(condition, keyword);
		else
			dataCount= dao.dataCount();
		total_page=util.pageCount(rows, dataCount);
		
		if(current_page>total_page)
			current_page=total_page;
		
		int offset=(current_page-1)*rows;
		
		List<NoticeDTO> list;
		if(keyword.length()!=0)
			list= dao.listNotice(offset, rows, condition, keyword);
		else
			list= dao.listNotice(offset, rows);
		
		// 공지글
		List<NoticeDTO> listNotice=null;
		listNotice = dao.listNotice();
		for(NoticeDTO dto : listNotice){
			dto.setCreated(dto.getCreated().substring(0, 10));
		}
		

		
		// 리스트 글번호 만들기
		int listNum, n=0;
		for(NoticeDTO dto : list){
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum);
			dto.setCreated(dto.getCreated().substring(0, 10));
			n++;
		}
		
		String query="";
		String listUrl;
		String articleUrl;
		
		listUrl=cp+"/notice/list.do";
		articleUrl=cp+"/notice/article.do?page=" +current_page;
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
			
			listUrl += "?"+query;
			articleUrl += "&"+query;
		}
		
		String paging=util.paging(current_page, total_page, listUrl);
		
		// 포워딩 jsp에 넘길 데이터
		req.setAttribute("list", list);
		req.setAttribute("listNotice", listNotice);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		if(! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/notice/list.do");
			return;
		}
		
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
	}
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		if(! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/notice/list.do");
			return;
		}
		
		String pathname = getFilePathname(req);
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		// 파라미터 넘겨 받아 디비에 저장
		// enctype="multipart/form-data" 인 경우는 request로 파라미터 받을 수 없다.
		String encType="utf-8";       // 파라미터의 encType
		int maxFilesize=5*1024*1024;  // 최대 업로드 크기
		MultipartRequest mreq;
		mreq=new MultipartRequest(req, pathname, maxFilesize, encType, 
				new DefaultFileRenamePolicy());
		
		NoticeDAO dao = new NoticeDAO();
	    NoticeDTO dto = new NoticeDTO();
	    
	    dto.setUserId(info.getUserId());
	    
	    dto.setTitle(mreq.getParameter("title"));
	    dto.setContent(mreq.getParameter("content"));
	    
	    if(mreq.getFile("upload")!=null) { // 첨부파일이 존재하면
	    	dto.setSaveFileName(mreq.getFilesystemName("upload"));
	    	dto.setOriginalFileName(mreq.getOriginalFileName("upload"));
	    	dto.setFileSize(mreq.getFile("upload").length());
	    }
	    dao.insertNotice(dto);
		
		resp.sendRedirect(cp+"/notice/list.do?");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		SessionInfo info = loginUser(req);
		
		NoticeDAO dao=new NoticeDAO();
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
		
		
		// 게시물 가져오기
		NoticeDTO dto=dao.readNotice(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do?"+query);
			return;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		// 이전글/다음글
		NoticeDTO preReadDto = dao.preReadNotice(dto.getNum(), condition, keyword);
		NoticeDTO nextReadDto = dao.nextReadNotice(dto.getNum(), condition, keyword);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/notice/article.jsp");
	}


	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int num=Integer.parseInt(req.getParameter("num"));
		
		NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=dao.readNotice(num);
		if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/notice/list.do?page="+page);
			return;
		}
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=new NoticeDTO();
		
		String pathname = getFilePathname(req);
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		String encType ="UTF-8";
		int maxFilesize=5*1024*1024;
		
		MultipartRequest mreq;
		mreq=new MultipartRequest(req, pathname, maxFilesize, encType, new DefaultFileRenamePolicy());
				
		dto.setUserId(info.getUserId());
		
		dto.setNum(Integer.parseInt(mreq.getParameter("num")));

		dto.setTitle(mreq.getParameter("title"));
		dto.setContent(mreq.getParameter("content"));
		dto.setSaveFileName(mreq.getParameter("saveFileName"));
		
		try {
			dao.updateNotice(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/notice/list.do");
		
		
		
		if(mreq.getFile("upload")!=null) { // 첨부파일이 존재하면
			if(dto.getSaveFileName().length()!=0) {
				// 기존 파일 지우기
				FileManager.doFiledelete(pathname, dto.getSaveFileName());
			}
			dto.setSaveFileName(mreq.getFilesystemName("upload"));
			dto.setOriginalFileName(mreq.getOriginalFileName("upload"));
			dto.setFileSize(mreq.getFile("upload").length());
		}
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
		keyword=URLDecoder.decode(keyword, "UTF-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		String pathname = getFilePathname(req);
		NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=dao.readNotice(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do?"+query);
			return;
		}
		
		if(dto.getSaveFileName()!=null) {
			FileManager.doFiledelete(pathname, dto.getSaveFileName());
		}
		
		dao.deleteNotice(num, info.getUserId());
		
		resp.sendRedirect(cp+"/notice/list.do?"+query);
	}
	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		NoticeDAO dao=new NoticeDAO();
		String pathname = getFilePathname(req);
		
		int num = Integer.parseInt(req.getParameter("num"));
		boolean b = false;
		NoticeDTO dto = dao.readNotice(num);
		
		if(dto!=null) {
			b=FileManager.doFiledownload(dto.getSaveFileName(), dto.getOriginalFileName(), pathname, resp);
		}
		
		if(! b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.print("<script>alert('파일 다운로드가 실패했습니다.');history.back();</script>");
		}
		
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
		// NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=new NoticeDTO();
		
		if(dto!=null && dto.getSaveFileName()!=null) {
			FileManager.doFiledelete(pathname, dto.getSaveFileName());
			dto.setSaveFileName("");
			dto.setOriginalFileName("");
			dto.setFileSize(0);
			// dao.updateNotice(dto);
		}
		
		resp.sendRedirect(cp+"/notice/update.do?num="+num+"&page="+page);
	}
	
}
