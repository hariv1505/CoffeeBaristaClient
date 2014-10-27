<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Barista - Home</title>
	</head>
	<body>
		<b><i>${status} <b>${id}</b></i></b><br>
		<!-- do for all coffees -->
		<c:forEach var="order" items="${orders}">
		<table style="width:25%">
			<tr>
				<td>Order:<b>${order.id}</b></td>
				<td><a href="prepare?id=${order.id}&preparing=true">Prepare</a></td>
				<td><a href="checkPayment?id=${order.id}&checkPay=true">Check Payment</a></td>
				<td><a href="releaseOrder?id=${order.id}&release=true">Release</a><td>
				
			</tr>
		</table>
		</c:forEach>
		<!-- display payment if there is an associated one -->
	</body>
</html>