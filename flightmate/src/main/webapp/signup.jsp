<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>FlightMate | Sign Up</title>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
</head>
<body class="background">
	<main>
		<header>
			<h1 class="center">Be Part of the FlightMate Crew</h1>
		</header>
		<form class="login-form" method="POST" action="signup">
			<label for="email" class="login-form-label">Email:</label>
			<input id="email" type="email" name="email" placeholder="johnsmith@email.com" class="login-form-input" pattern="\S+@\S+\.\S+" required />
			<fieldset class="mt-2">
				<label for="firstname" class="login-form-label">First Name: 
					<input id="firstname" type="text" name="firstname" placeholder="John" class="login-form-input" required />
				</label>
				<label for="lastname" class="login-form-label">Last Name:
					<input id="lastname" type="text" name="lastname" placeholder="Smith" class="login-form-input" required />
				</label>
			</fieldset>
			
			<p class="mt-2">Are you a...</p>
			<fieldset class="mt-2">
				<label><input id="radio_btn_pilot" type="radio" name="role" value="PILOT" required /> Pilot</label>
				<label><input id="radio_btn_admin" type="radio" name="role" value="ADMINISTRATOR" required /> Administrator</label>
			</fieldset> 
			<label for="password" class="login-form-label mt-2">Password:</label>
			<input id="password" type="password" name="password" placeholder="Enter your password" class="login-form-input" required />
			<input id="submit" type="submit" name="submit" value="Sign Up" class="login-form-btn mt-2"/>
		</form>
		<p class="mt-2 center">Already have an account? <a href="./login" class="login-link">Login Now</a></p>
		<p class="message-error mt-2">${message}</p>
		<p></p>
	</main>
	
	<footer class="center">
		<p>Created by Biqi, Dennis, Emily, Gabriel &amp; Jianxiang</p>
		<p>for CST8319 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>