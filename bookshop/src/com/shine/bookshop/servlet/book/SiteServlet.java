package com.shine.bookshop.servlet.book;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.bookshop.bean.Message;
import com.shine.bookshop.bean.User;
import com.shine.bookshop.dao.MessageDao;
import com.shine.bookshop.dao.impl.MessageDaoImpl;

import net.sf.json.JSONObject;

@WebServlet("/SiteServlet")
public class SiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		String action = request.getParameter("action");
		if ("addMessage".equals(action)) {
			addMessage(request, response);
		}
	}

	private void addMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		String userName = request.getParameter("userName");
		String content = request.getParameter("content");
		User user = (User) request.getSession().getAttribute("landing");
		if ((userName == null || userName.trim().length() == 0) && user != null) {
			userName = user.getName();
		}
		if (userName == null || userName.trim().length() == 0) {
			json.put("status", "n");
			json.put("info", "请输入留言人姓名");
			response.getWriter().print(json.toString());
			return;
		}
		if (content == null || content.trim().length() == 0) {
			json.put("status", "n");
			json.put("info", "留言内容不能为空");
			response.getWriter().print(json.toString());
			return;
		}
		Message message = new Message();
		message.setUserName(userName.trim());
		message.setContent(content.trim());
		MessageDao dao = new MessageDaoImpl();
		boolean success = dao.addMessage(message);
		json.put("status", success ? "y" : "n");
		json.put("info", success ? "留言提交成功" : "留言提交失败");
		response.getWriter().print(json.toString());
	}
}
