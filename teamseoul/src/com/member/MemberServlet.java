package com.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		

		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");

		if(userId != null) {
				// 로그인 성공
				// 세션의 유지 시간을 20분으로 설정 ( 기본 : 30)
				session.setMaxInactiveInterval(20*60);
				
				// 세션에 로그인 정보를 저장
				SessionInfo info = new SessionInfo();
				info.setUserId(userId);
				info.setTierName("서울대통령");
				info.setNickName("관리자");
				info.setUserName("관리자");
				// 세션에 member라는 이름으로 SessionInfo 객체를 저장
				session.setAttribute("member", info);
				
				// 메인 화면으로 리다이렉트
				resp.sendRedirect(cp);
				return;
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
}
