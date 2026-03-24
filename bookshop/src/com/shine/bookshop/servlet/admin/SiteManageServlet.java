package com.shine.bookshop.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.bookshop.bean.Notice;
import com.shine.bookshop.bean.SiteInfo;
import com.shine.bookshop.dao.MessageDao;
import com.shine.bookshop.dao.NoticeDao;
import com.shine.bookshop.dao.SiteInfoDao;
import com.shine.bookshop.dao.impl.MessageDaoImpl;
import com.shine.bookshop.dao.impl.NoticeDaoImpl;
import com.shine.bookshop.dao.impl.SiteInfoDaoImpl;

@WebServlet("/jsp/admin/SiteManageServlet")
public class SiteManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SITEINFO_PATH = "siteManage/siteInfo.jsp";
	private static final String NOTICE_PATH = "siteManage/noticeList.jsp";
	private static final String MESSAGE_PATH = "siteManage/messageList.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if (action == null) {
			action = "info";
		}
		switch (action) {
		case "info":
			siteInfo(request, response);
			break;
		case "saveInfo":
			saveInfo(request, response);
			break;
		case "noticeList":
			noticeList(request, response);
			break;
		case "addNotice":
			addNotice(request, response);
			break;
		case "noticeStatus":
			noticeStatus(request, response);
			break;
		case "delNotice":
			delNotice(request, response);
			break;
		case "messageList":
			messageList(request, response);
			break;
		case "replyMessage":
			replyMessage(request, response);
			break;
		case "delMessage":
			delMessage(request, response);
			break;
		default:
			siteInfo(request, response);
			break;
		}
	}

	private void siteInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SiteInfoDao dao = new SiteInfoDaoImpl();
		request.setAttribute("siteInfo", dao.getSiteInfo());
		request.getRequestDispatcher(SITEINFO_PATH).forward(request, response);
	}

	private void saveInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SiteInfoDao dao = new SiteInfoDaoImpl();
		SiteInfo siteInfo = new SiteInfo();
		siteInfo.setStoreName(trimValue(request.getParameter("storeName")));
		siteInfo.setIntroduction(trimValue(request.getParameter("introduction")));
		siteInfo.setContactInfo(trimValue(request.getParameter("contactInfo")));
		if (siteInfo.getStoreName().length() == 0) {
			request.setAttribute("siteMessage", "书店名称不能为空");
			siteInfo(request, response);
			return;
		}
		request.setAttribute("siteMessage", dao.saveSiteInfo(siteInfo) ? "网站信息保存成功" : "网站信息保存失败");
		siteInfo(request, response);
	}

	private void noticeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao dao = new NoticeDaoImpl();
		request.setAttribute("noticeList", dao.listNotice());
		request.getRequestDispatcher(NOTICE_PATH).forward(request, response);
	}

	private void addNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao dao = new NoticeDaoImpl();
		Notice notice = new Notice();
		notice.setTitle(trimValue(request.getParameter("title")));
		notice.setContent(trimValue(request.getParameter("content")));
		if (notice.getTitle().length() == 0 || notice.getContent().length() == 0) {
			request.setAttribute("noticeMessage", "公告标题和内容都不能为空");
			noticeList(request, response);
			return;
		}
		notice.setStatus("y");
		request.setAttribute("noticeMessage", dao.addNotice(notice) ? "公告发布成功" : "公告发布失败");
		noticeList(request, response);
	}

	private void noticeStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao dao = new NoticeDaoImpl();
		int noticeId = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		dao.updateStatus(noticeId, status);
		noticeList(request, response);
	}

	private void delNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao dao = new NoticeDaoImpl();
		int noticeId = Integer.parseInt(request.getParameter("id"));
		dao.deleteNotice(noticeId);
		noticeList(request, response);
	}

	private void messageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MessageDao dao = new MessageDaoImpl();
		request.setAttribute("messageList", dao.listMessage());
		request.getRequestDispatcher(MESSAGE_PATH).forward(request, response);
	}

	private void replyMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MessageDao dao = new MessageDaoImpl();
		int messageId = Integer.parseInt(request.getParameter("messageId"));
		String replyContent = trimValue(request.getParameter("replyContent"));
		if (replyContent.length() == 0) {
			request.setAttribute("messageTips", "回复内容不能为空");
			messageList(request, response);
			return;
		}
		request.setAttribute("messageTips", dao.replyMessage(messageId, replyContent) ? "留言回复成功" : "留言回复失败");
		messageList(request, response);
	}

	private void delMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MessageDao dao = new MessageDaoImpl();
		int messageId = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("messageTips", dao.deleteMessage(messageId) ? "留言删除成功" : "留言删除失败");
		messageList(request, response);
	}

	private String trimValue(String value) {
		return value == null ? "" : value.trim();
	}
}
