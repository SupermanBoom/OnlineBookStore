<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("basePath", basePath);
	String nav = "books";
	if ("board".equals(request.getParameter("view"))) {
		nav = "board";
	}
	pageContext.setAttribute("nav", nav);
	pageContext.setAttribute("isBoardView", "board".equals(nav));
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<base href="${basePath}">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${isBoardView ? '公告与留言' : '图书列表'}</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.js"></script>
	<link rel="stylesheet" href="css/book/head_footer.css" >
	<link rel="stylesheet" href="css/book/booklist.css" />
	<script type="text/javascript" src="js/book/getCatalog.js"></script>
	<script type="text/javascript" src="js/book/index.js"></script>
	<script type="text/javascript" src="js/book/landing.js"></script>
	<script type="text/javascript" src="js/book/addcart.js"></script>
</head>
<body>

	<div class="container-fullid">
		<%@include file="header.jsp" %>

		<div class="wrapper">
			<div class="main container">
				<div class="row">
					<c:choose>
						<c:when test="${isBoardView}">
							<div class="col-md-12 main-right">
								<div id="site-intro-block" class="site-card col-md-12">
									<h3>书店简介</h3>
									<p id="site-introduction"></p>
									<p><strong>联系方式：</strong><span id="site-contact"></span></p>
								</div>
								<div id="notice-message-block" class="site-card col-md-12">
									<h3>公告与留言</h3>
									<div class="row">
										<div class="col-md-5">
											<h4>最新公告</h4>
											<ul id="notice-list" class="notice-list"></ul>
										</div>
										<div class="col-md-7">
											<h4>客户留言</h4>
											<form id="message-form" class="form-horizontal message-form">
												<div class="form-group">
													<label class="col-sm-3 control-label">留言人</label>
													<div class="col-sm-8">
														<input type="text" class="form-control" name="userName" placeholder="请输入留言人姓名">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-3 control-label">留言内容</label>
													<div class="col-sm-8">
														<textarea class="form-control" rows="4" name="content" placeholder="请输入你的问题或建议"></textarea>
													</div>
												</div>
												<div class="form-group">
													<div class="col-sm-offset-3 col-sm-3">
														<button type="submit" class="btn btn-success btn-block">提交留言</button>
													</div>
												</div>
											</form>
											<div id="message-list"></div>
										</div>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-2 main-left">
								<h3>图书分类</h3>
								<ul id="catalog-list">
									<li><a href="BookList">全部图书</a></li>
								</ul>
							</div>
							<div class="col-md-10 main-right">
								<div class="pro col-md-12">
									<h3>图书列表 - ${title}</h3>
									<div class="pro-list">
										<ul class="row">
											<c:choose>
												<c:when test="${!empty bookList}">
													<c:forEach items="${bookList}" var="i">
														<li class="col-md-3">
															<div class="list">
																<a href="bookdetail?bookId=${i.bookId}">
																	<img class="img-responsive" src="${i.upLoadImg.imgSrc}" />
																</a>
																<div class="proinfo">
																	<h2>
																		<a class="text-center" href="bookdetail?bookId=${i.bookId}">${i.bookName}</a>
																	</h2>
																	<p class="text-muted">库存：${i.stock}</p>
																	<p>
																		<i>${i.price}</i>
																		<a class="btn btn-danger btn-xs ${i.stock le 0 ? 'disabled' : ''}" onclick="addToCart(${i.bookId})" href="javascript:void(0)" data-toggle="modal" data-target=".bs-example-modal-sm">加入购物车</a>
																	</p>
																</div>
															</div>
														</li>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<p style="text-align:center;font-size:20px;height:200px;">当前没有图书信息</p>
												</c:otherwise>
											</c:choose>
										</ul>
										<ul class="pager row">
											<li><button class="homePage btn btn-default btn-sm">首页</button></li>
											<li><button class="prePage btn btn-sm btn-default">上一页</button></li>
											<li>共 ${pageBean.pageCount} 页 | 当前第 ${pageBean.curPage} 页</li>
											<li>
												跳转到
												<div class="input-group form-group page-div">
													<input id="page-input" class="form-control input-sm" type="text" name="page"/>
													<span>
														<button class="page-go btn btn-sm btn-default">GO</button>
													</span>
												</div>
											</li>
											<li><button class="nextPage btn btn-sm btn-default">下一页</button></li>
											<li><button class="lastPage btn btn-sm btn-default">末页</button></li>
										</ul>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</div>

	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  	<div class="modal-dialog modal-sm">
	    	<div class="modal-content">
	    		<div class="modal-body" style="color:green;font-size:24px;">
				  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;已成功加入购物车
				</div>

	      		<div class="modal-footer">
	      			<a href="javascript:void(0)" type="button" class="btn btn-default" data-dismiss="modal">继续浏览</a>
			        <a href="jsp/book/cart.jsp" type="button" class="btn btn-success">去购物车结算</a>
			    </div>
	    	</div>
	  	</div>
	</div>
<script type="text/javascript">
	<c:if test="${!isBoardView}">
	function buildPageUrl(page){
		var params = new URLSearchParams(window.location.search);
		params.set("page", page);
		if(window.location.pathname.indexOf("BookList") > -1 && !params.get("action")){
			params.set("action", "list");
		}
		var query = params.toString();
		return window.location.pathname + (query ? "?" + query : "");
	}

	if("${pageBean.curPage}"==1){
		$(".homePage").attr("disabled","disabled");
		$(".prePage").attr("disabled","disabled");
	}
	if("${pageBean.curPage}"=="${pageBean.pageCount}"){
		$(".page-go").attr("disabled","disabled");
		$(".nextPage").attr("disabled","disabled");
		$(".lastPage").attr("disabled","disabled");
	}
	$(".homePage").click(function(){
		window.location=buildPageUrl(1);
	})
	$(".prePage").click(function(){
		window.location=buildPageUrl("${pageBean.prePage}");
	})
	$(".nextPage").click(function(){
		window.location=buildPageUrl("${pageBean.nextPage}");
	})
	$(".lastPage").click(function(){
		window.location=buildPageUrl("${pageBean.pageCount}");
	})
	$(".page-go").click(function(){
		var page=$("#page-input").val();
		var pageCount=${pageBean.pageCount};
		if(isNaN(page)||page.length<=0){
			$("#page-input").focus();
			alert("请输入数字页码");
			return;
		}
		if(page > pageCount || page < 1 ){
			alert("输入的页码超出范围");
			$("#page-input").focus();
		}else{
			window.location=buildPageUrl(page);
		}
	})
	</c:if>
</script>
</body>
</html>
