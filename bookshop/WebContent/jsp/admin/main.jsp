<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<title>后台首页</title>
	<style type="text/css">
		*{
			margin:0;
			padding:0;
		}
		body{
			background:#eee;
			font-family:"Microsoft YaHei";
		}
		.wrap{
			max-width:860px;
			margin:80px auto;
			background:#fff;
			padding:40px;
			box-shadow:0 8px 24px rgba(0,0,0,.08);
		}
		h2{
			text-align:center;
			margin-bottom:20px;
		}
		ul{
			margin-top:20px;
			line-height:32px;
			padding-left:20px;
		}
	</style>
</head>
<body>
	<div class="wrap">
		<h2>欢迎 ${adminUser.name} 登录书店后台管理系统</h2>
		<p>当前后台已经支持：用户注册登录、图书分类浏览、购物车下单、订单管理、图书与分类管理、库存维护、账目概览、公告发布、客户留言反馈等功能。</p>
		<ul>
			<li>新增图书可以通过“图书管理 -> 增加图书”完成。</li>
			<li>前台下单后，系统会自动校验库存并扣减库存数量。</li>
			<li>“经营报表”页面可以查看图书总数、订单金额和低库存提醒。</li>
			<li>“网站维护”中可以修改书店简介、发布公告以及回复客户留言。</li>
		</ul>
	</div>
</body>
</html>
