package com.shine.bookshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shine.bookshop.bean.Notice;
import com.shine.bookshop.dao.NoticeDao;
import com.shine.bookshop.util.DateUtil;
import com.shine.bookshop.util.DbUtil;

public class NoticeDaoImpl implements NoticeDao {

	@Override
	public List<Notice> listNotice() {
		List<Notice> notices = new ArrayList<>();
		String sql = "select * from s_notice order by publishTime desc";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		for (Map<String, Object> map : list) {
			notices.add(new Notice(map));
		}
		return notices;
	}

	@Override
	public List<Notice> listPublishedNotice(int limit) {
		List<Notice> notices = new ArrayList<>();
		String sql = "select * from s_notice where status='y' order by publishTime desc limit ?";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql, limit);
		for (Map<String, Object> map : list) {
			notices.add(new Notice(map));
		}
		return notices;
	}

	@Override
	public boolean addNotice(Notice notice) {
		String sql = "insert into s_notice(title,content,status,publishTime) values(?,?,?,?)";
		return DbUtil.excuteUpdate(sql, notice.getTitle(), notice.getContent(), notice.getStatus(), DateUtil.getTimestamp()) > 0;
	}

	@Override
	public boolean updateStatus(int noticeId, String status) {
		String sql = "update s_notice set status=? where noticeId=?";
		return DbUtil.excuteUpdate(sql, status, noticeId) > 0;
	}

	@Override
	public boolean deleteNotice(int noticeId) {
		String sql = "delete from s_notice where noticeId=?";
		return DbUtil.excuteUpdate(sql, noticeId) > 0;
	}
}
