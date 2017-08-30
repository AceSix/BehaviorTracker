package com.kotomi.sale.controller;

import com.kotomi.sale.service.CommonService;
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
@WebServlet("/discovery")
public class ShopDiscoveryServlet extends HttpServlet {
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
        String sql = "SELECT username , DAYOFWEEK(`commentTime`) , `commentTime` , shopid , shopname FROM shopcomment WHERE username IN (SELECT DISTINCT username  FROM `shopcomment`\n" +
                "WHERE userName IN  (SELECT userName FROM `shopcomment` WHERE shopid = '"+origin[0] +"' ) AND shopid ='"+origin[1]+"\'\n" +
                "AND userName <> '匿名用户') AND shopid IN (\""+origin[0]+"\",\""+origin[1]+"\") ORDER BY username,commentTime";
        List<Map<String,Object>> shopLocationList = commonService.getSqlResult(sql);
        JSONArray jsonList = JSONArray.fromObject(shopLocationList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(jsonList);
        out.flush();
        out.close();
    }



}
