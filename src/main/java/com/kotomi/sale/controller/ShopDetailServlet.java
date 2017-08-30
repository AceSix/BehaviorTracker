package com.kotomi.sale.controller;

import com.kotomi.sale.service.ShopCommentService;
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
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
@WebServlet("/shopdetail")
public class ShopDetailServlet extends HttpServlet {
    private ShopCommentService shopCommentService;
    @Override
    public void init()throws ServletException {
        shopCommentService =new ShopCommentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        String shopid = req.getParameter("shopid");
        //sql文件的注意大小写
        List<Map<String,Object>> shopTimeList = shopCommentService.getShopTime( shopid );

        JSONArray jsonList = JSONArray.fromObject(shopTimeList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }



}
