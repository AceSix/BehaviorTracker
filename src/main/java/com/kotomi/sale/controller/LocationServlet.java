package com.kotomi.sale.controller;


import com.kotomi.sale.model.Location;
import com.kotomi.sale.service.LocationService;
import com.kotomi.sale.service.ShopLocationService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by Kotomi on 2017/3/19.
 */
@WebServlet("/weahterlocation")
public class LocationServlet extends HttpServlet {
    private LocationService locationService;

    @Override
    public void init()throws ServletException {
        locationService=new LocationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        List<Location> locationList = locationService.getLocationBycity("\"北京\"");
        JSONArray jsonList = JSONArray.fromObject(locationList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }

}
