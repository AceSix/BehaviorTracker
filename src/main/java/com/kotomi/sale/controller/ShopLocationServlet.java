package com.kotomi.sale.controller;

import com.kotomi.sale.service.ShopLocationService;
import net.sf.json.JSONArray;
import org.apache.poi.util.LittleEndianByteArrayInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
@WebServlet("/shoplocation")
public class ShopLocationServlet extends HttpServlet {
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
        String shopid = req.getParameter("shopid");
        List<Map<String, Object>> shopLocationList =new ArrayList<>();
        if(shopid==null) {
            //sql文件的注意大小写
             shopLocationList = shopLocationService.getAllLocation("sql/shoplocation_getall");
        }else{
            shopLocationList = shopLocationService.getCompetitiorLocation(shopid);
        }

        JSONArray jsonList = JSONArray.fromObject(shopLocationList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }



}
