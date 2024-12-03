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

 
        <!-- Flights Table Section -->
        <section class="container mt-2">
            <h2 class="section-title">All Flights</h2>
                <table class="temptable w-full">
                <thead>
                    <tr>
                        <th>Flight Number</th>
                        <th>Origin</th>
                        <th>Destination</th>
                        <th>Status</th>
                        <th>Departure Time</th>
                        <th>Arrival Time</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="flight" items="${flights}">
                    <tr>
                        <td>${flight.flightNumber}</td>
                        <td>${flight.origin}</td>
                        <td>${flight.destination}</td>
                        <td>${flight.status}</td>
                        <td>${flight.departureTime}</td>
                        <td>${flight.arrivalTime}</td>
                        <td>
                            <div class="flex">
                                <a href="flight-management?action=edit&id=${flight.flightId}" class="btn">Edit</a>
                                <a href="flight-management?action=delete&id=${flight.flightId}" class="btn error"
                                   onclick="return confirm('Are you sure you want to delete this flight?');">
                                    Delete
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
		</section>
    </main>
</body>
</html>