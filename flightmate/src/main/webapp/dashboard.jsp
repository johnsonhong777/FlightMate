<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>FlightMate | Dashboard</title>
</head>
<body class="background">
	<jsp:include page='./components/header.jsp' />
	<main>
		<header class="mb-2">
			<ul class="main-header">
		        <li><a href="flight.jsp" class="btn">Manage Flights</a></li>
		        <li><a href="upload.jsp" class="btn">Upload Documents</a></li>
		        <li><a href="feedback.jsp" class="btn">Submit Feedback</a></li>
		        <li><a href="airport.jsp" class="btn">See Airports</a></li>
	   		</ul>
		</header>
		<section class="container mt-2">		
			<h1 class="subtitle">Dashboard</h1>
			<p>Hello ${user.getFirstName()}</p>
			<p>Your role: ${user.getRole().toString()}</p>
		</section>
	</main>
</body>
</html>
