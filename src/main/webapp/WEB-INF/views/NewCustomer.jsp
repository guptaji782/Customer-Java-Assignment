<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
	input{
		margin: 10px;
	}
</style>
</head>
<body>
	<form action="/add" method="post">
		<h2>Customer Details</h2>
		<input type="text" placeholder="First name" name="first_name" required="required">
		<input type="text" placeholder="Last name" name="last_name" required="required">
		<br>
		<input type="text" placeholder="Street" name="street">
		<input type="text" placeholder="Adderss" name="address">
		<br>
		<input type="text" placeholder="City" name="city">
		<input type="text" placeholder="State" name="state">
		<br>
		<input type="text" placeholder="Email" name="email">
		<input type="text" placeholder="Phone" name="phone">
		<br>
		<input type="submit" Value="Submit">
	</form>
</body>
</html>