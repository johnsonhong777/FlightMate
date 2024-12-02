package com.flightmate.servlets;

import com.flightmate.dao.AirportDao;
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

@WebServlet("/airport/statistics")
public class AirportStatisticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Long> result = AirportDao.getDao().getAirportCountByCity();
        List<Map<String, Object>> list = new ArrayList<>();
        for (String key : result.keySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", key);
            item.put("value", result.get(key));
            list.add(item);
        }
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(list));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
