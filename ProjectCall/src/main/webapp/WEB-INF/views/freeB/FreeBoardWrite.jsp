<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.project.call.domain.*" %>
<% 
	Member m = (Member)session.getAttribute("loginUser"); 
%>
<!DOCTYPE html >
<html>
<head>
<meta content="charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="resources/js/jquery-1.11.3.min.js"></script>
</head>
<body>

<script>
/*  $(function() {
	 $("#").on("click", function() {
	
	if($("#title").val () != "") {
		
		$ajax({
			url : 'title',
			type : 'post',
			datatype : "text",
			data : ({
				
				loginUser: $("#loginUser").val(),
				title : $("#title").val()
				
			}),
			success : function(result, status, xhr){
				if(result == 1){
					$("FreeBoardWrite").submit();
				}else {
					alert('제목이나 내용을 입력해주세요');
				}	
			}
		
		});
 */		 

function edit1(){
	var title = $("#title").val();
	var content = $("#content").val();
	var file = $("#file").val();
	
	 if(title == ""){
		alert("제목을 입력 하세요.");
		return false;
	} else if(content == ""){
		alert("내용을 입력 하세요.");
		return false;
	} 	
}



</script>
<form action="FreeBoardWrite" method="POST"
		enctype="multipart/form-data" onsubmit="return edit1()">
	<table>
	
	<tr><th>작&nbsp;성&nbsp;자</th>
		<td><span><%=m.getNickName()%></span>
			<input type="hidden" name="writer" value="<%=m.getNickName()%>"/>
			<input type="hidden" name="email" value="<%=m.getEmail()%>"/>
		</td>
		
	</tr>
	
	<tr>	
	<td>제&nbsp;목	
		<input type="text" name="title" id="title" placeholder="제목을&nbsp;입력해주세요" /></td>
	</tr>
	
	<tr>	
		<td><textarea name="content" id="content" rows="10" cols="60"  placeholder="내용을&nbsp;입력해주세요" ></textarea></td>
	</tr>
	
	<tr>
		<td><input type="file" name="photo" id="file"/></td>
	</tr>	
		
	</table>
	
	<input type="submit" value="등록" />
	<input type="reset" value="취소" /> 
</form>
</body>
</html>