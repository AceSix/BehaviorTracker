package com.kotomi.sale.controller;

import com.kotomi.sale.model.ShopLocation;
import com.kotomi.sale.service.ShopInfoService;
import com.kotomi.sale.service.ShopLocationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
    private ShopInfoService shopInfoService;
    @Override
    public void init()throws ServletException {
        shopInfoService = new ShopInfoService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        String shopid = req.getParameter("shopid");
        //sql文件的注意大小写
        List<Map<String,Object>> shopInfoList =shopInfoService.getShopInfo(shopid);
        if(shopInfoList.size() != 1) return;
        Map<String,Object> shopinfo = shopInfoList.get(0);
        JSONObject jsonObject = JSONObject.fromObject(shopinfo);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }
}
