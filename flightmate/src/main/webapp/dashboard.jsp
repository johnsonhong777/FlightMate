<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</head>
<body class="background">
    <jsp:include page='./components/header.jsp' />
    <main>
        <header class="mb-2">
        	<h1 class="subtitle">Dashboard</h1>
            <ul class="main-header">
                <li><a href="upload" class="btn">Upload Documents</a></li>
                <li><a href="airport" class="btn">See Airports</a></li>
                <c:if test="${user.getRole().toString() == 'ADMINISTRATOR'}">
                    <li><a href="aircraft" class="btn">Add New Aircraft</a></li>
                    <li><a href="user-management" class="btn">User Management</a></li>
                    <li><a href="flight" class="btn">Manage Flights</a></li>
                </c:if>
            </ul>
            <p class="success mt-2">${message}</p>
        </header>
                                   
		<c:if test="${user.getRole().equals(roles['PILOT'])}">
			<section class="container mt-2">
	            <h2 class="section-title center">Log Flight Hours</h2>
	            <form action="logFlightHours" method="post" class="my-2">
	            	<fieldset class="form-group mt-2">
		            	<label for="flight_date" class="form-label">Flight Date:</label>
		                <input type="date" id="flight_date" name="flight_date" class="form-input"required>
		                <label for="hours_flighted" class="form-label ">Hours Flown:</label>
		                <input type="number" id="hours_flighted" name="hours_flighted" step="0.01" class="form-input" required>
	            	</fieldset>
	                <label for="notes" class="form-label mt-2">Notes (optional):</label>
	                <textarea id="notes" name="notes"class="form-textarea"></textarea>
	                <button type="submit" class="form-btn mt-2">Log Flight Hours</button>
	            </form>
	            <h2 class="section-title center">Flight Statistics</h2>
                <div id="aa">&nbsp;</div>
               	
               	<div class="flex">
	                <div id="flightPieChart" class="flex-1"></div>
	                <div id="airportChart" class="flex-1"></div>
                </div>
                <h2 class="section-title center">Flights</h2>
				<div class="flightTableData mt-2">
	                    <table class="dashboard-table w-full border-2 rounded mt-2">
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
			</section>
			<a href="./dashboard?feedback=true" class="feedback-btn"><img src="${pageContext.request.contextPath}/assets/images/feedback-icon.png" alt="submit feedback" width="24" height="24" /></a>
        </c:if>
		
        <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
	        <section class="container">
	            <h2 class="section-title center">Pending Flight Hour Approvals</h2>
	            <table class="dashboard-table w-full border-2 rounded mt-2">
	                <thead>
	                    <tr>
	                        <th>Pilot Id</th> <!-- Fix this so it retrieves the pilot name instead -->
	                        <th>Flight Date</th>
	                        <th>Hours</th>
	                        <th>Notes</th>
	                        <th>Action</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach var="hour" items="${pendingFlightHours}">
	                        <tr>
	                            <td>${hour.pilotId}</td><!-- Fix this so it retrieves the pilot name instead -->
	                            <td>${hour.flightDate}</td>
	                            <td>${hour.hoursFlighted}</td>
	                            <td>${hour.notes}</td>
	                            <td>
	                                <form action="approveFlightHours" method="post">
	                                    <input type="hidden" name="id" value="${hour.id}" />
	                                    <button type="submit" name="action" value="approve" class="btn">Approve</button>
	                                    <button type="submit" name="action" value="reject"  class="btn error ml-2">Reject</button>
	                                </form>
	                            </td>
	                        </tr>
	                    </c:forEach>
	                    <c:if test="${pendingFlightHours.isEmpty()}">
	                    	<tr><td>No pending flights!</td></tr>
	                    </c:if>
	                </tbody>
	            </table>
	        </section>
        </c:if>
        <c:if test="${not empty param.feedback}">
	        <aside class="container mt-auto w-fit ml-6 border-3">
		        <header>
			        <h2 class="subtitle">Feedback Form</h2>
			        <button onClick="history.back()" class="feedback-close-btn">x</button>
		        </header>
		        <form action="submitFeedback" method="POST">
		            <label class="form-label">Name: <input type="text" id="name" name="name" value="${user.getFirstName()} ${user.getLastName()}" class="w-full form-input" disabled></label>
		            <label class="form-label">Email: <input type="text" id="email" name="email" value="${user.getEmail()}" class="w-full form-input" disabled></label>
		            
		
		            <label for="feedback_type" class="form-label">Feedback Type: 
			            <select id="feedback_type" name="feedback_type" required class="form-single-select w-full">
			            	<option value="" disabled>Select one:</option>
			            	<option value="GENERAL">General Feedback</option>
			            	<option value="DENIAL_EXPLANATION">Request explanation for denial</option>
			            </select>
		            </label>
		            <label for="feedback_comment" class="form-label">Comment:</label>
		            <textarea id="feedback_comment" name="feedback_comment" rows="4" cols="50" class="form-textarea" required></textarea>
		
		            <button type="submit" class="form-btn mt-2">Submit</button>
		        </form>
	    	</aside>
    	</c:if>
    </main>
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
</body>
</html>
