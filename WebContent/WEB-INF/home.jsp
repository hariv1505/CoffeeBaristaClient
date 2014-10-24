<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Barista - Home</title>
</head>
<body>
	<!-- do for all coffees -->
	<a href="showorder.jsp?id=1">Order 1</a>
	<form id="delete1" action="../CoffeeService/orders/1" method="DELETE">
		<input type="submit" value="Cancel" />
	</form>
	<!-- need to change Service - needs to have a 'status' option for each order -->
	<form id="prepare1" action="../CoffeeService/orders/1" method="PUT">
		<input id="prepare" type="hidden" value="true" />
		<input type="submit" value="Prepare" />
	</form>
	<!-- not sure how to check payments -->
	<!-- need to change Service - needs to have a 'status' option for each order -->
	<form id="release1" action="../CoffeeService/orders/1" method="PUT">
		<input id="release" type="hidden" value="true" />
		<input type="submit" value="Prepare" />
	</form>
</body>
</html>