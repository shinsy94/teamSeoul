package com.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

@WebServlet("/member/*")
public class MemberServlet extends HttpServlet {
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
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		} else if(uri.indexOf("member.do")!=-1) {
			memberForm(req, resp);
		} else if(uri.indexOf("member_ok.do")!=-1) {
			memberSubmit(req, resp);
		} else if(uri.indexOf("pwd.do")!=-1) {
			pwdForm(req, resp);
		} else if(uri.indexOf("pwd_ok.do")!=-1) {
			pwdSubmit(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("userIdCheck.do")!=-1) {
			userIdCheck(req, resp);
		} else if(uri.indexOf("mypage.do")!=-1) {
			mypage(req, resp);
		}
	}

	protected void forward(HttpServletRequest req, 	HttpServletResponse resp, String path)
			throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리
		// 세션객체. 세션 정보는 서버에 저장(로그인정보, 권한등)
		HttpSession session = req.getSession();
		
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");

		MemberDTO dto = dao.readMember(userId);
		if(userId != null) {
			if(dto.getUserPwd().equals(userPwd)) {
				// 로그인 성공
				// 세션의 유지 시간을 20분으로 설정 ( 기본 : 30)
				session.setMaxInactiveInterval(20*60);
				
				// 세션에 로그인 정보를 저장
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				info.setNickName(dto.getNickname());
				
				// 세션에 member라는 이름으로 SessionInfo 객체를 저장
				session.setAttribute("member", info);
				
				// 메인 화면으로 리다이렉트
				resp.sendRedirect(cp);
				return;
			}
		}
		
		// 로그인이 실패한 경우
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);
		
		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		// 세션에 저장된 정보 지우기
		session.removeAttribute("member");
		
		// 세션의 모든 내용을 지우기 세션을 초기화
		session.invalidate(); // 위에꺼 없어도 문제 안생김
		
		// 메인화면으로 리다이렉트
		resp.sendRedirect(cp);
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "created");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		
	}
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao=new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		dto.setUserBirth(req.getParameter("userBirth"));
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		if (email1 != null && email1.length() != 0 && email2 != null
				&& email2.length() != 0) {
			dto.setUserEmail(email1 + "@" + email2);
		}
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		if (tel1 != null && tel1.length() != 0 && tel2 != null
				&& tel2.length() != 0 && tel3 != null && tel3.length() != 0) {
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);
		}

		try {
			dao.insertMember(dto);
		} catch (Exception e) {
			String message = "회원 가입이 실패 했습니다.";

			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}

		String cp=req.getContextPath();
		resp.sendRedirect(cp);
		
	}
	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인이 안된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String mode = req.getParameter("mode");
		if(mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);
		
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
		
	}
	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인이 안된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.readMember(info.getUserId());
		
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		
		String userPwd = req.getParameter("userPwd");
		String mode=req.getParameter("mode");
		if(! dto.getUserPwd().equals(userPwd)) {
			if(mode.equals("update")) {
				req.setAttribute("title", "회원 정보 수정");
			} else {
				req.setAttribute("title", "회원 탈퇴");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message", "<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");
			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		}
		
		if(mode.equals("delete")) {
			// 회원 탈퇴
			
		} else if(mode.equals("update")) {
			// 회원 정보 수정 폼
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
		}
		
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAO();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { 
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		MemberDTO dto = new MemberDTO();

		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		dto.setUserBirth(req.getParameter("userBirth"));
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		if (email1 != null && email1.length() != 0 && email2 != null
				&& email2.length() != 0) {
			dto.setUserEmail(email1 + "@" + email2);
		}
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		if (tel1 != null && tel1.length() != 0 && tel2 != null
				&& tel2.length() != 0 && tel3 != null && tel3.length() != 0) {
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		
		try {
			dao.updateMember(dto);
		} catch (Exception e) {
		}
		
		resp.sendRedirect(cp);
		
	}
	
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.readMember(userId);
		
		String passed = "false";
		if(dto == null)
			passed = "true";
		
		// {"키":"값"} , {"키1":"값1", "키2":"값2"}
		JSONObject job = new JSONObject();
		job.put("passed", passed);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		
		out.print(job.toString());
		
	}
	
	private void mypage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path="/WEB-INF/views/member/mypage.jsp";
		forward(req, resp, path);
		
	}
	
}
