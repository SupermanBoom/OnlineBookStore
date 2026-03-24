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
<title>公告管理</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<style>
	body{background:#eee;padding:20px;}
	.box{background:#fff;padding:24px;}
</style>
</head>
<body>
	<div class="box">
		<h2 class="text-center">公告管理</h2>
		<c:if test="${!empty noticeMessage}">
			<div class="alert alert-info">${noticeMessage}</div>
		</c:if>
		<form class="form-horizontal" action="jsp/admin/SiteManageServlet?action=addNotice" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">公告标题</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="title" placeholder="请输入公告标题">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">公告内容</label>
				<div class="col-sm-8">
					<textarea class="form-control" rows="4" name="content" placeholder="请输入公告内容"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-3">
					<button type="submit" class="btn btn-success btn-block">发布公告</button>
				</div>
			</div>
		</form>
		<hr>
		<table class="table table-striped">
			<tr>
				<th>标题</th>
				<th>内容</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${noticeList}" var="i">
				<tr>
					<td>${i.title}</td>
					<td>${i.content}</td>
					<td>
						<c:choose>
							<c:when test="${i.status eq 'y'}">已发布</c:when>
							<c:otherwise>已下架</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${i.status eq 'y'}">
								<a class="btn btn-xs btn-warning" href="jsp/admin/SiteManageServlet?action=noticeStatus&id=${i.noticeId}&status=n">下架</a>
							</c:when>
							<c:otherwise>
								<a class="btn btn-xs btn-info" href="jsp/admin/SiteManageServlet?action=noticeStatus&id=${i.noticeId}&status=y">重新发布</a>
							</c:otherwise>
						</c:choose>
						<a class="btn btn-xs btn-danger" href="jsp/admin/SiteManageServlet?action=delNotice&id=${i.noticeId}" onclick="return confirm('确定要删除这条公告吗？');">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
