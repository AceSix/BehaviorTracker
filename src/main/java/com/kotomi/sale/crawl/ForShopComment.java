package com.kotomi.sale.crawl;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/9
 * @Modified By:
 */

import com.kotomi.sale.model.ShopComment;
import com.kotomi.sale.model.ShopInfo;
import com.kotomi.sale.service.ShopCommentService;
import com.kotomi.sale.service.ShopInfoService;
import com.kotomi.sale.util.CsvUtil;
import com.kotomi.sale.util.SpiderUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;



public class ForShopComment {
    public static void main(String[] args) throws InterruptedException {
        ShopInfoService shopInfoService = new ShopInfoService();
        ShopCommentService shopCommentService=new ShopCommentService();
        //从shopinfo_filter中读取shopid
        List<ShopInfo> shops=new ArrayList<ShopInfo>();
        List<ShopComment> shopsCom=new ArrayList<ShopComment>();
        shops=shopInfoService.getShopInfoList();
        shopsCom=shopCommentService.getShopList();
        //记录异常shop
        String [] colname={"shopid","mtWmPoiId","shopname","content"};
        File errorlog=CsvUtil.createFileAndColName("/","errorlog.csv",colname);

        //设置日期格式化样式为：yyyy-MM-dd
        SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //确定地点（北京大学）
        String lat="39.88382";
        String lng="116.643131";
        String url1="https://takeaway.dianping.com/waimai/ajax/newm/shopcomments?lng="+lng+"&lat="+lat+"&gpsLng=&gpsLat=";
        String shopid="";
        String mtWmPoiId="";
        String shopname="";
        JSONArray commentlist = new JSONArray();
        //存放一条comment记录
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        Date dbtime=new Date();
        for (ShopInfo shop:shops) {
            //判断shop review里面是否有，如果有，则不需要爬
           shopid=shop.getShopId();
           mtWmPoiId=shop.getMtWmPoiId();
           shopname=shop.getShopName();
            System.out.printf("shopname:%s shopid:%s mtWmPoiId:%s \n", shopname, shopid,mtWmPoiId);
           Integer startIndex=0;
           //判断该用户是否已经导入
          // if(shopCommentService.getShopCommentListByShopInfo(shopid,mtWmPoiId).size()!=0) continue;
            //获取目前数据库最大的commenttime
            List<Map<String,Object>> list=shopCommentService.getMaxCommentTime(shopid,mtWmPoiId);
            try {
                //有可能数据库里面没有，这个时候就会报错
                Object temp=list.get(0).get("dbtime");
                if(temp==null)
                    dbtime = SimpleDateFormat.parse("1970-1-1");
                else
                    dbtime= SimpleDateFormat.parse(temp.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //根据shopid构建需要访问的path
            while(true) {
                String url =url1+ "&shopId=" + shopid + "&mtWmPoiId=" + mtWmPoiId + "&startIndex=" + startIndex.toString() + "&labelId=0&scoreType=0";
                System.out.printf("---startIndex:%d url:%s\n", startIndex, url);
                //让线程随机停止
                long radom = (int) Math.random() * 10000;
                Thread.sleep(radom);
                //访问path，获取json信息，并存入数据库
                JSONObject jsonObject = SpiderUtil.getJson(url);
                //判断是否访问成功;如果不成功
                if (jsonObject.get("code").toString().equals("500") || jsonObject.getJSONObject("data") == null) {
                    //休息几秒钟
                    System.out.print("----请求失败，正在重新请求\n");
                    Thread.sleep(6000);
                    continue;
                }
                JSONObject data = jsonObject.getJSONObject("data");
                //如果有异常，捕捉异常；如果是无法处理的问题，则先放在一边
                try {
                    commentlist = new JSONArray().fromObject(data.get("list"));
                }catch (Exception e){
                    //如果有异常，在log中记录
                     List<String> errorlist=new ArrayList<String>();
                     errorlist.add(shopid);
                     errorlist.add(mtWmPoiId);
                     errorlist.add(shopname);
                     errorlist.add(jsonObject.toString());
                    CsvUtil.appendData(errorlog,errorlist);
                }

                if (commentlist.size()==0) {
                    System.out.printf("店铺:%s,%s ,%s采集完毕\n", shopname, shopid, mtWmPoiId);
                    break;
                }

                for (int i = 0; i < commentlist.size(); i++) {
                    JSONObject comment = commentlist.getJSONObject(i);
                    //准备数据，并插入数据库
                    //如果commentTime比数据库的评论时间大，则插入
                    if (comment.get("commentTime") == null) {
                        fieldMap.put("commentTime", null);
                    } else {
                        String commentTime = comment.get("commentTime").toString();
                        try {
                           if(SimpleDateFormat.parse(commentTime).getTime()<=dbtime.getTime()) continue;
                           fieldMap.put("commentTime", SimpleDateFormat.parse(commentTime));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //判断是否为空
                    if (comment.get("userName") == null) {
                        fieldMap.put("userName", null);
                    } else {
                        fieldMap.put("userName", comment.get("userName").toString());
                    }
                    fieldMap.put("shopid", shopid);
                    fieldMap.put("mtWmPoiId", mtWmPoiId);
                    fieldMap.put("shopname", shopname);

                    if (comment.get("score") == null) {
                        fieldMap.put("score", null);
                    } else {
                        fieldMap.put("score", Integer.parseInt(comment.get("score").toString()));
                    }
                    //处理特殊字符 ---点赞emoj 修改sql的编码为utf8mb4
                    if (comment.get("content") == null) {
                        fieldMap.put("content", null);
                    } else {
                        fieldMap.put("content", comment.get("content").toString());
                    }

                    if (comment.get("label") == null) {
                        fieldMap.put("label", null);
                    } else {
                        fieldMap.put("label", comment.get("label").toString());
                    }

                    if (comment.get("praiseDish") == null) {
                        fieldMap.put("praiseDish", null);
                    } else {
                        fieldMap.put("praiseDish", comment.get("praiseDish").toString());
                    }

                    if (comment.get("shopRely") == null) {
                        fieldMap.put("praiseDish", null);
                    } else {
                        fieldMap.put("shopRely", comment.get("shopRely").toString());
                    }

                    //配送时间数值化 1小时50分钟==》110 （-209889异常处理）
                    String delivery0 = comment.get("deliveryTime").toString();
                    int delivery = 0;
                    int erro = delivery0.indexOf("-");
                    int hourindex = delivery0.indexOf("小时");
                    int minindex = delivery0.indexOf("分钟");
                    int hour = 0;
                    int min = 0;

                    if (erro != -1) delivery = 0;//异常处理，赋值为0
                    if (hourindex != -1) hour = Integer.valueOf(delivery0.substring(0, hourindex)) * 60;
                    if (minindex != -1 && hourindex != -1)
                        min = Integer.valueOf(delivery0.substring(hourindex + 2, minindex));
                    if (minindex != -1 && hourindex == -1) min = Integer.valueOf(delivery0.substring(0, minindex));
                    if (erro == -1 && hourindex != -1 && minindex != -1) delivery = hour + min;
                    if (erro == -1 && hourindex != -1 && minindex == -1) delivery = hour;
                    if (erro == -1 && hourindex == -1 && minindex != -1) delivery = min;
                    fieldMap.put("deliveryTime0", delivery0);
                    fieldMap.put("deliveryTime", delivery);
                    try {
                        shopCommentService.createShopComment(fieldMap);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.printf("%s %s爬取失败",shopid,mtWmPoiId);
                        //删除数据库相关评论
                        shopCommentService.deleteShopComment(shopid,mtWmPoiId);
                        continue;
                    }
                }
                startIndex += 20;
            }
        }





    }
}
