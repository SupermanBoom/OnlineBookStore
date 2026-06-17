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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>后台导航</title>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<link rel="stylesheet" href="css/admin/left.css">
</head>
<body>
	<div class="left">
		<ul>
			<li class="list">
				<h3>账号管理</h3>
				<ul>
					<li><a href="jsp/admin/AdminManageServlet?action=list" target="rFrame">管理员管理</a></li>
					<li><a href="jsp/admin/UserManageServlet?action=list" target="rFrame">客户管理</a></li>
				</ul>
			</li>
			<li class="list">
				<h3>图书管理</h3>
				<ul>
					<li><a href="jsp/admin/BookManageServlet?action=list" target="rFrame">图书列表</a></li>
					<li><a href="jsp/admin/CatalogServlet?action=list" target="rFrame">分类管理</a></li>
				</ul>
			</li>
			<li class="list">
				<h3>订单管理</h3>
				<ul>
					<li><a href="jsp/admin/OrderManageServlet?action=list" target="rFrame">订单列表</a></li>
					<li><a href="jsp/admin/OrderManageServlet?action=processing" target="rFrame">订单处理</a></li>
				</ul>
			</li>
			<li class="list">
				<h3>经营报表</h3>
				<ul>
					<li><a href="jsp/admin/ReportServlet" target="rFrame">数据概览</a></li>
				</ul>
			</li>
			<li class="list">
				<h3>网站维护</h3>
				<ul>
					<li><a href="jsp/admin/SiteManageServlet?action=info" target="rFrame">书店信息</a></li>
					<li><a href="jsp/admin/SiteManageServlet?action=noticeList" target="rFrame">公告管理</a></li>
					<li><a href="jsp/admin/SiteManageServlet?action=messageList" target="rFrame">留言管理</a></li>
				</ul>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
		$(".list h3").next().slideUp(300);
		$(".list h3").click(function(){
			$(".list h3").css("color","#fff");
			$(".list h3").next().slideUp(300);
			if($(this).next().css("display")=="none"){
				$(this).css("color","#52de92");
				$(this).next().slideDown(300);
			}else{
				$(this).next().slideUp(300);
			}
		})

		$(".list ul a").click(function(){
			$(".list ul a").css("color","#000");
			$(this).css("color","#52de92");
		})
	</script>
</body>
</html>
