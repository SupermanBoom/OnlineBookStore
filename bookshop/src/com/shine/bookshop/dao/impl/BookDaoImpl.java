package com.shine.bookshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shine.bookshop.bean.Book;
import com.shine.bookshop.bean.PageBean;
import com.shine.bookshop.dao.BookDao;
import com.shine.bookshop.util.DateUtil;
import com.shine.bookshop.util.DbUtil;

public class BookDaoImpl implements BookDao {
	private static final String BOOK_BASE_SQL =
			"select s_catalog.catalogName,s_book.bookId,s_book.catalogId,s_book.bookName,s_book.price,"
			+ "s_book.description,s_book.imgId,s_book.stock,s_uploadimg.imgName,s_uploadimg.imgSrc,"
			+ "s_uploadimg.imgType,s_book.author,s_book.press,s_book.addTime "
			+ "from s_book join s_catalog on s_book.catalogId=s_catalog.catalogId "
			+ "join s_uploadimg on s_book.imgId=s_uploadimg.imgId";

	@Override
	public List<Book> bookList(PageBean pageBean) {
		List<Book> list = new ArrayList<>();
		String sql = BOOK_BASE_SQL + " limit ?,?";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
				pageBean.getMaxSize());
		for (Map<String, Object> map : lm) {
			list.add(new Book(map));
		}
		return list;
	}

	@Override
	public long bookReadCount() {
		String sql = "select count(*) as count from s_book";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql);
		return lm.size() > 0 ? (long) lm.get(0).get("count") : 0;
	}

	@Override
	public boolean bookAdd(Book book) {
		String sql = "insert into s_book(bookName,catalogId,author,press,price,description,imgId,stock,addTime) values(?,?,?,?,?,?,?,?,?)";
		int i = DbUtil.excuteUpdate(sql, book.getBookName(), book.getCatalog().getCatalogId(), book.getAuthor(),
				book.getPress(), book.getPrice(), book.getDescription(), book.getUpLoadImg().getImgId(), book.getStock(),
				DateUtil.getTimestamp());
		return i > 0;
	}

	@Override
	public Book findBookById(int bookId) {
		String sql = BOOK_BASE_SQL + " where s_book.bookId=?";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql, bookId);
		if (list.size() > 0) {
			return new Book(list.get(0));
		}
		return null;
	}

	@Override
	public boolean findBookByBookName(String bookName) {
		String sql = "select * from s_book where bookName=?";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql, bookName);
		return list.size() > 0;
	}

	@Override
	public boolean bookUpdate(Book book) {
		String sql = "update s_book set catalogId=?,author=?,press=?,price=?,description=?,stock=? where bookId=?";
		int i = DbUtil.excuteUpdate(sql, book.getCatalogId(), book.getAuthor(), book.getPress(), book.getPrice(),
				book.getDescription(), book.getStock(), book.getBookId());
		return i > 0;
	}

	@Override
	public boolean bookDelById(int bookId) {
		String sql = "SET FOREIGN_KEY_CHECKS = 0;delete from s_book where bookId=?";
		int i = DbUtil.excuteUpdate(sql, bookId);
		return i > 0;
	}

	@Override
	public String findimgIdByIds(String ids) {
		String imgIds = "";
		String sql = "select imgId from s_book where bookId in(" + ids + ")";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i != list.size() - 1) {
					imgIds += list.get(i).get("imgId") + ",";
				} else {
					imgIds += list.get(i).get("imgId");
				}
			}
		}
		return imgIds;
	}

	@Override
	public boolean bookBatDelById(String ids) {
		String sql = "delete from s_book where bookId in(" + ids + ")";
		int i = DbUtil.excuteUpdate(sql);
		return i > 0;
	}

	@Override
	public List<Book> bookList(int num) {
		List<Book> list = new ArrayList<>();
		String sql = BOOK_BASE_SQL + " order by rand() LIMIT ?";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, num);
		for (Map<String, Object> map : lm) {
			list.add(new Book(map));
		}
		return list;
	}

	@Override
	public List<Book> newBooks(int num) {
		List<Book> list = new ArrayList<>();
		String sql = BOOK_BASE_SQL + " ORDER BY s_book.addTime desc limit 0,?";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, num);
		for (Map<String, Object> map : lm) {
			list.add(new Book(map));
		}
		return list;
	}

	@Override
	public long bookReadCount(int catalogId) {
		String sql = "select count(*) as count from s_book where catalogId=?";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, catalogId);
		return lm.size() > 0 ? (long) lm.get(0).get("count") : 0;
	}

	@Override
	public List<Book> bookList(PageBean pageBean, int catalogId) {
		List<Book> list = new ArrayList<>();
		String sql = BOOK_BASE_SQL + " where s_book.catalogId=? limit ?,?";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, catalogId,
				(pageBean.getCurPage() - 1) * pageBean.getMaxSize(), pageBean.getMaxSize());
		for (Map<String, Object> map : lm) {
			list.add(new Book(map));
		}
		return list;
	}

	@Override
	public List<Book> bookList(PageBean pageBean, String bookname) {
		List<Book> list = new ArrayList<>();
		String sql = BOOK_BASE_SQL + " where s_book.bookName like '%" + bookname + "%' limit ?,?";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
				pageBean.getMaxSize());
		for (Map<String, Object> map : lm) {
			list.add(new Book(map));
		}
		return list;
	}

	@Override
	public long bookReadCount(String bookname) {
		String sql = "select count(*) as count from s_book where bookName like '%" + bookname + "%'";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql);
		return lm.size() > 0 ? (long) lm.get(0).get("count") : 0;
	}

	@Override
	public boolean updateStock(int bookId, int stock) {
		String sql = "update s_book set stock=? where bookId=?";
		return DbUtil.excuteUpdate(sql, stock, bookId) > 0;
	}

	@Override
	public boolean decreaseStock(int bookId, int quantity) {
		String sql = "update s_book set stock=stock-? where bookId=? and stock>=?";
		return DbUtil.excuteUpdate(sql, quantity, bookId, quantity) > 0;
	}

	@Override
	public long stockCount() {
		String sql = "select ifnull(sum(stock),0) as count from s_book";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql);
		if (lm.size() == 0) {
			return 0;
		}
		Object count = lm.get(0).get("count");
		return count instanceof Number ? ((Number) count).longValue() : 0;
	}

	@Override
	public List<Book> lowStockBooks(int threshold) {
		List<Book> list = new ArrayList<>();
		String sql = BOOK_BASE_SQL + " where s_book.stock<=? order by s_book.stock asc, s_book.addTime desc";
		List<Map<String, Object>> lm = DbUtil.executeQuery(sql, threshold);
		for (Map<String, Object> map : lm) {
			list.add(new Book(map));
		}
		return list;
	}
}
