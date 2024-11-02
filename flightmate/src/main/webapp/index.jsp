<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Welcome to FlightMate</title>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
</head>
<body class="background">
	<main class="center">
		<h1>Welcome to FlightMate!</h1>
		<section class="flex space-evenly">
			<a href="./login" class="btn">Login</a>
			<a href="./signup" class="btn success">Sign Up</a>
		</section>
	</main>
	<footer class="center">
		<p>Created by Biqi, Dennis, Emily, Gabriel &amp; Jianxiang</p>
		<p>for CST8319 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>