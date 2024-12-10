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
     <main class="center">
        <h1>Aircraft Management</h1>
            <h1 class="subtitle">Flight Management</h1>

          <section class="container mt-2">

            <h2 class="center">Add New Flight</h2>

            <!-- Success message -->
            <c:if test="${not empty sessionScope.success}">
                <div class="alert success center">${sessionScope.success}</div>
                <c:set var="success" value="${sessionScope.success}" />
                <c:remove var="success" scope="session" />
            </c:if>

            <!-- Error message -->
            <c:if test="${not empty sessionScope.error}">
                <div class="alert error center">${sessionScope.error}</div>
                <c:set var="error" value="${sessionScope.error}" />
                <c:remove var="error" scope="session" />
            </c:if>
            
            <form action="${pageContext.request.contextPath}/flight-management" method="POST" class="my-2">
                                <fieldset class="form-group mt-2">
            
                <input type="hidden" name="action" value="save">
<label for="flightNumber" class="form-label">Flight Number:</label>
<input type="text" id="flightNumber" name="flight_number" class="form-input flex-2 border-2 rounded" required><br>
                <!-- Departure Time -->
                    <label for="departureTime" class="form-label">Departure Time:</label>
                    <input type="datetime-local" id="departureTime" name="departureTime" class="form-input flex-2 border-2 rounded" required><br>

                <!-- Arrival Time -->
                    <label for="arrivalTime" class="form-label">Arrival Time:</label>
                    <input type="datetime-local" id="arrivalTime" name="arrivalTime" class="form-input flex-2 border-2 rounded" required><br>

           <!-- Origin Airport -->
<label for="origin" class="form-label">Origin:</label>
<select id="origin" name="origin" class="form-input" required>
    <c:forEach var="airport" items="${airports}">
        <option value="${airport.airportId}">${airport.airportName}</option>
    </c:forEach>
</select><br><br>

<!-- Destination Airport -->
<label for="destination" class="form-label">Destination:</label>
<select id="destination" name="destination" class="form-input" required>
    <c:forEach var="airport" items="${airports}">
        <option value="${airport.airportId}">${airport.airportName}</option>
    </c:forEach>
</select><br>
                <!-- Status -->
                    <label for="status" class="form-label">Status:</label>
                    <select id="status" name="status" class="form-input flex-2 border-2 rounded">
                        <option value="SCHEDULED">Scheduled</option>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="COMPLETED">Completed</option>
                        <option value="CANCELLED">Cancelled</option>
                        <option value="DELAYED">Delayed</option>
                        <option value="ON_TIME">On-Time</option>
                    </select><br>

                <!-- Submit Button -->
                    <button type="submit" class="btn success">Submit</button>
                                        </fieldset>
                    
            </form>
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
                    
                    <!-- Origin -->
                    <td>
                        <c:forEach var="airport" items="${airports}">
                            <c:if test="${airport.airportId == item.origin}">
                                ${airport.airportName}
                            </c:if>
                        </c:forEach>
                    </td>
                    
                    <!-- Destination -->
                    <td>
                        <c:forEach var="airport" items="${airports}">
                            <c:if test="${airport.airportId == item.destination}">
                                ${airport.airportName}
                            </c:if>
                        </c:forEach>
                    </td>
                    
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