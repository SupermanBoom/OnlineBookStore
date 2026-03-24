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
<title>经营报表</title>
<link rel="stylesheet" href="bs/css/bootstrap.css">
<style>
	body{background:#eee;padding:20px;}
	.box{background:#fff;padding:24px;}
	.card{background:#f8f8f8;border:1px solid #e5e5e5;padding:18px;text-align:center;margin-bottom:20px;}
	.card strong{display:block;font-size:28px;color:#d9534f;margin-top:8px;}
</style>
</head>
<body>
	<div class="box">
		<h2 class="text-center">书店经营数据概览</h2>
		<div class="row">
			<div class="col-sm-3"><div class="card">图书总数<strong>${bookCount}</strong></div></div>
			<div class="col-sm-3"><div class="card">客户总数<strong>${userCount}</strong></div></div>
			<div class="col-sm-3"><div class="card">订单总数<strong>${orderCount}</strong></div></div>
			<div class="col-sm-3"><div class="card">库存总量<strong>${stockCount}</strong></div></div>
		</div>
		<div class="row">
			<div class="col-sm-4"><div class="card">待处理订单<strong>${pendingOrderCount}</strong></div></div>
			<div class="col-sm-4"><div class="card">已发货订单<strong>${shipOrderCount}</strong></div></div>
			<div class="col-sm-4"><div class="card">订单总金额<strong>${totalAmount}</strong></div></div>
		</div>
		<div class="row">
			<div class="col-sm-4"><div class="card">已成交金额<strong>${dealAmount}</strong></div></div>
		</div>
		<h3>低库存图书提醒</h3>
		<table class="table table-striped">
			<tr>
				<th>图书名称</th>
				<th>图书分类</th>
				<th>当前库存</th>
			</tr>
			<c:forEach items="${lowStockBooks}" var="i">
				<tr>
					<td>${i.bookName}</td>
					<td>${i.catalog.catalogName}</td>
					<td>${i.stock}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
