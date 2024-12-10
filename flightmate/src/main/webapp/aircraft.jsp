<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>FlightMate | Aircrafts</title>
</head>

<body class="background">
    <jsp:include page='./components/header.jsp' />

    <main class="center">
        <h1>Aircraft Management</h1>
        <!-- Add Aircraft Form (Visible only for Admin) -->
        <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
            <section class="container">
                <h2 class="section-title">Add an Aircraft</h2>
                <form action="${pageContext.request.contextPath}/aircraft" method="POST">
                    <fieldset class="form-group mt-2">
                        <label for="aircraftModel" class="form-label">Aircraft Model:</label>
                        <input type="text" id="aircraftModel" name="aircraftModel" class="form-input" required><br>

                        <label for="manufactureDate" class="form-label">Manufacture Date:</label>
                        <input type="date" id="manufactureDate" name="manufactureDate" class="form-input" required><br>

                        <label for="lastMaintenanceDate" class="form-label">Last Maintenance Date:</label>
                        <input type="date" id="lastMaintenanceDate" name="lastMaintenanceDate" class="form-input" required><br>

                        <label for="nextMaintenanceDate" class="form-label">Next Maintenance Date:</label>
                        <input type="date" id="nextMaintenanceDate" name="nextMaintenanceDate" class="form-input" required><br>

                        <label for="aircraftNotes" class="form-label">Aircraft Notes:</label>
                        <textarea id="aircraftNotes" name="aircraftNotes" class="form-input"></textarea><br>

                        <label for="administratorId" class="form-label">Administrator:</label>
                        <select id="administratorId" name="administratorId" class="form-input" required>
                            <c:forEach var="admin" items="${administrators}">
                                <option value="${admin.getUserId()}">${admin.getFirstName()} ${admin.getLastName()}</option>
                            </c:forEach>
                        </select><br>

                        <label for="airportId" class="form-label">Airport:</label>
                        <select id="airportId" name="airportId" class="form-input" required>
                            <c:forEach var="airport" items="${airports}">
                                <option value="${airport.getAirportId()}">${airport.getAirportName()}</option>
                            </c:forEach>
                        </select><br>

                        <button type="submit" class="form-btn">Add Aircraft</button>
                    </fieldset>
                </form>
            </section>
        </c:if>

        <h2 class="section-title mt-2">List of Aircraft</h2>
        <table class="dashboard-table w-full border-2 rounded mt-2">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Aircraft Model</th>
                    <th>Manufacture Date</th>
                    <th>Last Maintenance Date</th>
                    <th>Next Maintenance Date</th>
                    <th>Notes</th>
                    <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
                        <th>Actions</th>
                    </c:if>
                </tr>
            </thead>
           <tbody>
    <c:forEach var="aircraft" items="${aircrafts}">
        <!-- Wrap each row in a form to handle individual submissions for Update/Delete -->
        <form action="${pageContext.request.contextPath}/UpdateDeleteAircraftServlet" method="POST">
            <tr>
                <td>
                    ${aircraft.getAircraftId()}
                    <input type="hidden" name="id" value="${aircraft.getAircraftId()}" />
                </td>
                <td>
                    <input type="text" class="form-input w-full" name="aircraft_model" value="${aircraft.getAircraftModel()}" />
                </td>
                <td>
                    <input type="date" class="form-input w-full" name="manufacture_date" value="${aircraft.getManufactureDate()}" />
                </td>
                <td>
                    <input type="date" class="form-input w-full" name="last_maintenance_date" value="${aircraft.getLastMaintenanceDate()}" />
                </td>
                <td>
                    <input type="date" class="form-input w-full" name="next_maintenance_date" value="${aircraft.getNextMaintenanceDate()}" />
                </td>
                <td>
                    <textarea class="form-input w-full" name="aircraft_notes">${aircraft.getAircraftNotes()}</textarea>
                </td>
                <td>
                <label for="airportId" class="form-label">Airport:</label>
   <select id="airportId" name="airportId" class="form-input border-2 rounded" required>
    <c:forEach var="airport" items="${airports}">
        <option value="${airport.getAirportId()}">${airport.getAirportName()}</option>
    </c:forEach>
</select>
                  </td>      
                <td>
                    <label for="administratorId" class="form-label">Administrator:</label>
<select id="administratorId" name="administratorId" class="form-input border-2 rounded" required>
    <c:forEach var="admin" items="${administrators}">
        <option value="${admin.getUserId()}">${admin.getFirstName()} ${admin.getLastName()}</option>
    </c:forEach>
</select>
                </td>
                <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
                    <td>
                        <input type="submit" name="action" value="Update" class="btn w-full" />
                        <input type="submit" name="action" value="Delete" class="btn error w-full mt-2" onclick="return confirm('Are you sure you want to delete this aircraft?')" />
                    </td>
                </c:if>
            </tr>
        </form>
    </c:forEach>
</tbody>
        </table>
    </main>
</body>
</html>