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
		
		// uri�� ���� �۾� ����
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
		// �������� ���� �޼ҵ�
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ��
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ó��
		// ���ǰ�ü. ���� ������ ������ ����(�α�������, ���ѵ�)
		HttpSession session = req.getSession();
		

		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");

		if(userId != null) {
				// �α��� ����
				// ������ ���� �ð��� 20������ ���� ( �⺻ : 30)
				session.setMaxInactiveInterval(20*60);
				
				// ���ǿ� �α��� ������ ����
				SessionInfo info = new SessionInfo();
				info.setUserId(userId);
				info.setTierName("��������");
				info.setNickName("������");
				info.setUserName("������");
				// ���ǿ� member��� �̸����� SessionInfo ��ü�� ����
				session.setAttribute("member", info);
				
				// ���� ȭ������ �����̷�Ʈ
				resp.sendRedirect(cp);
				return;
		}
		
		// �α����� ������ ���
		String msg = "���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.";
		req.setAttribute("message", msg);
		
		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α׾ƿ�
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		// ���ǿ� ����� ���� �����
		session.removeAttribute("member");
		
		// ������ ��� ������ ����� ������ �ʱ�ȭ
		session.invalidate(); // ������ ��� ���� �Ȼ���
		
		// ����ȭ������ �����̷�Ʈ
		resp.sendRedirect(cp);
	}
}
