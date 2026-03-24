ALTER TABLE s_book ADD COLUMN stock int NOT NULL DEFAULT 20 AFTER imgId;

UPDATE s_book SET stock = 20 WHERE stock IS NULL;

CREATE TABLE IF NOT EXISTS s_siteinfo (
  id int NOT NULL,
  storeName varchar(100) NOT NULL,
  introduction text,
  contactInfo varchar(255),
  updateTime datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO s_siteinfo (id, storeName, introduction, contactInfo, updateTime)
SELECT 1, '网上书店系统', '欢迎来到网上书店，这里提供图书浏览、会员注册登录、购物车下单以及留言反馈等功能。', '服务热线：400-000-0000', NOW()
WHERE NOT EXISTS (SELECT 1 FROM s_siteinfo WHERE id = 1);

CREATE TABLE IF NOT EXISTS s_notice (
  noticeId int NOT NULL AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  content text,
  status varchar(1) NOT NULL DEFAULT 'y',
  publishTime datetime DEFAULT NULL,
  PRIMARY KEY (noticeId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS s_message (
  messageId int NOT NULL AUTO_INCREMENT,
  userName varchar(50) NOT NULL,
  content text NOT NULL,
  replyContent text,
  status varchar(1) NOT NULL DEFAULT 'y',
  createTime datetime DEFAULT NULL,
  replyTime datetime DEFAULT NULL,
  PRIMARY KEY (messageId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
