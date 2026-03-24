$(function(){
	var form=$("#myForm").Validform({
		tiptype:2,
	});

	form.addRule([
		{
			ele:"#userName",
		    datatype:"*2-15",
		    ajaxurl:"jsp/admin/UserManageServlet?action=find",
		    nullmsg:"*请输入用户名！",
		    errormsg:"*用户名长度应为 2-15 位！"
		},
		{
			ele:"#passWord",
			datatype:"*4-8",
			nullmsg:"*请输入密码！",
			errormsg:"*密码长度应为 4-8 位！"
		},
		{
			ele:"#c_passWord",
			datatype:"*",
			recheck:"passWord",
			mullmsg:"*请输入确认密码！",
			errormsg:"*两次输入的密码不一致，请重新输入！"
		},
		{
			ele:"#name",
		    datatype:"*2-15",
		    nullmsg:"请输入姓名！",
		    errormsg:"姓名长度应为 2-15 位！"
		},
		{
			ele:"#sex",
		    datatype:"*",
		    nullmsg:"请选择性别！",
		    errormsg:"请选择性别！"
		},
		{
			ele:"#age",
		    datatype:"n1-2",
		    nullmsg:"请输入年龄！",
		    errormsg:"年龄应为 1-2 位数字！"
		},
		{
			ele:"#tell",
		    datatype:"/^13[0-9]{9}$|17[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|18[0-9]{9}$/",
		    nullmsg:"请输入电话号码！",
		    errormsg:"电话号码格式不正确，请重新输入！"
		},
		{
			ele:"#address",
		    datatype:"*1-100",
		    nullmsg:"请输入地址！",
		    errormsg:"地址长度不能超过 100 个字符！"
		}
	]);
});
