package com.shine.bookshop.dao.impl;

import java.util.List;
import java.util.Map;

import com.shine.bookshop.bean.SiteInfo;
import com.shine.bookshop.dao.SiteInfoDao;
import com.shine.bookshop.util.DateUtil;
import com.shine.bookshop.util.DbUtil;

public class SiteInfoDaoImpl implements SiteInfoDao {

	@Override
	public SiteInfo getSiteInfo() {
		String sql = "select * from s_siteinfo order by id asc limit 1";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		if (list.size() > 0) {
			return new SiteInfo(list.get(0));
		}
		SiteInfo siteInfo = new SiteInfo();
		siteInfo.setStoreName("网上书店系统");
		siteInfo.setIntroduction("欢迎来到网上书店，这里提供图书浏览、会员注册登录、购物车下单以及留言反馈等功能。");
		siteInfo.setContactInfo("服务热线：400-000-0000");
		return siteInfo;
	}

	@Override
	public boolean saveSiteInfo(SiteInfo siteInfo) {
		String countSql = "select count(*) as count from s_siteinfo";
		List<Map<String, Object>> list = DbUtil.executeQuery(countSql);
		long count = 0;
		if (list.size() > 0 && list.get(0).get("count") instanceof Number) {
			count = ((Number) list.get(0).get("count")).longValue();
		}
		if (count > 0) {
			String sql = "update s_siteinfo set storeName=?,introduction=?,contactInfo=?,updateTime=? where id=1";
			return DbUtil.excuteUpdate(sql, siteInfo.getStoreName(), siteInfo.getIntroduction(), siteInfo.getContactInfo(),
					DateUtil.getTimestamp()) > 0;
		}
		String sql = "insert into s_siteinfo(id,storeName,introduction,contactInfo,updateTime) values(1,?,?,?,?)";
		return DbUtil.excuteUpdate(sql, siteInfo.getStoreName(), siteInfo.getIntroduction(), siteInfo.getContactInfo(),
				DateUtil.getTimestamp()) > 0;
	}
}
