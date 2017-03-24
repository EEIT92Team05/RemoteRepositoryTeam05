<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.* , model.*"%>
<jsp:useBean id="service" class="model.ProductService" />
<c:set var="totalPage" value="${service.totalPage }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>神鬼論壇</title>
<style>
table {
	margin: auto;
	margin-top: 100px;
	border-collapse: collapse;
}
</style>
</head>
<body>

	<c:forEach var="aBean" items="${productList }" varStatus="vs">
		<c:if test="${vs.first }">
			<c:out value="<table border='1px solid black' collapse >"
				escapeXml="false" />
			<tr><td colspan="6">
				<c:if test="${empty shoppingCart}">
					<c:out value="購物車沒有商品" />
				</c:if>
				<c:if test="${shoppingCart}">
					<c:out
						value="購物車有${shoppingCart.content }項商品 ,總計${shoppingCart.totalcost }元" />
				</c:if>
			</td></tr>
		</c:if>

		<tr>
			<td width='64'><img height='100' width='80'
				src="showImage.do?id=${aBean.pdid}&fileName=${aBean.filename}" /></td>
			<td>名稱:${aBean.pdname }</td>
			<td>售價:${aBean.pdprice}</td>
			<td>描述:${aBean.pdinfo}</td>
			<td>
				<form method="" action="">
					<select name="amount">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select> <input type="submit" value="加入購物車" />
				</form>
			</td>
		</tr>
		<c:if test="${vs.last }">
			<c:out value="</table>" escapeXml="false" />
		</c:if>
	</c:forEach>
	<table>
		<tr>
			<c:if test="${pageNo > 1 }">
				<td><a href="/xxx/getProduct.do?pageNo=${pageNo-1 }">前一頁</a></td>
			</c:if>
			<c:if test="${pageNo < totalPage}">
				<td><a href="/xxx/getProduct.do?pageNo=${pageNo + 1}">下一頁</a></td>
			</c:if>
			<td>第${pageNo }頁/共${totalPage }頁</td>
		</tr>
	</table>
</body>
</html>