function addToCart(bookId){
	$.ajax({
		url:"CartServlet?action=add",
		dataType:"json",
		async:true,
		data:{"bookId":bookId},
		type:"POST",
		success:function(data){
			if(data.status=="y"){
				$("#cart .num").html(data.totQuan);
			}else if(data.info){
				alert(data.info);
			}
		}
	})
}
