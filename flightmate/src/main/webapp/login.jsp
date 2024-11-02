<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>FlightMate | Login</title>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
</head>
<body class="background">
	<main>
		<header>
			<h1 class="center">Log In to FlightMate</h1>
		</header>
		<form class="login_form" method="POST" action="login">
			<label for="email" class="login_form_label">Email:</label>
			<input id="email" type="email" name="email" placeholder="Enter your email" pattern="\S+@\S+\.\S+" class="login_form_input" required />
			<label for="password" class="login_form_label mt-2">Password:</label>
			<input id="password" type="password" name="password" placeholder="Enter your password" class="login_form_input" required />
			<input id="submit" type="submit" name="submit" value="Log in" class="login_form_btn mt-2"/>
		</form>
		<p class="mt-2 center">Don't have an account? <a href="./signup" class="login_link">Sign Up for an Account</a></p>
		<p class="message_error mt-2">${message}</p>
	</main>
	
	<footer class="center">
		<p>Created by Biqi, Dennis, Emily, Gabriel &amp; Jianxiang</p>
		<p>for CST8319 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>