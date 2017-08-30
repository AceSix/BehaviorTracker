package com.kotomi.sale.controller;

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
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/7/22
 * @Modified By:
 */
@WebServlet("/shopinfo")
public class ShopInfoServlet  extends HttpServlet {
    private ShopLocationService shopLocationService;
    @Override
    public void init()throws ServletException {
        shopLocationService =new ShopLocationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        String shops = req.getParameter("shops");
        System.out.println( shops );
        //sql文件的注意大小写
        List<Map<String,Object>> shopLocationList = shopLocationService.getAllLocation("sql/shopinfo");

        JSONArray jsonList = JSONArray.fromObject(shopLocationList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }
}
