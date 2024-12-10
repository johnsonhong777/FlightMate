<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>FlightMate | Aircrafts</title>
</head>

<body class="background">
    <jsp:include page='./components/header.jsp' />

 <main>
        <div class="container mx-auto mt-8 w-72 rounded border-1 bg-white">
            <h1 class="center">Add New Flight</h1>
            
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
            
            <form action="${pageContext.request.contextPath}/flight-management" method="post" class="my-2">
                <input type="hidden" name="action" value="save">
                
                <div class="form-group space-evenly">
                    <label for="flightNumber" class="form-label">Flight Number:</label>
                    <input type="text" id="flightNumber" name="flightNumber" class="form-input flex-2 border-2 rounded" required>
                </div>

                <div class="form-group space-evenly">
                    <label for="departureTime" class="form-label">Departure Time:</label>
                    <input type="datetime-local" id="departureTime" name="departureTime" class="form-input flex-2 border-2 rounded" required>
                </div>

                <div class="form-group space-evenly">
                    <label for="arrivalTime" class="form-label">Arrival Time:</label>
                    <input type="datetime-local" id="arrivalTime" name="arrivalTime" class="form-input flex-2 border-2 rounded" required>
                </div>

                <div class="form-group space-evenly">
                    <label for="origin" class="form-label">Origin:</label>
<select id="origin" name="origin" class="form-input flex-2 border-2 rounded" required>
    <option value="">Select Origin</option>
    <c:forEach var="airport" items="${airports}">
        <option value="${airport.code}">${airport.name}</option>
    </c:forEach>
</select>                </div>

                <div class="form-group space-evenly">
                    <label for="destination" class="form-label">Destination:</label>
<select id="destination" name="destination" class="form-input flex-2 border-2 rounded" required>
    <option value="">Select Destination</option>
    <c:forEach var="airport" items="${airports}">
        <option value="${airport.code}">${airport.name}</option>
    </c:forEach>
</select>                </div>

               <div class="form-group space-evenly">
                    <label for="pilots" class="form-label">Assign Pilots:</label>
                    <select id="pilots" name="pilots" class="form-input border-2 rounded" multiple>
                        <c:forEach var="pilot" items="${pilots}">
                            <option value="${pilot.getUserId()}">${pilot.firstName} ${pilot.lastName}</option>
                        </c:forEach>
                    </select>
                </div> 

                <div class="form-group space-evenly">
                    <label for="status" class="form-label">Status:</label>
                    <select id="status" name="status" class="form-input border-2 rounded">
       <option value="SCHEDULED">Scheduled</option>
<option value="IN_PROGRESS">In Progress</option>
<option value="COMPLETED">Completed</option>
<option value="CANCELLED">Cancelled</option>
<option value="DELAYED">Delayed</option>
<option value="ON_TIME">On-Time</option>
                    </select>
                </div>

                <div class="center my-2">
                    <button type="submit" class="btn success">Submit</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>