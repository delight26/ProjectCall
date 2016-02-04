<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>추가정보입력</title>
</head>
<body>
	<h1>추가 정보 입력</h1>
	<form action="step4" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<th>Phone</th>
				<td>
					<select id="phone1" name="phone2">
						<option>010</option>
						<option>011</option>
						<option>016</option>
						<option>019</option>
					</select>-
					<input type="text" name="phone1" id="phone2">-<input type="text" name="phone3" id="phone2">
				</td>
			</tr>
			<tr>	
				<th>주소</th>
				<td>
					<input type="text" name="address" id="address" placeholder="주소를 적어주세요">
				</td>
			</tr>
			<tr>	
				<th>출몰지역</th>
				<td>
					<select id="area" name="area">
						<c:forEach items="${areaList}" var="area">
							<option>${area.area}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>	
				<th>한마디</th>
				<td>
					<input type="text" name="word" id="word" placeholder="하고싶은말!!">
				</td>
			</tr>
			<tr>
				<th>Photo</th>
				<td><input type="file" name="photo" id="photo" placeholder="phone"></td>
			</tr>
		</table>
		<input type="reset" value="취소">
		<input type="submit" value="입력">
	</form>
</body>
</html>