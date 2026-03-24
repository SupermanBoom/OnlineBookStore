package com.shine.bookshop.dao;

import com.shine.bookshop.bean.SiteInfo;

public interface SiteInfoDao {
	SiteInfo getSiteInfo();

	boolean saveSiteInfo(SiteInfo siteInfo);
}
