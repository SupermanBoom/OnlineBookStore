package com.shine.bookshop.servlet.book;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.bookshop.bean.Book;
import com.shine.bookshop.bean.Cart;
import com.shine.bookshop.bean.CartItem;
import com.shine.bookshop.dao.BookDao;
import com.shine.bookshop.dao.impl.BookDaoImpl;

import net.sf.json.JSONObject;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "add":
			addTOCart(request, response);
			break;
		case "changeIn":
			changeIn(request, response);
			break;
		case "delItem":
			delItem(request, response);
			break;
		case "delAll":
			delAll(request, response);
			break;
		default:
			break;
		}
	}

	private void delAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("shopCart");
		response.sendRedirect("jsp/book/cart.jsp");
	}

	private void delItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");
		if (shopCart != null && shopCart.getMap().containsKey(bookId)) {
			shopCart.getMap().remove(bookId);
		}
		response.sendRedirect("jsp/book/cart.jsp");
	}

	private void changeIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");
		JSONObject json = new JSONObject();
		if (shopCart == null) {
			json.put("status", "n");
			json.put("info", "购物车为空");
			response.getWriter().print(json.toString());
			return;
		}
		CartItem item = shopCart.getMap().get(bookId);
		if (item == null) {
			json.put("status", "n");
			json.put("info", "购物车商品不存在");
			response.getWriter().print(json.toString());
			return;
		}
		Book book = new BookDaoImpl().findBookById(bookId);
		if (book == null) {
			json.put("status", "n");
			json.put("info", "图书不存在");
			response.getWriter().print(json.toString());
			return;
		}
		if (quantity < 1) {
			quantity = 1;
		}
		if (book.getStock() <= 0) {
			shopCart.getMap().remove(bookId);
			json.put("status", "n");
			json.put("info", "当前图书已经售罄");
			json.put("totPrice", shopCart.getTotPrice());
			json.put("totQuan", shopCart.getTotQuan());
			response.getWriter().print(json.toString());
			return;
		}
		if (quantity > book.getStock()) {
			quantity = book.getStock();
			json.put("info", "库存不足，已自动调整数量");
		}
		item.setBook(book);
		item.setQuantity(quantity);
		json.put("status", "y");
		json.put("subtotal", item.getSubtotal());
		json.put("totPrice", shopCart.getTotPrice());
		json.put("totQuan", shopCart.getTotQuan());
		json.put("quantity", item.getQuantity());
		response.getWriter().print(json.toString());
	}

	private void addTOCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookId = request.getParameter("bookId");
		BookDao bd = new BookDaoImpl();
		Book book = bd.findBookById(Integer.parseInt(bookId));
		JSONObject json = new JSONObject();
		if (book == null) {
			json.put("status", "n");
			json.put("info", "图书不存在");
			response.getWriter().print(json.toString());
			return;
		}
		if (book.getStock() <= 0) {
			json.put("status", "n");
			json.put("info", "当前图书库存不足");
			response.getWriter().print(json.toString());
			return;
		}

		Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");
		if (shopCart == null) {
			shopCart = new Cart();
			request.getSession().setAttribute("shopCart", shopCart);
		}
		CartItem item = shopCart.getMap().get(book.getBookId());
		if (item != null && item.getQuantity() >= book.getStock()) {
			json.put("status", "n");
			json.put("info", "已达到当前库存上限");
			json.put("totQuan", shopCart.getTotQuan());
			response.getWriter().print(json.toString());
			return;
		}
		shopCart.addBook(book);
		json.put("status", "y");
		json.put("totQuan", shopCart.getTotQuan());
		json.put("info", "加入购物车成功");
		response.getWriter().print(json.toString());
	}
}
