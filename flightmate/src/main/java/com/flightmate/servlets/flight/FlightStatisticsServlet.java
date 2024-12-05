package com.flightmate.servlets.flight;

import com.flightmate.dao.FlightDao;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/flight/statistics")
public class FlightStatisticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Integer> result = FlightDao.getDao().getFlightStatusCount();
        List<Map<String,Object>> list=new ArrayList<>();
        for (String key : result.keySet()) {
            Map<String,Object> item=new HashMap<>();
            item.put("name",key);
            item.put("value",result.get(key));
            list.add(item);
        }
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(list));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
