package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;
import com.kotomi.sale.model.Competition;
import com.kotomi.sale.model.Customer;
import com.kotomi.sale.util.CsvUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/7
 * @Modified By:
 */
public class CompetitionService {

    //获取所有店铺
    public List<Shop> getShopList(){
        String sql="select shopid,shopname from shopcomment";
        return DatabaseHelper.queryEntityList(Shop.class,sql);
    }

    //创建竞争信息
    public boolean createCompetition(Map<String,Object> fieldMap){

        return DatabaseHelper.insertEntity(Competition.class,fieldMap);
    }
//    更新竞争信息的距离
    public boolean updateCompetition(String origin,String destination,Map<String,Object> fieldMap){
        return DatabaseHelper.updateEntity(Competition.class,origin,destination,fieldMap);
    }

    //单个店铺的竞争状况查询
    public List<Competition> calCompetition(String shopid, String shopname){
        String sql="SELECT t.shopid,t.shop,COUNT(*) AS num FROM (\n" +
                "SELECT shopid,shop,userName FROM `shopcomment`\n" +
                "WHERE userName IN  (SELECT userName FROM `shopcomment` WHERE shopid ='"+shopid+"' AND shop=\"" +shopname+
                "\") AND shopid <> '"+shopid+
                "' AND userName <> '匿名用户'\n" +
                "UNION \n" +
                "SELECT shopid,shop,userName FROM `shopcomment`\n" +
                "WHERE userName IN  (SELECT userName FROM `shopcomment` WHERE shopid ='"+shopid+"' AND shop=\""+shopname +
                "\") AND shopid <> '"+shopid+
                "' AND userName <> '匿名用户') t\n" +
                "GROUP BY t.shopid,t.shop";

        return DatabaseHelper.queryEntityList(Competition.class,sql);

    }
    public List<Map<String,Object>> getCompetitionList(String shopid){
        String  sql = "select a.destination, a.origin ,a.num as num ,a.distance *10000 AS distance ,b.cate ,b.shopname from competition a inner join shopseg b" +
                " on a.destination = b.shopid where a.origin = " + shopid + " and a.num > 1" ;
        return DatabaseHelper.executeQuery(sql);
    }
//     for shopcompetition version1.0
//    public List<Map<String,Object>> getCompetitionList(String shopid){
//        String  sql=" SELECT a.shopid,a.shopname,a.cateid,a.catename,b.num,b.distance FROM `shopinfo_backup` a\n" +
//                " INNER JOIN ( SELECT destination,num,distance FROM `1001aa_takeaway_competition_csv` WHERE origin="+shopid+") AS b\n" +
//                " ON a.shopid=b.destination AND a.cateid  IN (SELECT cateid FROM shopinfo_backup WHERE shopid="+shopid+")";
//        return DatabaseHelper.executeQuery(sql);
//    }
    public List<Map<String,Object>> getCooperationList(String shopid){
        String  sql=" SELECT a.shopid,a.shopname,a.cateid,a.catename,b.num,b.distance FROM `shopinfo_backup` a\n" +
                " INNER JOIN ( SELECT destination,num,distance FROM `1001aa_takeaway_competition_csv` WHERE origin="+shopid+") AS b\n" +
                " ON a.shopid=b.destination AND a.cateid  NOT IN (SELECT cateid FROM shopinfo_backup WHERE shopid="+shopid+")";
        return DatabaseHelper.executeQuery(sql);
    }

    //将获取的信息放入数据库中
//    public boolean createCompetition(Map<String,Object> fieldMap){
//
//        return DatabaseHelper.insertEntity(Competition.class,fieldMap);
//    }

//    public static  void main(String args[]){
//        CompetitionService competitionService=new CompetitionService();
//        //先读取所有shopid
//        List<Shop> shoplist=competitionService.getShopList();
//        //循环遍历 同时存入csv
//        String[] colNames={"origin","destination","count"};
//        File csvFile = CsvUtil.createFileAndColName("D:\\Users\\Kotomi\\Desktop\\餐饮行业之外卖\\数据", "1001aa_takeaway_competition.csv", colNames);
//        List<List<String>> data = new ArrayList<>();
//        int count=0;
//        for(int i=0;i<shoplist.size();i++){
//            Shop shop=shoplist.get(i);
//            String shopid=shop.getShopid();
//            String shopname=shop.getShopname();
//            List<Competition> competitions=competitionService.calCompetition(shopid,shopname);
//
////            CsvUtil.appendData(csvFile, data);
//
//            for(int j=0;j<competitions.size();j++){
//                List<String> list=new ArrayList<>();
//                list.add(shopid);
//                list.add(competitions.get(j).getShopid());
//                list.add(competitions.get(j).getNum().toString());
//                System.out.print(shopid+" ");
//                System.out.print(competitions.get(j).getShopid()+" ");
//                System.out.print(competitions.get(j).getNum()+"\n");
//                data.add(list);
//
//            }
//            count+=1;
//            System.out.print(count+"----完成"+shopid+"\n");
//        }
////        CsvUtil.appendData(csvFile, data);
//
//
//    }
}
