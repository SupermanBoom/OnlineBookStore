package com.shine.bookshop.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.bookshop.bean.Book;
import com.shine.bookshop.dao.BookDao;
import com.shine.bookshop.dao.MessageDao;
import com.shine.bookshop.dao.NoticeDao;
import com.shine.bookshop.dao.SiteInfoDao;
import com.shine.bookshop.dao.impl.BookDaoImpl;
import com.shine.bookshop.dao.impl.MessageDaoImpl;
import com.shine.bookshop.dao.impl.NoticeDaoImpl;
import com.shine.bookshop.dao.impl.SiteInfoDaoImpl;

import net.sf.json.JSONObject;

@WebServlet("/ShopIndex")
public class ShopIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		JSONObject json = new JSONObject();
		BookDao bd = new BookDaoImpl();
		List<Book> recBooks = bd.bookList(4);
		json.put("recBooks", recBooks);
		List<Book> newBooks = bd.newBooks(4);
		json.put("newBooks", newBooks);
		SiteInfoDao siteInfoDao = new SiteInfoDaoImpl();
		NoticeDao noticeDao = new NoticeDaoImpl();
		MessageDao messageDao = new MessageDaoImpl();
		json.put("siteInfo", siteInfoDao.getSiteInfo());
		json.put("noticeList", noticeDao.listPublishedNotice(5));
		json.put("messageList", messageDao.listFrontMessage(6));
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
	}
}
