<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>网站维护</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<style>
	body{background:#eee;padding:20px;}
	.box{background:#fff;padding:24px;}
</style>
</head>
<body>
	<div class="box">
		<h2 class="text-center">网站简介与联系信息维护</h2>
		<c:if test="${!empty siteMessage}">
			<div class="alert alert-info">${siteMessage}</div>
		</c:if>
		<form class="form-horizontal" action="jsp/admin/SiteManageServlet?action=saveInfo" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">书店名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="storeName" value="${siteInfo.storeName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">书店简介</label>
				<div class="col-sm-8">
					<textarea class="form-control" rows="6" name="introduction">${siteInfo.introduction}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">联系方式</label>
				<div class="col-sm-8">
					<textarea class="form-control" rows="3" name="contactInfo">${siteInfo.contactInfo}</textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-3">
					<button type="submit" class="btn btn-success btn-block">保存信息</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
