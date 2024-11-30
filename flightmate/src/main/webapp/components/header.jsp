<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<header class="dashboard-header">
		<h2><a href="${pageContext.request.contextPath}/dashboard">FlightMate</a></h2>
		<p class="welcome-msg">Welcome ${user.getFirstName()}!</p>
		<nav>
			<c:if test="${fn:contains(pageContext.request.requestURI, '/settings')}">
					<a href='${pageContext.request.contextPath}/dashboard' class='nav-btn'>Dashboard</a>
			</c:if>
			<c:if test="${!fn:contains(pageContext.request.requestURI, '/settings')}">
					<a href='${pageContext.request.contextPath}/settings' class='nav-btn'>Settings</a>
			</c:if>
			<a href="${pageContext.request.contextPath}/logout" class="nav-btn">Logout</a>
		</nav>
	</header>