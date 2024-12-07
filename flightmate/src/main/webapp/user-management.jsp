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
			<h2 class="section-title">User Management</h2>
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
			<header>
				<h2 class="section-title center">User Feedback</h2>
				<form method="get" action="user-management" class="filter-form mb-2">
			        <label for="filterType" class="mr-2">Filter by:</label>
			        <select name="filterType" id="filterType" class="form-single-select mr-2">
			            <option value="all" ${param.filterType == 'all' ? 'selected' : ''}>All</option>
			            <option value="read" ${param.filterType == 'read' ? 'selected' : ''}>Read</option>
			            <option value="unread" ${param.filterType == 'unread' ? 'selected' : ''}>Unread</option>
			        </select>
			        <button type="submit" class="btn">Apply</button>
			    </form>
		    </header>
            <table class="dashboard-table w-full border-2 rounded mt-2">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Feedback Type</th>
                        <th>Comment</th>
                        <th>Read Status</th>
                    </tr>
                </thead>
                <tbody>
	                <c:forEach var="feedbackItem" items="${feedback}">
		                <tr class="${feedbackItem.hasRead() ? 'isRead' : 'isUnread' }">
		                    <td>${feedbackItem.getFeedbackDate()}</td>
		                    <td>${feedbackItem.getUser().getFirstName()} ${feedbackItem.getUser().getLastName()}</td>
		                    <td><a href="mailto:${feedbackItem.getUser().getEmail()}" target="_blank">${feedbackItem.getUser().getEmail()}</a></td>
		                    <td>${feedbackItem.getFeedbackType()}</td>
		                    <td>${feedbackItem.getFeedbackComment()}</td>
		                    <td><a href="feedback-read?id=${feedbackItem.getFeedbackId()}" class="btn">${feedbackItem.hasRead() ? "Mark as Unread" : "Mark as Read"}</a></td>
		                </tr>
	                </c:forEach>
            	</tbody>
        	</table>
		</section>
	</main>
</body>
</html>