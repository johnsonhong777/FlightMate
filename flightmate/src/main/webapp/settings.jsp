<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>FlightMate | Settings</title>
</head>
<body class="background">
	<jsp:include page='./components/header.jsp' />
	<main>
		<header class="main-header">
			<h1 class="subtitle">Settings</h1>
		</header>
		<div class="flex gap-8 w-72">
			<section class="container mt-2 flex-2">
				<h2 class="subtitle">Your information</h2>
				<form action="settings" method="POST">
					<input type="hidden" name="action_info" value="${action_info.isBlank() ? 'save' : 'update'}">
					<div class="flex my-2">
						<label for="fname" class="form-label flex-1">First Name:</label><input type="text" id="fname" name="firstname" class="form-input w-full flex-2" value="${user.getFirstName()}" ${action_info} >
						
					</div>
					<div class="flex my-2">
						<label for="lname" class="form-label flex-1">Last Name: </label><input type="text" id="lname" name="lastname" class="form-input w-full flex-2" value="${user.getLastName()}" ${action_info}>
					</div>
					<div class="flex my-2">
						<label for="email" class="form-label flex-1">Email address: </label><input type="email" id="email" name="email" class="form-input w-full flex-2" value="${user.getEmail()}" ${action_info}>
					</div>
					<c:if test="${action_info.isBlank()}">
						<input type="submit" name="submit" value="Save" class="btn">
						<input type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/settings'" class="btn ml-2 cancel">
					</c:if>
					<c:if test="${action_info.equals('disabled')}">
						<input type="submit" name="submit" value="Update Info" class="btn">
					</c:if>
				</form>
				<form action="settings" method="POST">
					<input type="hidden" name="action_pass" value="${action_pass.isBlank() ? 'save' : 'update'}">
					<div class="flex my-2">
						<label for="password" class="form-label flex-1">Current Password: </label><input type="password" id="password" name="password" class="form-input w-full flex-2" value="placeholder" ${action_pass}>
					</div>
					<c:if test="${action_pass.isBlank()}">
						<div class="flex my-2">
							<label for="new_password" class="form-label flex-1">New Password: </label><input type="password" id="new_password" name="new_password" class="form-input w-full flex-2">
						</div>
						<div class="flex my-2">
							<label for="confirm_new" class="form-label flex-1">Confirm New Password: </label><input type="password" id="confirm_new" name="confirm_new" class="form-input w-full flex-2">
						</div>
						<input type="submit" name="submit" value="Save" class="btn">
						<input type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/settings'" class="btn ml-2 cancel">
					</c:if>
					<c:if test="${action_pass.equals('disabled')}">
						<input type="submit" name="submit" value="Update Password" class="btn">
					</c:if>
				</form>
				<p class="message-error mt-2">${message}</p>
				<p class="message-success mt-2">${success}</p>
			</section>

			
		</div>
	</main>
</body>
</html>