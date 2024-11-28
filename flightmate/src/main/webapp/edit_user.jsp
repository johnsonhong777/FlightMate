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

            <h1 class="center">Edit User</h1>

<form action="${pageContext.request.contextPath}/admin-dashboard" method="post">
    <input type="hidden" name="action" value="save" />
    <input type="hidden" name="user_id" value="${user.userId}" />
    
    <label for="first_name">First Name:</label>
    <input type="text" id="first_name" name="first_name" value="${user.firstName}" required />

    <label for="last_name">Last Name:</label>
    <input type="text" id="last_name" name="last_name" value="${user.lastName}" required />

    <label for="email_address">Email Address:</label>
    <input type="email" id="email_address" name="email_address" value="${user.email}" required />

    <label for="role_id">Role:</label>
<select id="role_id" name="role_id">
    <c:forEach var="role" items="${roles}">
        <option value="${role.roleId}" <c:if test="${role.roleId == user.roleId}">selected</c:if>>
            ${role.roleKey}
        </option>
    </c:forEach>
</select>





    <button type="submit" class="btn">Save</button>
</form>


</body>
</html>