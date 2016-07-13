<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户注册</title>
</head>
<body>
<center>
	<h2>用户注册</h2>

	<%--登录表单--%>
	<form action="user/GT_RegisterUser.sy" method="post">
	<table>
		<tr>
			<td>用户名：</td>
			<td><input type="text" name="user.name"></td>
		</tr>
		<tr>
			<td>密&nbsp;&nbsp;码：</td>
			<td><input type="text" name="user.password"></td>
		</tr>
		<tr>
			<td colspan="2">
			<input type="submit" value="注册">
			<input type="reset" value="重置">
			</td>
		</tr>
	</table>
	</form>
</center>
</body>
</html>