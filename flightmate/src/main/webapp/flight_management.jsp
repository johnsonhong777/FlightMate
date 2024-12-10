<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>FlightMate | Dashboard</title>
</head>
<body class="background">
    <jsp:include page='./components/header.jsp' />
    <main>
        <section class="container mt-2">
            <h1 class="subtitle">Flight Management</h1>

            <!-- Button to create a new flight -->
            <c:if test="${user.getRole().toString() == 'ADMINISTRATOR'}">
                <a href="flight-management?action=create" class="btn">Add New Flight</a>
            </c:if>
        </section>

 		
	      
                <h2 class="section-title center">Flights</h2>
				<div class="flightTableData mt-2">
	                    <table class="dashboard-table w-full border-2 rounded mt-2">
	                        <thead>
		                        <tr>
		                            <th>Flight Number</th>
		                            <th>Departure Time</th>
		                            <th>Arrival Time</th>
		                            <th>Origin</th>
		                            <th>Destination</th>
		                            <th>Status</th>
		                              <th>Action</th>
		                        </tr>
	                        </thead>
	                        <tbody>
	                        <c:forEach var="item" items="${flights}">
	                            <tr>
	                                <td>${item.flightNumber}</td>
	                                <td>${item.departureTime}</td>
	                                <td>${item.arrivalTime}</td>
	                                <td>${item.origin}</td>
	                                <td>${item.destination}</td>
	                                <td>${item.status}</td>
	                                <td>
                            <div class="flex">
                                <a href="flight-management?action=edit&id=${item.flightId}" class="btn">Edit</a>
                                <a href="flight-management?action=delete&id=${item.flightId}" class="btn error"
                                   onclick="return confirm('Are you sure you want to delete this flight?');">
                                    Delete
                                </a>
                            </div>
                        </td>
	                            </tr>
	                        </c:forEach>
	                        </tbody>
	                    </table>
	            </div>    
	
       
    </main>
</body>
</html>