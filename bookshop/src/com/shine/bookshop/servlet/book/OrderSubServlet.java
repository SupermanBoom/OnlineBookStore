package com.shine.bookshop.servlet.book;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shine.bookshop.bean.Book;
import com.shine.bookshop.bean.Cart;
import com.shine.bookshop.bean.CartItem;
import com.shine.bookshop.bean.Order;
import com.shine.bookshop.bean.OrderItem;
import com.shine.bookshop.bean.PageBean;
import com.shine.bookshop.bean.User;
import com.shine.bookshop.dao.BookDao;
import com.shine.bookshop.dao.OrderDao;
import com.shine.bookshop.dao.OrderItemDao;
import com.shine.bookshop.dao.impl.BookDaoImpl;
import com.shine.bookshop.dao.impl.OrderDaoImpl;
import com.shine.bookshop.dao.impl.OrderItemDaoImpl;
import com.shine.bookshop.util.DateUtil;
import com.shine.bookshop.util.RanUtil;

@WebServlet(name = "OrderServlet", urlPatterns = { "/OrderServlet" })
public class OrderSubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_LIST_SIZE = 5;
	private static final String CART_PATH = "jsp/book/cart.jsp";
	private static final String ORDER_PAY_PATH = "jsp/book/ordersuccess.jsp";
	private final OrderDao orderDao;
	private final OrderItemDao orderItemDao;
	private final BookDao bookDao;

	public OrderSubServlet() {
		this(new OrderDaoImpl(), new OrderItemDaoImpl(), new BookDaoImpl());
	}

	OrderSubServlet(OrderDao orderDao, OrderItemDao orderItemDao, BookDao bookDao) {
		this.orderDao = orderDao;
		this.orderItemDao = orderItemDao;
		this.bookDao = bookDao;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "subOrder":
			subOrder(request, response);
			break;
		case "list":
			myOrderList(request, response);
			break;
		case "ship":
			orderShip(request, response);
			break;
		default:
			break;
		}
	}

	private void orderShip(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String orderId = request.getParameter("id");
		if (orderDao.orderStatus(Integer.parseInt(orderId), 3)) {
			request.setAttribute("orderMessage", "订单操作成功");
		} else {
			request.setAttribute("orderMessage", "订单操作失败");
		}
		myOrderList(request, response);
	}

	private void myOrderList(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute("landing");
		if (user == null) {
			response.sendRedirect("jsp/book/reg.jsp?type=login");
		} else {
			int curPage = 1;
			String page = request.getParameter("page");
			if (page != null) {
				curPage = Integer.parseInt(page);
			}
			PageBean pb = new PageBean(curPage, MAX_LIST_SIZE, orderDao.orderReadCount(user.getUserId()));
			List<Order> orderList = orderDao.orderList(pb, user.getUserId());
			for (Order order : orderList) {
				order.setoItem(orderItemDao.findItemByOrderId(order.getOrderId()));
				for (OrderItem oi : order.getoItem()) {
					oi.setBook(bookDao.findBookById(oi.getBookId()));
				}
			}
			request.setAttribute("pageBean", pb);
			request.setAttribute("orderList", orderList);
			request.getRequestDispatcher("jsp/book/myorderlist.jsp").forward(request, response);
		}
	}

	private void subOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("shopCart");
		User user = (User) session.getAttribute("landing");
		if (cart == null || cart.getMap().isEmpty()) {
			request.setAttribute("suberr", "购物车为空，请先选购图书");
			request.getRequestDispatcher(CART_PATH).forward(request, response);
			return;
		}
		if (user == null) {
			response.sendRedirect("jsp/book/reg.jsp?type=login");
			return;
		}
		String orderNum = RanUtil.getOrderNum();
		String orderDate = DateUtil.show();
		Order order = new Order();

		for (Map.Entry<Integer, CartItem> meic : cart.getMap().entrySet()) {
			Book book = bookDao.findBookById(meic.getKey());
			if (book == null) {
				request.setAttribute("suberr", "购物车中存在已下架图书，请刷新后重试");
				request.getRequestDispatcher(CART_PATH).forward(request, response);
				return;
			}
			if (book.getStock() < meic.getValue().getQuantity()) {
				request.setAttribute("suberr", "图书《" + book.getBookName() + "》库存不足，请调整数量后再下单");
				request.getRequestDispatcher(CART_PATH).forward(request, response);
				return;
			}
		}

		order.setOrderNum(orderNum);
		order.setOrderDate(orderDate);
		order.setMoney(cart.getTotPrice());
		order.setOrderStatus(1);
		order.setUserId(user.getUserId());

		if (orderDao.orderAdd(order)) {
			order.setOrderId(orderDao.findOrderIdByOrderNum(orderNum));
			for (Map.Entry<Integer, CartItem> meic : cart.getMap().entrySet()) {
				OrderItem oi = new OrderItem();
				oi.setBookId(meic.getKey());
				oi.setQuantity(meic.getValue().getQuantity());
				oi.setOrderId(order.getOrderId());
				orderItemDao.orderAdd(oi);
				bookDao.decreaseStock(meic.getKey(), meic.getValue().getQuantity());
			}
			session.removeAttribute("shopCart");
			request.setAttribute("orderNum", order.getOrderNum());
			request.setAttribute("money", order.getMoney());
			request.getRequestDispatcher(ORDER_PAY_PATH).forward(request, response);
		} else {
			request.setAttribute("suberr", "订单提交失败，请重新提交");
			request.getRequestDispatcher(CART_PATH).forward(request, response);
		}
	}
}
