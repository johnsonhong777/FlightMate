<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>    
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>FlightMate | List of Airports</title>
</head>
<body class="background">
	<jsp:include page='./components/header.jsp' />
	<main class="center">
    <h1>Airport Management</h1>
    <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
	    <section class="container">
	    <h2 class="section-title">Add an Airport</h2>
	    <form action="airport" method="POST">
	        <label for="airportName" class="form-label">Airport Name:</label>
	        <input type="text" id="airportName" name="airportName" class="form-input" required><br>
	
	        <label for="airportCode" class="form-label">Airport Code (3-letter):</label>
	        <input type="text" id="airportCode" name="airportCode" maxlength="3" class="form-input" required><br>
	
	        <label for="city" class="form-label">City:</label>
	        <input type="text" id="city" name="city" class="form-input" required><br>
	
	        <label for="country" class="form-label">Country:</label>
	        <input type="text" id="country" name="country" class="form-input" required><br>
	
	        <label for="runways" class="form-label">Number of Runways:</label>
	        <input type="number" id="runways" name="runways" min="1" class="form-input" required><br>
	
	        <button type="submit" class="form-btn">Add Airport</button>
	    </form>
	    </section>
    </c:if>
    <h2 class="section-title mt-2">List of Airports</h2>
    <table class="temptable">
        <tr>
            <th>ID</th>
            <th>Airport Name</th>
            <th>Code</th>
            <th>City</th>
            <th>Country</th>
            <th>Runways</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="airport" items="${airports}">	
	        <tr>
		        <form method="post" action="UpdateDeleteAirportServlet">
		            <td>${airport.getAirportId()}<input type="hidden" name="id" value="${airport.getAirportId()}" /></td>
		            <td><input type="text" name="airport_name" value="${airport.getAirportName()}" /></td>
		            <td><input type="text" name="airport_code" value="${airport.getAirportCode()}" /></td>
		            <td><input type="text" name="city" value="${airport.getCity()}" /></td>
		            <td><input type="text" name="country" value="${airport.getCountry()}" /></td>
		            <td><input type="number" name="runways" value="${airport.getRunways()}" /></td>
		            <td>
		                <input type="submit" name="action" value="Update" />
		                <input type="submit" name="action" value="Delete" onclick="return confirm('Are you sure you want to delete this airport?')" />
		            </td>
		        </form>
	        </tr>
        </c:forEach>
    </table>
    </main>
</body>
</html>