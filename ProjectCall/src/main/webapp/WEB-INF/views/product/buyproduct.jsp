<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<form action="buyproduct" name="form1">
			<table id="cartTable" class="table">
				<tr>
					<th width="5%">선택</th>
					<th colspan="2">상품정보</th>
					<th>판매가</th>
					<th>수량</th>
					<th>배송비</th>
					<th>합계</th>
				</tr>
				<c:forEach var="p" items="${ pList }" varStatus="s">
				<tr id="datatr">
					<td width="5%">
					<input type="checkbox" value="${p.pProductCode }" name="checkbox" id="check${s.count }" checked onchange="changef(${s.count })" />
					</td>
					<td width="10%" ><img style="width: 80px; height: 80px" src="resources/uploadimages/${p.pImage }" /></td>
					<td width="30%">${p.pName }</td>
					<td width="10%">${p.pPrice } <input type="hidden" id="price${s.count }" value="${p.pPrice }" />
					<input type="hidden" id="count${s.last }" value="${s.count }" /></td>
					<td width="15%"><input type="text" id="quentity${s.count }" name="quentity" value="${p.pQuantity }" readonly />
					<img src="resources/images/btn_plus.gif" onclick="plus(${s.count })" />
					<img src="resources/images/btn_minus.gif" onclick="minus(${s.count })" /></td>
					<td width="20%"><input type="text" id="result${s.count }" name="payprice" value="${p.pPrice * p.pQuantity }" readonly /></td>
				</tr>
				</c:forEach>
			</table>
			<div id="cartbottom">
				<div id="price">
				<div id="pc"><b><input type="text" id="payresult" name="payresult" readonly /></b></div><div id="p">상품합계포인트</div>
				<div id="pc"><b><input type="text" id="point" name="point" value="${loginUser.point }" readonly /></b></div><div id="p">회원님의 포인트</div>
				</div>
			</div>
			<div id="buttonGroup">
			<a href="cartsubmit()">구매하기</a>
			<a href="javascript:back()">돌아가기</a>
	</div>
	</form>
</html>