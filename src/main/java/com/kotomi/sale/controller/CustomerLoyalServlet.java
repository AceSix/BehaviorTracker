package com.kotomi.sale.controller;

import com.kotomi.sale.service.CommonService;
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
@WebServlet("/customerLoyal")
public class CustomerLoyalServlet extends HttpServlet {
    private CommonService commonService;
    @Override
    public void init()throws ServletException {
        commonService =new CommonService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        String params = req.getParameter("params");
        String[] origin = params.split(",");
        //sql文件的注意大小写
        String sql = "SELECT username, shopid , commentTime FROM shopcomment WHERE username IN   \n" +
                "(SELECT a.username FROM (SELECT DISTINCT(username) FROM shopcomment WHERE  shopid =  '"+ origin[0] +"' )a\n" +
                "INNER JOIN\n" +
                "(SELECT DISTINCT(username) FROM shopcomment WHERE shopid = " + origin[1] + ")b\n" +
                "ON a.username = b.username AND a.username <> \"匿名用户\")\n" +
                "AND shopid IN ( '"+ origin[0] + "','" + origin[1] +") ORDER BY username,commentTime";
        List<Map<String,Object>> shopLocationList = commonService.getSqlResult(sql);
        JSONArray jsonList = JSONArray.fromObject(shopLocationList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }



}
