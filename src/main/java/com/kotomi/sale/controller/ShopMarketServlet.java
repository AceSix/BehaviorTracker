package com.kotomi.sale.controller;

import com.kotomi.sale.model.ShopLocation;
import com.kotomi.sale.service.CommonService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
@WebServlet("/shopmarket")
public class ShopMarketServlet extends HttpServlet {
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
        String[] params = req.getParameter("params").split(",");
        if(params.length != 2)  return;
        String origin = params[0];
        String selectshopid = params[1];
        String sqlMarketorder = "SELECT username, COUNT(*) AS marketorder  FROM shopcomment WHERE username IN  (SELECT DISTINCT username  FROM `shopcomment`\n" +
                "                WHERE userName IN  (SELECT userName FROM `shopcomment` WHERE shopid = "+origin+" ) AND shopid =" +selectshopid + "\n " +
                "                AND userName <> '匿名用户' ) AND shopid IN (SELECT shopid FROM  `shopseg` WHERE cate = \"奶茶\") GROUP BY username";
        String sqlTotalorder = "SELECT username, COUNT(*)  AS totalorder FROM shopcomment WHERE username IN  (SELECT DISTINCT username  FROM `shopcomment`\n" +
                "                WHERE userName IN  (SELECT userName FROM `shopcomment` WHERE shopid = "+origin+" ) AND shopid =" +selectshopid + "\n" +
                "                AND userName <> '匿名用户' ) GROUP BY username   ";
        String sqlshopOrder = "SELECT a.username , a.shopid , a.shopname ,COUNT(*) AS shoporder FROM \n" +
                "(SELECT username , DAYOFWEEK(`commentTime`) , `commentTime` , shopid , shopname FROM shopcomment WHERE username IN (SELECT DISTINCT username  FROM `shopcomment`\n" +
                "                WHERE userName IN  (SELECT userName FROM `shopcomment` WHERE shopid = "+origin+" ) AND shopid =" +selectshopid + "\n" +
                "                AND userName <> '匿名用户') AND shopid IN ( "+ origin +","+selectshopid+") ORDER BY username,commentTime) a\n" +
                "                GROUP BY a.username,a.shopid";

        List<Map<String,Object>> marketorderList = commonService.getSqlResult(sqlMarketorder);
        List<Map<String,Object>> totaloorderlist = commonService.getSqlResult(sqlTotalorder);
        List<Map<String,Object>> shoporderList = commonService.getSqlResult(sqlshopOrder);

        JSONArray marketorderJson= JSONArray.fromObject(marketorderList);
        JSONArray totaloorderJson= JSONArray.fromObject(totaloorderlist);
        JSONArray shoporderJson= JSONArray.fromObject(shoporderList);

        JSONObject json = new JSONObject();
        json.put("marketorder" , marketorderJson );
        json.put("totalorder" , totaloorderJson );
        json.put("shoporder", shoporderJson );
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(json);
        out.flush();
        out.close();

    }

}