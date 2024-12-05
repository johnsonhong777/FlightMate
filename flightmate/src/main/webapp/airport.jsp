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
		    <fieldset class="form-group mt-2">
		        <label for="airportName" class="form-label">Airport Name:</label>
		        <input type="text" id="airportName" name="airportName" class="form-input" required><br>
		
		        <label for="airportCode" class="form-label">Airport Code (3-letter):</label>
		        <input type="text" id="airportCode" name="airportCode" maxlength="3" class="form-input" required><br>
			</fieldset>
			<fieldset class="form-group mt-2">
		        <label for="city" class="form-label">City:</label>
		        <input type="text" id="city" name="city" class="form-input" required><br>
		
		        <label for="country" class="form-label">Country:</label>
		        <input type="text" id="country" name="country" class="form-input" required><br>
		        
		        <label for="runways" class="form-label">Number of Runways:</label>
	        	<input type="number" id="runways" name="runways" min="1" class="form-input" required><br>
			</fieldset>
	        
	        <button type="submit" class="form-btn mt-2">Add Airport</button>
	    </form>
	    </section>
    </c:if>
    <h2 class="section-title mt-2">List of Airports</h2>
    <table class="dashboard-table w-full border-2 rounded mt-2">
    	<thead>
	        <tr>
	            <th>ID</th>
	            <th>Airport Name</th>
	            <th>Code</th>
	            <th>City</th>
	            <th>Country</th>
	            <th>Runways</th>
	            <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
	            	<th>Actions</th>
	            </c:if>
	        </tr>
        </thead>
        <tbody>
	        <c:forEach var="airport" items="${airports}">	
		        <tr>
			        <form method="post" action="UpdateDeleteAirportServlet">
			            <td>${airport.getAirportId()}<input type="hidden" name="id" value="${airport.getAirportId()}" ${disabledInput}/></td>
			            <td><input type="text" class="form-input w-full" name="airport_name" value="${airport.getAirportName()}" ${disabledInput}/></td>
			            <td><input type="text" class="form-input w-full" name="airport_code" value="${airport.getAirportCode()}" ${disabledInput}/></td>
			            <td><input type="text" class="form-input w-full" name="city" value="${airport.getCity()}" ${disabledInput}/></td>
			            <td><input type="text" class="form-input w-full" name="country" value="${airport.getCountry()}" ${disabledInput}/></td>
			            <td><input type="number" class="form-input w-full" name="runways" value="${airport.getRunways()}" ${disabledInput}/></td>
			            <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
				            <td>
				                <input type="submit" name="action" value="Update" class="btn w-full" />
				                <input type="submit" name="action" value="Delete" class="btn error w-full mt-2" onclick="return confirm('Are you sure you want to delete this airport?')" />
				            </td>
			            </c:if>
			        </form>
		        </tr>
	        </c:forEach>
        </tbody>
    </table>
    </main>
</body>
</html>