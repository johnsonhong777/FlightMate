<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Flight Status:</title>
</head>
<body>
<form action="updateFlightStatus" method="post">
     <input type="hidden" name="flightId" value="${flight.id}">
     <button type="submit" name="status" value="Approved">Approve</button>
     <button type="submit" name="status" value="Rejected">Reject</button>
</form>
	<footer class="center">
		<p>Created by Biqi, Dennis, Emily, Gabriel &amp; Jianxiang</p>
		<p>for CST8319 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>