<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>Edit Flight | FlightMate</title>
</head>
<body class="background">
    <jsp:include page='./components/header.jsp' />
    <main class=>
        <h1 class="center">Edit Flight</h1>

        <form action="${pageContext.request.contextPath}/flight-management" method="post" class="form">
            <input type="hidden" name="action" value="save" />
            <input type="hidden" name="flight_id" value="${flight.flightId}" />

            <label for="flight_number">Flight Number:</label>
            <input type="text" id="flight_number" name="flight_number" value="${flight.flightNumber}" required />

            <label for="origin">Origin:</label>
            <input type="text" id="origin" name="origin" value="${flight.origin}" required />

            <label for="destination">Destination:</label>
            <input type="text" id="destination" name="destination" value="${flight.destination}" required />

            <label for="status">Status:</label>
            <select id="status" name="status">
                <option value="Scheduled" <c:if test="${flight.status == 'Scheduled'}">selected</c:if>>Scheduled</option>
                <option value="In Progress" <c:if test="${flight.status == 'In Progress'}">selected</c:if>>In Progress</option>
                <option value="Completed" <c:if test="${flight.status == 'Completed'}">selected</c:if>>Completed</option>
                <option value="Cancelled" <c:if test="${flight.status == 'Cancelled'}">selected</c:if>>Cancelled</option>
            </select>

            <label for="departure_time">Departure Time:</label>
            <input type="datetime-local" id="departure_time" name="departure_time" value="${flight.departureTime}" required />

            <label for="arrival_time">Arrival Time:</label>
            <input type="datetime-local" id="arrival_time" name="arrival_time" value="${flight.arrivalTime}" required />

            <button type="submit" class="btn">Save</button>
            <a href="${pageContext.request.contextPath}/flight-management" class="btn error">Cancel</a>
        </form>
    </main>
</body>
</html>

