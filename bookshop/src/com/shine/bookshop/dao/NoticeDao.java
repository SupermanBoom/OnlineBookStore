package com.shine.bookshop.dao;

import java.util.List;

import com.shine.bookshop.bean.Notice;

public interface NoticeDao {
	List<Notice> listNotice();

	List<Notice> listPublishedNotice(int limit);

	boolean addNotice(Notice notice);

	boolean updateStatus(int noticeId, String status);

	boolean deleteNotice(int noticeId);
}
