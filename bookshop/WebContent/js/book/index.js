$.ajax({
	url:"ShopIndex",
	dataType:"json",
	async:true,
	data:{},
	type:"POST",
	success:function(data){
		datalist(data);
	}
});

function datalist(data){
	if(data.siteInfo!=null){
		$("#site-name").text(data.siteInfo.storeName || "网上书店系统");
		$("#site-introduction").text(data.siteInfo.introduction || "");
		$("#site-contact").text(data.siteInfo.contactInfo || "");
	}

	if(data.recBooks!=null){
		$("#recBooks ul").empty();
		$.each(data.recBooks,function(i,n){
			var stockText = n.stock > 0 ? "库存：" + n.stock : "暂时缺货";
			var disabled = n.stock > 0 ? "" : " disabled='disabled'";
			var tag="<li class='col-md-3'><div class='list'>" +
			"<a href='bookdetail?bookId="+n.bookId+"'><img class='img-responsive' src='"+n.upLoadImg.imgSrc+"'/></a>"+
			"<div class='proinfo'><h2><a class='text-center' href='bookdetail?bookId="+n.bookId+"'>"+n.bookName+"</a></h2>"+
			"<p class='text-muted' style='margin-bottom:4px;'>"+stockText+"</p>"+
			"<p><i>"+n.price+"</i><a class='btn btn-danger btn-xs' href='javascript:void(0)' onclick='addToCart("+n.bookId+")' "+
				disabled+" data-toggle='modal' data-target='.bs-example-modal-sm'>加入购物车</a></p></div></div></li>";
			$("#recBooks ul").append(tag);
		})
	}

	if(data.newBooks!=null){
		$("#newBooks ul").empty();
		$.each(data.newBooks,function(i,n){
			var stockText = n.stock > 0 ? "库存：" + n.stock : "暂时缺货";
			var disabled = n.stock > 0 ? "" : " disabled='disabled'";
			var tag="<li class='col-md-3'><div class='list'>" +
			"<a href='bookdetail?bookId="+n.bookId+"'><img class='img-responsive' src='"+n.upLoadImg.imgSrc+"'/></a>"+
			"<div class='proinfo'><h2><a class='text-center' href='bookdetail?bookId="+n.bookId+"'>"+n.bookName+"</a></h2>"+
			"<p class='text-muted' style='margin-bottom:4px;'>"+stockText+"</p>"+
			"<p><i>"+n.price+"</i><a class='btn btn-danger btn-xs' href='javascript:void(0)' onclick='addToCart("+n.bookId+")' "+
				disabled+" data-toggle='modal' data-target='.bs-example-modal-sm'>加入购物车</a></p></div></div></li>";
			$("#newBooks ul").append(tag);
		})
	}

	if(data.noticeList!=null){
		$("#notice-list").empty();
		$.each(data.noticeList,function(i,n){
			var tag="<li><strong>"+n.title+"</strong><p class='text-muted' style='margin:6px 0 0;'>"+n.content+"</p></li>";
			$("#notice-list").append(tag);
		});
	}

	if(data.messageList!=null){
		$("#message-list").empty();
		$.each(data.messageList,function(i,n){
			var reply = n.replyContent ? "<div class='well well-sm' style='margin-top:8px;'><strong>店主回复：</strong>"+n.replyContent+"</div>" : "";
			var tag="<div class='panel panel-default'><div class='panel-heading'><strong>"+n.userName+"</strong></div>"+
				"<div class='panel-body'><p>"+n.content+"</p>"+reply+"</div></div>";
			$("#message-list").append(tag);
		});
	}
}

$(function(){
	$("#message-form").submit(function(){
		$.post("SiteServlet?action=addMessage", $(this).serialize(), function(data){
			alert(data.info);
			if(data.status=="y"){
				window.location.reload();
			}
		}, "json");
		return false;
	});
});
