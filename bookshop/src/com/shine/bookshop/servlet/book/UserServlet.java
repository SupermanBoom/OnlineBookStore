package com.shine.bookshop.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.bookshop.bean.User;
import com.shine.bookshop.dao.UserDao;
import com.shine.bookshop.dao.impl.UserDaoImpl;

import net.sf.json.JSONObject;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PATH = "jsp/book/reg.jsp?type=login";
	private static final String REG_PATH = "jsp/book/reg.jsp?type=reg";
	private static final String INDEX_PATH = "jsp/book/index.jsp";
	private static final String LANDING = "landing";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "login":
			login(request, response);
			break;
		case "off":
			offlogin(request, response);
			break;
		case "ajlogin":
			ajlogin(request, response);
			break;
		case "reg":
			reg(request, response);
			break;
		case "landstatus":
			landstatus(request, response);
			break;
		default:
			break;
		}
	}

	private void landstatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = (User) request.getSession().getAttribute(LANDING);
		PrintWriter pw = response.getWriter();
		JSONObject json = new JSONObject();
		json.put("status", user != null ? "y" : "n");
		pw.print(json.toString());
		pw.flush();
	}

	private void ajlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		User user = new User(userName, passWord);
		PrintWriter pw = response.getWriter();
		JSONObject json = new JSONObject();
		UserDao ud = new UserDaoImpl();
		User user2 = ud.userLogin(user);
		if (user2 != null) {
			if ("y".equals(user2.getEnabled())) {
				request.getSession().setAttribute(LANDING, user2);
				json.put("status", "y");
			} else {
				json.put("info", "该用户已被禁用，请联系管理员");
			}
		} else {
			json.put("info", "用户名或密码错误，请重新登录");
		}
		pw.print(json.toString());
	}

	private void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao ad = new UserDaoImpl();
		User user = new User(request.getParameter("userName"), request.getParameter("passWord"),
				request.getParameter("name"), request.getParameter("sex"),
				Integer.parseInt(request.getParameter("age")), request.getParameter("tell"),
				request.getParameter("address"));
		user.setEnabled("y");
		if (ad.findUser(user.getUserName())) {
			request.setAttribute("infoList", "注册失败，用户名已存在");
			request.getRequestDispatcher(REG_PATH).forward(request, response);
		} else {
			if (ad.userAdd(user)) {
				request.setAttribute("infoList", "注册成功，请登录");
				request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
			} else {
				request.setAttribute("userMessage", "用户注册失败");
				request.getRequestDispatcher(REG_PATH).forward(request, response);
			}
		}
	}

	private void offlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = (User) request.getSession().getAttribute(LANDING);
		if (user != null) {
			request.getSession().removeAttribute(LANDING);
		}
		response.sendRedirect(INDEX_PATH);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		User user = new User(userName, passWord);
		UserDao ud = new UserDaoImpl();
		User user2 = ud.userLogin(user);
		if (user2 != null) {
			if ("y".equals(user2.getEnabled())) {
				request.getSession().setAttribute(LANDING, user2);
				response.sendRedirect(INDEX_PATH);
			} else {
				request.setAttribute("infoList", "该用户已被禁用，请联系管理员");
				request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
			}
		} else {
			request.setAttribute("infoList", "用户名或密码错误，请重新登录");
			request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
		}
	}
}
