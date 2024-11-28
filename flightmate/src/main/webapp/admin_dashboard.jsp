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
            <h1>Admin Dashboard</h1>

            <!-- Add Aircraft Button -->
            <div class="my-2 center">
                <a href="add-aircraft" class="btn success">Add New Aircraft</a>
            </div>

            <!-- User Management Section -->
            <h2>User Management</h2>
            <table class="dashboard_table w-full border-2 rounded">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.firstName} ${user.lastName}</td>
                            <td>${user.email}</td>
                            <td>${user.role}</td>
                            <td>
                                <div class=" flex">
                                    <a href="admin-dashboard?action=edit&id=${user.userId}" class="btn">Edit</a>
                                    <a href="admin-dashboard?action=delete&id=${user.userId}" class="btn error"
                                        onclick="return confirm('Are you sure you want to delete this user?');">
                                        Delete
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            

            <!-- Flight Management Section -->
            <h2>Flight Management</h2>
            <table class="dashboard_table w-full border-2 rounded">
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
                                    <div class="flex space-evenly">
                                    <a href="approve-flight?id=${flight.flightId}" class="btn success">Approve</a>
                                    <a href="reject-flight?id=${flight.flightId}" class="btn error">Reject</a>
                                    <a href="complete-flight?id=${flight.flightId}" class="btn">Mark Complete</a>
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