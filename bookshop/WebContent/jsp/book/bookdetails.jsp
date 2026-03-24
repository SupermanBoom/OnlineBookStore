<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<base href="${basePath}">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>图书详情</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.js"></script>
	<script type="text/javascript" src="js/book/landing.js"></script>
	<link href="css/book/head_footer.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/book/addcart.js"></script>
	<style type="text/css">
		.wrapper .pro_info{
			border-bottom: 1px #ccc solid;
			line-height: 34px;
			margin-top:20px;
		}
		.wrapper .pro_info tr td:first-child{
			font-weight: bold;
		}
		.wrapper .pro_info i{
			color:red;
			font-size:22px;
		}
		.wrapper .buy_pro{
			margin-top:20px;
		}
		.wrapper .pro_desc{
			margin:10px;
		}
		.wrapper .pro_desc h3{
			border-bottom: 1px #ccc solid;
			padding:10px;
		}
		.wrapper .pro_desc div{
			text-indent: 2em;
			line-height: 2em;
		}
	</style>
</head>
<body>

	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<div class="main container">
				<div class="sub-nav">
					<ol class="breadcrumb">
  						<li><a href="jsp/book/index.jsp">首页</a></li>
						<li><a href="#">${bookInfo.catalog.catalogName}</a></li>
						<li class="active">${bookInfo.bookName}</li>
					</ol>
				</div>
				<div class="row">
					<div class="col-md-5">
						<img class="img-responsive" src="${bookInfo.upLoadImg.imgSrc}" />
					</div>
					<div class="col-md-7">
						<table class="pro_info">
							<tr>
								<td colspan="2"><h2>${bookInfo.bookName}</h2></td>
							</tr>
							<tr>
								<td>价格：</td>
								<td><i>￥${bookInfo.price}</i></td>
							</tr>
							<tr>
								<td>图书编号：</td>
								<td>${bookInfo.bookId}</td>
							</tr>
							<tr>
								<td>图书分类：</td>
								<td>${bookInfo.catalog.catalogName}</td>
							</tr>
							<tr>
								<td>作者：</td>
								<td>${bookInfo.author}</td>
							</tr>
							<tr>
								<td>出版社：</td>
								<td>${bookInfo.press}</td>
							</tr>
							<tr>
								<td>库存：</td>
								<td>${bookInfo.stock}</td>
							</tr>
							<tr>
								<td>上架日期：</td>
								<td>${bookInfo.addTime}</td>
							</tr>
							<tr>
								<td>服务：</td>
								<td>由书店统一发货，并提供售后服务，预计三天内送达，我们会尽力为您提供更好的服务。</td>
							</tr>
						</table>
						<p class="buy_pro">
							<a class="btn btn-info ${bookInfo.stock le 0 ? 'disabled' : ''}" href="jsp/book/cart.jsp">立即购买</a>
							<button type="button" class="btn btn-danger ${bookInfo.stock le 0 ? 'disabled' : ''}" onclick="addToCart(${bookInfo.bookId})" data-toggle="modal" data-target=".bs-example-modal-sm">加入购物车</button>
						</p>
						<c:if test="${bookInfo.stock le 0}">
							<p style="color:#d9534f;">当前图书暂时无库存，请留意后续补货信息。</p>
						</c:if>
						<p>温馨提示：支持 7 天无理由退货。</p>
					</div>
				</div>
				<div class="row pro_desc">
					<h3>图书简介</h3>
					<div>${bookInfo.description}</div>
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
</body>
</html>
