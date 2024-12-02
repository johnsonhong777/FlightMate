<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <c:if test="${user.getRole().equals(roles['PILOT'])}">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.2/dist/echarts.min.js"></script>
    </c:if>
	<title>FlightMate | Dashboard</title>
    <style>
        .container {
            display: inline-block;
            width: 100%;
            box-shadow:unset !important;
        }
<c:if test="${user.getRole().equals(roles['PILOT'])}">
        .dashboard-table {
            width: 100%;
            border-collapse: collapse;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }

        .dashboard-table th, .dashboard-table td {
            padding: 12px 15px;
            text-align: left;
            font-size: 14px;
        }

        .dashboard-table thead {
            background-color: #007BFF;
            color: white;
            font-weight: bold;
        }

        .dashboard-table th {
            text-transform: uppercase;
        }

        .dashboard-table tbody tr {
            border-bottom: 1px solid #ddd;
        }

        .dashboard-table tbody tr:hover {
            background-color: #f1f1f1;
        }

        .dashboard-table tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .dashboard-table tbody tr:nth-child(odd) {
            background-color: #fff;
        }

        .dashboard-table td {
            color: #333;
        }

        @media screen and (max-width: 768px) {
            .dashboard-table th, .dashboard-table td {
                padding: 10px;
            }

            .dashboard-table th {
                font-size: 12px;
            }

            .dashboard-table td {
                font-size: 12px;
            }
        }
</c:if>
    </style>
</head>
<body class="background">
	<jsp:include page='./components/header.jsp' />
	<main>
		<header class="mb-2">
			<ul class="main-header">
		        <li><a href="upload" class="btn">Upload Documents</a></li>
		        <li><a href="feedback" class="btn">Submit Feedback</a></li>
		        <li><a href="airport" class="btn">See Airports</a></li>
		        <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
			        <li><a href="aircraft" class="btn">Add New Aircraft</a></li>
			        <li><a href="flight" class="btn">Manage Flights</a></li>
		        </c:if>
	   		</ul>
		</header>
		<section class="container mt-2">		
			<h1 class="subtitle">Dashboard</h1>
			<p>Hello ${user.getFirstName()}</p>
			<p>Your role: ${user.getRole()}</p>
            <c:if test="${user.getRole().equals(roles['PILOT'])}">
                <div id="aa">&nbsp;</div>
                <div class="container">
                    <div id="flightPieChart" style="width:50%; height: 400px;float:left"></div>
                    <div id="airportChart" style="width:50%; height: 400px;float:right"></div>
                </div>
                <div class="flightTableData">
                    <h2>Flight</h2>
                    <table class="dashboard-table w-full border-2 rounded">
                        <thead>
                        <tr>
                            <th>Flight Number</th>
                            <th>Departure Time</th>
                            <th>Arrival Time</th>
                            <th>Origin</th>
                            <th>Destination</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${flights}">
                            <tr>
                                <td>${item.flightNumber}</td>
                                <td>${item.departureTime}</td>
                                <td>${item.arrivalTime}</td>
                                <td>${item.origin}</td>
                                <td>${item.destination}</td>
                                <td>${item.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
		</section>

        <%-- Only display the form for pilots --%>
        <c:if test="${sessionScope.user.role == 'PILOT'}">
            <h2>Log Flight Hours</h2>
            <form action="logFlightHours" method="post">
                <label for="flight_date">Flight Date:</label>
                <input type="date" id="flight_date" name="flight_date" required>
                <label for="hours_flighted">Hours Flown:</label>
                <input type="number" id="hours_flighted" name="hours_flighted" step="0.01" required>
                <label for="notes">Notes (optional):</label>
                <textarea id="notes" name="notes"></textarea>
                <button type="submit">Log Flight Hours</button>
            </form>
        </c:if>

        <c:if test="${sessionScope.user.role == 'ADMINISTRATOR'}">
            <h2>Pending Flight Hour Approvals</h2>
            <table>
                <thead>
                    <tr>
                        <th>Pilot Name</th>
                        <th>Flight Date</th>
                        <th>Hours</th>
                        <th>Notes</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="hour" items="${pendingFlightHours}">
                        <tr>
                            <td>${hour.pilotName}</td>
                            <td>${hour.flightDate}</td>
                            <td>${hour.hoursFlighted}</td>
                            <td>${hour.notes}</td>
                            <td>
                                <form action="approveFlightHours" method="post">
                                    <input type="hidden" name="id" value="${hour.id}" />
                                    <button type="submit" name="action" value="approve">Approve</button>
                                    <button type="submit" name="action" value="reject">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

		<c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
			<h2>User Management</h2>
            <table class="dashboard-table w-full border-2 rounded">
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
                                <div class=" flex">
                                    <a href="dashboard?action=edit&id=${user.userId}" class="btn">Edit</a>
                                    <a href="dashboard?action=delete&id=${user.userId}" class="btn error"
                                        onclick="return confirm('Are you sure you want to delete this user?');">
                                        Delete
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
	</main>
</body>
<c:if test="${user.getRole().equals(roles['PILOT'])}">
    <script>
        $(document).ready(function () {
            loadFlightData();
            loadAirportData();
        });
        // Flight status
        function loadFlightData(){
            $.ajax({
            url: '/flight/statistics',
            method: 'GET',
            dataType: 'json',
            success: function(statusData) {
                var flightPieChart = echarts.init(document.getElementById('flightPieChart'));
                var flightOption = {
                    title: {
                        text: 'Flight Status Distribution',
                        subtext: 'Flight Counts',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: statusData.map(function (item) {
                            return item.name;
                        })
                    },
                    series: [
                        {
                            name: 'Flight Status',
                            type: 'pie',
                            radius: '50%',
                            data: statusData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                flightPieChart.setOption(flightOption);
            },
            error: function(xhr, status, error) {
                console.error("Error fetching data: " + error);
            }
        });
        }

        // Airport
        function loadAirportData(){
            $.ajax({
            url: '/airport/statistics',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                var cities = [];
                var airportCounts = [];
                data.forEach(function (item) {
                    cities.push(item.name);
                    airportCounts.push(item.value);
                });
                var airportChart = echarts.init(document.getElementById('airportChart'));

                var airportOption = {
                    title: {
                        text: 'Airport Count by City'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    xAxis: {
                        type: 'category',
                        data: cities,
                        axisLabel: {
                            rotate: 45
                        }
                    },
                    yAxis: {
                        type: 'value',
                        name: 'Airport Count'
                    },
                    series: [{
                        data: airportCounts,
                        type: 'bar',
                        smooth: true,
                    }]
                };
                airportChart.setOption(airportOption);
            },
            error: function (xhr, status, error) {
                console.error('AJAX Error: ' + status + ' - ' + error);
            }
        });
        }
    </script>
</c:if>
</html>
