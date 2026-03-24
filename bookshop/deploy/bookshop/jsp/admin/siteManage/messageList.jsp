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
<title>留言管理</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<style>
	body{background:#eee;padding:20px;}
	.box{background:#fff;padding:24px;}
	.reply-box{min-width:260px;}
</style>
</head>
<body>
	<div class="box">
		<h2 class="text-center">客户留言与回复</h2>
		<c:if test="${!empty messageTips}">
			<div class="alert alert-info">${messageTips}</div>
		</c:if>
		<table class="table table-striped">
			<tr>
				<th>留言人</th>
				<th>留言内容</th>
				<th>店主回复</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${messageList}" var="i">
				<tr>
					<td>${i.userName}</td>
					<td>${i.content}</td>
					<td>${empty i.replyContent ? '暂未回复' : i.replyContent}</td>
					<td class="reply-box">
						<form action="jsp/admin/SiteManageServlet?action=replyMessage" method="post" class="form-inline">
							<input type="hidden" name="messageId" value="${i.messageId}">
							<input type="text" class="form-control" name="replyContent" value="${i.replyContent}" placeholder="请输入回复内容">
							<button type="submit" class="btn btn-success btn-sm">保存回复</button>
						</form>
						<a href="jsp/admin/SiteManageServlet?action=delMessage&id=${i.messageId}" class="btn btn-danger btn-sm" style="margin-top:8px;" onclick="return confirm('确定要删除这条留言吗？')">删除留言</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
