package com.kotomi.sale.controller;

import com.kotomi.sale.model.Competition;
import com.kotomi.sale.service.CompetitionService;
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
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
@WebServlet("/shopcompetition")
public class ShopCompetitionServlet extends HttpServlet {
    private CompetitionService competitionService;
    public JSONObject json;
    @Override
    public void init()throws ServletException {
        competitionService =new CompetitionService();
        json=new JSONObject();
    }
    @Override
    //servlet的代码
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //防止前台代码不识别中文
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        String shopid = req.getParameter("shopid");
//        System.out.print(shopid);
        List<Map<String,Object>> shopCompetitionList = competitionService.getCompetitionList(shopid);
        JSONArray jsonList = JSONArray.fromObject(shopCompetitionList);
        PrintWriter out= null;
        out = resp.getWriter();
        out.print( jsonList );
        out.flush();
        out.close();
    }


//    public void get(List<Map<String,Object>> list){
//        for(int i=0;i<list.size();i++){
//            Map<String,Object> shop=list.get(i);
//            String temp=shop.get("shopid").toString();
//            JSONObject jsontemp=new JSONObject();
//            jsontemp.put("competition",JSONArray.fromObject(competitionService.getCompetitionList(temp)));
//            jsontemp.put("cooperation",JSONArray.fromObject(competitionService.getCooperationList(temp)));
//            json.put(temp,jsontemp);
//        }
//    }



}
