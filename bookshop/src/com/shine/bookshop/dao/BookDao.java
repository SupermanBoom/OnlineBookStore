package com.shine.bookshop.dao;

import java.util.List;

import com.shine.bookshop.bean.Book;
import com.shine.bookshop.bean.PageBean;

public interface BookDao {
	long bookReadCount();

	long bookReadCount(String bookname);

	List<Book> bookList(PageBean pageBean, String bookname);

	List<Book> bookList(PageBean pageBean);

	long bookReadCount(int catalogId);

	List<Book> bookList(PageBean pageBean, int catalogId);

	boolean bookAdd(Book book);

	Book findBookById(int bookId);

	boolean findBookByBookName(String bookName);

	boolean bookUpdate(Book book);

	boolean bookDelById(int bookId);

	String findimgIdByIds(String ids);

	boolean bookBatDelById(String ids);

	List<Book> bookList(int num);

	List<Book> newBooks(int num);

	boolean updateStock(int bookId, int stock);

	boolean decreaseStock(int bookId, int quantity);

	long stockCount();

	List<Book> lowStockBooks(int threshold);
}
