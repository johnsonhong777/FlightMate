<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	  			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>

<body class="background">
    <jsp:include page='./components/header.jsp' />

    <main>
        <div class="container mx-auto mt-8 w-72 rounded border-1 bg-white">
            <h1 class="center">Add New Aircraft</h1>
            
            <c:if test="${not empty success}">
                <div class="alert success center">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error center">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/add-aircraft" method="post" class="my-2">
                <div class="form_group space-evenly">
                    <label for="model" class="form_label">Aircraft Model:</label>
                    <input type="text" id="model" name="model" class="form_input flex-2 border-2 rounded" required>
                </div>

                <div class="form_group space-evenly ">
                    <label for="airport" class="form_label">Assign to Airport:</label>
                    <select id="airport" name="airport" class="form_input border-2 rounded">
                        <c:forEach var="airport" items="${airports}">
                            <option value="${airport.airportId}">${airport.name}</option>
                        </c:forEach>
                    </select>
                    <!-- Button to Add Airport -->
                    <a href="${pageContext.request.contextPath}/add-airport" class="btn ml-2">Add Airport</a>
                </div>

                <div class="form_group space-evenly">
                    <label for="details" class="form_label">Aircraft Details:</label>
                    <textarea id="details" name="details" class="form_input flex-2 border-2 rounded"></textarea>
                </div>

                <div class="center my-2">
                    <button type="submit" class="btn success">Submit</button>
                </div>
            </form>
            
            <!-- Button to return to Admin Dashboard -->
            <div class="center my-2">
                <a href="${pageContext.request.contextPath}/admin-dashboard" class="btn">Return to Admin Dashboard</a>
            </div>
        </div>
    </main>
</body>
</html>
