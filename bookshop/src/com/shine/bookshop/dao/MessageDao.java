package com.shine.bookshop.dao;

import java.util.List;

import com.shine.bookshop.bean.Message;

public interface MessageDao {
	List<Message> listMessage();

	List<Message> listFrontMessage(int limit);

	boolean addMessage(Message message);

	boolean replyMessage(int messageId, String replyContent);

	boolean deleteMessage(int messageId);
}
