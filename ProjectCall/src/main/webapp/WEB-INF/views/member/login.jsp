<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>술배틀 Login</title>
</head>
<body>
	<form action="loginresult" method="post">
		<table>
			<tr>
				<th>아이디:</th>
				<td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<th>비밀번호:</th>
				<td><input type="password" name="pass" /></td>
				<td><input type="submit" value="로그인" /></td>
			</tr>
		</table>
	</form>
</body>
</html>