<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String currentNav = (String) pageContext.getAttribute("nav");
	if (currentNav == null || currentNav.length() == 0) {
		String requestUri = request.getRequestURI();
		if ("board".equals(request.getParameter("view"))) {
			currentNav = "board";
		} else if (requestUri.endsWith("/index.jsp")) {
			currentNav = "home";
		} else if (requestUri.contains("BookList") || requestUri.endsWith("/booklist.jsp") || requestUri.endsWith("/bookdetails.jsp")) {
			currentNav = "books";
		} else {
			currentNav = "";
		}
		pageContext.setAttribute("nav", currentNav);
	}
%>
<div class="head">
	<div class="top">
		<div class="container">
			<div class="pull-right">
				<c:choose>
					<c:when test="${empty landing}">
						<div class="top-right">
							您好，请
							<a href="jsp/book/reg.jsp?type=login">登录</a>
							<a href="jsp/book/reg.jsp?type=reg">注册</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="btn-group adminName top-right">
							<a href="javascript:void(0)">
								${landing.name} <span class="caret"></span>
							</a>
							<ul class="dropdown-menu dropdown-menu-right">
								<li><a href="OrderServlet?action=list">我的订单</a></li>
								<li><a style="border-top:1px #ccc solid" href="UserServlet?action=off" onClick="return confirm('确定要退出登录吗？')">退出登录</a></li>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="mid container">
		<div class="row">
			<a class="logo col-md-5" title="网上书店系统">
				<span id="site-name">网上书店系统</span>
			</a>
			<div class="search col-md-4">
				<div class="input-group">
					<form action="BookList2" method="get">
						<input style="float: left;width: 160px;" type="text" class="form-control" name="seachname" placeholder="请输入想搜索的图书名称...">
						&nbsp;&nbsp;&nbsp;
						<input style="float: left;width: 55px;" class="btn btn-default" type="submit" value="搜索"/>
					</form>
				</div>
			</div>
			<div class="shopcart col-md-2 col-md-offset-1">
				<a id="cart" href="jsp/book/cart.jsp">
					<span class="badge num">
						<c:choose>
							<c:when test="${!empty shopCart}">
								${shopCart.getTotQuan()}
							</c:when>
							<c:otherwise>
								0
							</c:otherwise>
						</c:choose>
					</span>
					<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
					<span>购物车</span>
					<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
				</a>
			</div>
		</div>
		<div class="navbar">
			<ul class="nav navbar-nav">
				<li class="${nav eq 'home' ? 'active' : ''}">
					<a href="jsp/book/index.jsp">首页<c:if test="${nav eq 'home'}"><span class="sr-only">(current)</span></c:if></a>
				</li>
				<li class="${nav eq 'books' ? 'active' : ''}">
					<a href="BookList">全部图书<c:if test="${nav eq 'books'}"><span class="sr-only">(current)</span></c:if></a>
				</li>
				<li class="${nav eq 'board' ? 'active' : ''}">
					<a href="BookList?view=board#notice-message-block">公告与留言<c:if test="${nav eq 'board'}"><span class="sr-only">(current)</span></c:if></a>
				</li>
			</ul>
		</div>
	</div>
</div>
