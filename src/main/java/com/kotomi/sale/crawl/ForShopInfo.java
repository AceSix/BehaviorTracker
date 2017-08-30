package com.kotomi.sale.crawl;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/9
 * @Modified By:
 */

import com.kotomi.sale.service.ShopInfoService;
import com.kotomi.sale.util.JsonUtil;
import com.kotomi.sale.util.SpiderUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.in;


public class ForShopInfo {
    public static void main(String[] args) throws InterruptedException {
        ShopInfoService shopInfoService = new ShopInfoService();
        String path = "https://takeaway.dianping.com/waimai/ajax/newm/newIndex?address=%E6%B8%85%E5%8D%8E%E5%9B%AD%E6%B8%85%E5%8D%8E%E5%A4%A7%E5%AD%A6&firstcategoryid=0&secondcategoryid=0&cateId=&sortId=&startIndex=0&multifilterids=&channel=6&lat=40.00936&lng=116.335391&actualLat=40.00936&actualLng=116.335391&geoType=2&initialLat=40.00936&initialLng=116.335391&_=1494313425205";
        JSONObject jsonObject = SpiderUtil.getJson(path).getJSONObject("data");
        //类别数组
        JSONArray cates = new JSONArray();
        //shoplist
        JSONArray shoplist = new JSONArray();
        //定义map，存放cateid和name
        Map<String, String> cateType = new HashMap<String, String>();
        //存放一条shop记录
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        //获取cateNav
        cates = JSONArray.fromObject(jsonObject.get("cateNav"));
        for (int i = 0; i < cates.size(); i++) {
            JSONObject cate = cates.getJSONObject(i);
            String cateid = cate.get("cateId").toString();
            String catename = cate.get("name").toString();
            //如果是美食或者全部，则忽略
            if (cateid.equals("0") || cateid.equals("101260")||cateid.equals("19")) continue;
            cateType.put(cateid, catename);
        }

        //搜集在西城区的所有外卖id、star，且按类别存储
        String lat="39.88382";
        String lng="116.643131";
        String shoparea="通州区中医医院2期";
        String shopcity="北京";
        String url = "";
        String url1 = "https://takeaway.dianping.com/waimai/ajax/newm/newIndex?address=%E6%B8%85%E5%8D%8E%E5%9B%AD%E6%B8%85%E5%8D%8E%E5%A4%A7%E5%AD%A6";
        String url2 = "&sortId=";
        String url3 = "&multifilterids=&channel=6&lat="+lat+"&lng="+lng+"&actualLat="+lat+"&actualLng="+lng+"&geoType=2&initialLat="+lat+"&initialLng="+lng;
        String firstcategoryid = "0";
        String secondcategoryid = "0";
        String cateId = "";
        Integer k = 0;


        //按类别存储
        for (String cateid : cateType.keySet()) {
            String catename = cateType.get(cateid);
            firstcategoryid = cateid;
            cateId = cateid;
            k += 1;
            System.out.printf("-------类别:%s,%s,%d\n", cateid, catename, k);
            Integer startIndex = 0;
            Boolean flag = true;
            while (true) {
                url = url1 + "&firstcategoryid=" + firstcategoryid + "&secondcategoryid=" + secondcategoryid + "&cateId=" + cateId + url2 + "&startIndex=" + startIndex.toString() + url3;
                System.out.printf("---startIndex:%d url:%s\n", startIndex, url);
                //让线程随机停止
                long radom = (int) Math.random() * 10000;
                Thread.sleep(radom);
                jsonObject = SpiderUtil.getJson(url);
//                System.out.printf(jsonObject.toString());
                //判断是否访问成功;如果不成功
                if (jsonObject.get("code").toString().equals("500") || jsonObject.getJSONObject("data") == null) {
                    //休息几秒钟
                    System.out.print("----请求失败，正在重新请求\n");
                    Thread.sleep(6000);
                    continue;
                }
                jsonObject = jsonObject.getJSONObject("data");
                //标记该类别的最后一条
//                   if(jsonObject.get("nextStartIndex").toString()=="0") flag=false;
                if (jsonObject.get("shopList") == null) {
                    System.out.printf("类别:%s,%s 采集完毕\n", cateid, catename);
                    break;
                }
                shoplist = new JSONArray().fromObject(jsonObject.get("shopList"));
                for (int i = 0; i < shoplist.size(); i++) {
                    JSONObject shop = shoplist.getJSONObject(i);
                    //判断如果数据库中有数据，则跳过
                    String shopid = shop.get("id").toString();
                    String mtWmPoiId = shop.get("mtWmPoiId").toString();
                    if (shopInfoService.getShopInfo(shopid, mtWmPoiId, cateid) != null) continue;

                    //准备数据，并插入数据库
                    Boolean shopOpenStatus;
                    if (shop.get("shopOpenStatus").toString() == "1") shopOpenStatus = false; //未开
                    else shopOpenStatus = true;//开
                    fieldMap.put("deliverTime", Integer.parseInt(shop.get("deliverTime").toString()));
                    fieldMap.put("distance", Integer.parseInt(shop.get("distance").toString()));
                    fieldMap.put("minDeliverFee", Double.parseDouble(shop.get("minDeliverFee").toString()));
                    fieldMap.put("minFee", Double.parseDouble(shop.get("minFee").toString()));
                    fieldMap.put("mtWmPoiId", shop.get("mtWmPoiId").toString());
                    fieldMap.put("shopId", shopid);
                    fieldMap.put("shopName", shop.get("name").toString());
                    fieldMap.put("shopOpenStatus", shopOpenStatus);
                    fieldMap.put("sold", Integer.parseInt(shop.get("sold").toString()));
                    fieldMap.put("star", Integer.parseInt(shop.get("star").toString()));
                    fieldMap.put("cateId", cateid);
                    fieldMap.put("cateName", catename);
                    fieldMap.put("shoparea", shoparea);
                    fieldMap.put("shopcity", shopcity);
                    shopInfoService.createShopInfo(fieldMap);
                }
                //如果是最后一条，则跳出
                if (!flag) break;
                startIndex += 25;
            }

        }


    }
}
