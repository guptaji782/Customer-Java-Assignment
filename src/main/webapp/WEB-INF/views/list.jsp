<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
	<style type="text/css">
	table { 
    	table-layout:fixed;
    	border-spacing: 10px;
	}
	td{
		border: 1px solid;
		overflow: hidden; 
    	text-overflow: ellipsis; 
    	word-wrap: break-word;
    	
    	
	}
	</style>
</head>
<html>
<body>
	<table>
	<a href='nw'>Add Customer</a>
	<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Address</th>
			<th>City</th>
			<th>State</th>
			<th>Email</th>
			<th>Phone</th>
			<th colspan="2">Action</th>
		</tr>
	<c:forEach items="${cb }" var="c">
		
		<tr>
			<td><c:out value="${c.first_name }"></c:out></td>
			<td><c:out value="${c.last_name }"></c:out></td>
			<td><c:out value="${c.address }"></c:out></td>
			<td><c:out value="${c.city }"></c:out></td>
			<td><c:out value="${c.state }"></c:out></td>
			<td><c:out value="${c.email }"></c:out></td>
			<td><c:out value="${c.phone }"></c:out></td>
			<td><a href='del?uuid=${c.first_name }'>Delete</a></td>
			<td><a href='upd?uuid=${c.first_name }'>Edit</a></td>
		</tr>
	</c:forEach>
	
	</table>
	
</body>
</html>