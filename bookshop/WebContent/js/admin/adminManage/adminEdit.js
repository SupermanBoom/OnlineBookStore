$(function(){
	var form=$("#myForm").Validform({
		tiptype:2,
	});
	form.addRule([
		{
			ele:"#passWord",
			datatype:"s4-8",
			nullmsg:"请输入密码！",
			errormsg:"密码长度应为 4-8 位！"
		},
		{
			ele:"#c_passWord",
			datatype:"*",
			recheck:"passWord",
			mullmsg:"请输入确认密码！",
			errormsg:"两次输入的密码不一致，请重新输入！"
		},
		{
			ele:"#name",
			datatype:"s2-8",
			mullmsg:"请输入姓名！",
			errormsg:"姓名长度应为 2-8 位！"
		}
	]);
});
