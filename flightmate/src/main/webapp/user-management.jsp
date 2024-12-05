<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>FlightMate | User Management</title>
</head>
<body class="background">
    <jsp:include page='./components/header.jsp' />
    <main>
		<section class="container mt-2">
			<h2 class="section-title center">User Management</h2>
            <table class="dashboard-table w-full border-2 rounded mt-2">
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
		                        <div class="flex">
		                            <a href="dashboard?action=edit&id=${user.userId}" class="btn">Edit</a>
		                            <a href="dashboard?action=delete&id=${user.userId}" class="btn error ml-2"
		                               onclick="return confirm('Are you sure you want to delete this user?');">
		                                Delete
		                            </a>
		                        </div>
		                    </td>
		                </tr>
	                </c:forEach>
            	</tbody>
        	</table>
		</section>
		<section class="container mt-2">
			<h2 class="section-title center">User Feedback</h2>
            <table class="dashboard-table w-full border-2 rounded mt-2">
                <thead>
                    <tr>
                        <th>Feedback ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Feedback Type</th>
                        <th>Date</th>
                        <th>Comment</th>
                    </tr>
                </thead>
                <tbody>
	                <c:forEach var="feedbackItem" items="${feedback}">
		                <tr>
		                    <td>${feedbackItem.getFeedbackId()}</td>
		                    <td>${feedbackItem.getUser().getFirstName()} ${feedbackItem.getUser().getLastName()}</td>
		                    <td>${feedbackItem.getUser().getEmail()}</td>
		                    <td>${feedbackItem.getFeedbackType()}</td>
		                    <td>${feedbackItem.getFeedbackDate()}</td>
		                    <td>${feedbackItem.getFeedbackComment()}</td>
		                </tr>
	                </c:forEach>
            	</tbody>
        	</table>
		</section>
	</main>
</body>
</html>