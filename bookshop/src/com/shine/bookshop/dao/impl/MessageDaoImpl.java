package com.shine.bookshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shine.bookshop.bean.Message;
import com.shine.bookshop.dao.MessageDao;
import com.shine.bookshop.util.DateUtil;
import com.shine.bookshop.util.DbUtil;

public class MessageDaoImpl implements MessageDao {

	@Override
	public List<Message> listMessage() {
		List<Message> messages = new ArrayList<>();
		String sql = "select * from s_message order by createTime desc";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		for (Map<String, Object> map : list) {
			messages.add(new Message(map));
		}
		return messages;
	}

	@Override
	public List<Message> listFrontMessage(int limit) {
		List<Message> messages = new ArrayList<>();
		String sql = "select * from s_message where status='y' order by createTime desc limit ?";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql, limit);
		for (Map<String, Object> map : list) {
			messages.add(new Message(map));
		}
		return messages;
	}

	@Override
	public boolean addMessage(Message message) {
		String sql = "insert into s_message(userName,content,status,createTime) values(?,?,?,?)";
		return DbUtil.excuteUpdate(sql, message.getUserName(), message.getContent(), "y", DateUtil.getTimestamp()) > 0;
	}

	@Override
	public boolean replyMessage(int messageId, String replyContent) {
		String sql = "update s_message set replyContent=?,replyTime=?,status='y' where messageId=?";
		return DbUtil.excuteUpdate(sql, replyContent, DateUtil.getTimestamp(), messageId) > 0;
	}

	@Override
	public boolean deleteMessage(int messageId) {
		String sql = "delete from s_message where messageId=?";
		return DbUtil.excuteUpdate(sql, messageId) > 0;
	}
}
