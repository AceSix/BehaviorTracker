package com.kotomi.sale.controller;

import com.kotomi.sale.service.CommonService;
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
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
@WebServlet("/discoveryriver")
public class ShopDiscoveryRiverServlet extends HttpServlet {
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
        String origin = req.getParameter("params");
        //sql文件的注意大小写
        String sql = "SELECT commentTime AS days, COUNT(*) num, shopid FROM shopcomment WHERE \n" +
                "shopid IN (SELECT t.destination FROM (SELECT destination, num  FROM competition WHERE origin = '"+ origin + "'  ORDER BY num DESC LIMIT 6 ) AS t)\n" +
                "and commentTime <=(select max(commentTime) from shopcomment where shopid = '" + origin +"') \n" +
                "and commentTime >=(select min(commentTime) from shopcomment where shopid = '" + origin +"') \n" +
                "GROUP BY shopid,days";
        List<Map<String,Object>> orderList = commonService.getSqlResult(sql);

        String sql_type = "SELECT destination  FROM competition WHERE origin = '"+ origin + "' ORDER BY num DESC LIMIT 6 ";
        List<Map<String,Object>> typeList = commonService.getSqlResult(sql_type);
        //JSONArray jsonList1 = JSONArray.fromObject(orderList);
        //JSONArray jsonList2 = JSONArray.fromObject(typeList);
        JSONObject json = new JSONObject();
        json.put("orderlist" , orderList );
        json.put("typelist" , typeList );
        PrintWriter out= null;
        out = resp.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }



}
