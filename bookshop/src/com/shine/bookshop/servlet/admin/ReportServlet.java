package com.shine.bookshop.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.bookshop.bean.Book;
import com.shine.bookshop.dao.BookDao;
import com.shine.bookshop.dao.OrderDao;
import com.shine.bookshop.dao.UserDao;
import com.shine.bookshop.dao.impl.BookDaoImpl;
import com.shine.bookshop.dao.impl.OrderDaoImpl;
import com.shine.bookshop.dao.impl.UserDaoImpl;
import com.shine.bookshop.util.DbUtil;

@WebServlet("/jsp/admin/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUMMARY_PATH = "report/summary.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao bookDao = new BookDaoImpl();
		UserDao userDao = new UserDaoImpl();
		OrderDao orderDao = new OrderDaoImpl();
		request.setAttribute("bookCount", bookDao.bookReadCount());
		request.setAttribute("userCount", userDao.bookReadCount());
		request.setAttribute("orderCount", orderDao.orderReadCount());
		request.setAttribute("pendingOrderCount", orderDao.orderReadCountByStatus(1));
		request.setAttribute("shipOrderCount", orderDao.orderReadCountByStatus(3));
		request.setAttribute("stockCount", bookDao.stockCount());
		request.setAttribute("totalAmount", getSum("select ifnull(sum(money),0) as total from s_order"));
		request.setAttribute("dealAmount", getSum("select ifnull(sum(money),0) as total from s_order where orderStatus in (2,3)"));
		List<Book> lowStockBooks = bookDao.lowStockBooks(5);
		request.setAttribute("lowStockBooks", lowStockBooks);
		request.getRequestDispatcher(SUMMARY_PATH).forward(request, response);
	}

	private double getSum(String sql) {
		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		if (list.size() == 0) {
			return 0;
		}
		Object total = list.get(0).get("total");
		return total instanceof Number ? ((Number) total).doubleValue() : 0;
	}
}
