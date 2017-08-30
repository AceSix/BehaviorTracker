package com.kotomi.sale.controller;

import com.kotomi.sale.service.Z_ShopLocationService;
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
 * Created by Kotomi on 2017/3/20.
 */
@WebServlet("/shop")
public class ZShopLocationServlet extends HttpServlet{
    private Z_ShopLocationService ZShopLocationService;
    @Override
    public void init()throws ServletException {
        ZShopLocationService =new Z_ShopLocationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        List<Map<String,Object>> shopLocationList = ZShopLocationService.getShopLocation("sql/ADshoplocation_get.sql");
        JSONArray jsonList = JSONArray.fromObject(shopLocationList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }
}
