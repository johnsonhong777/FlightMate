<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
<title>FlightMate | Flight Status</title>
</head>
<body class="background">
	<jsp:include page='./components/header.jsp' />
	<main>
		<form action="updateFlightStatus" method="post">
		     <input type="hidden" name="flightId" value="${flight.id}">
		     <button type="submit" name="status" value="Approved" class='btn success'>Approve</button>
		     <button type="submit" name="status" value="Rejected" class='btn error'>Reject</button>
		</form>
	</main>
</body>
</html>