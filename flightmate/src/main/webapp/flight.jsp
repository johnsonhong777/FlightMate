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
	 <!-- Flight Management Section -->
	    <h2>Flight Management</h2>
	    <table class="dashboard-table w-full border-2 rounded">
	        <thead>
	            <tr>
	                <th>Flight ID</th>
	                <th>Pilot Name</th>
	                <th>Flight Hours</th>
	                <th>Status</th>
	                <th>Actions</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach var="flight" items="${flights}">
	                <tr>
	                    <td>${flight.flightId}</td>
	                    <td>${flight.pilotName}</td>
	                    <td>${flight.flightHours}</td>
	                    <td>${flight.status}</td>
	                    <td>
	                   		<form action="updateFlightStatus" method="post">
							    <input type="hidden" name="flightId" value="${flight.id}">
							    <button type="submit" name="status" value="Approved" class='btn success'>Approve</button>
							    <button type="submit" name="status" value="Rejected" class='btn error'>Reject</button>
							</form>
	                            <a href="complete-flight?id=${flight.flightId}" class="btn">Mark Complete</a>
	                    </td>
	                </tr>
	            </c:forEach>
	        </tbody>
	    </table>

	</main>
</body>
</html>