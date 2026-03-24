$(function(){
	var form=$("#bookAddForm").Validform({
		tiptype:2
	});

	form.addRule([
		{
			ele:"#bookName",
		    datatype:"*2-20",
		    ajaxurl:"jsp/admin/BookManageServlet?action=find",
		    nullmsg:"请输入书名！",
		    errormsg:"书名至少 2 个字符，最多 20 个字符！"
		},
		{
			ele:"#catalog",
			datatype:"*",
			nullmsg:"请选择图书分类！",
			errormsg:"请选择图书分类！"
		},
		{
			ele:"#price",
			datatype:"/^[0-9]{1,}([.][0-9]{1,2})?$/",
			nullmsg:"价格不能为空！",
			errormsg:"价格只能为数字！"
		},
		{
			ele:"#author",
		    datatype:"*2-30",
		    nullmsg:"请输入作者名称！",
		    errormsg:"作者名称至少 2 个字符，最多 30 个字符！"
		},
		{
			ele:"#press",
		    datatype:"*2-30",
		    nullmsg:"请输入出版社！",
		    errormsg:"出版社至少 2 个字符，最多 30 个字符！"
		},
		{
			ele:"#bookImg",
		    datatype:"*",
		    nullmsg:"请上传图书图片！",
		    errormsg:"请上传图书图片！"
		}

	]);

});
