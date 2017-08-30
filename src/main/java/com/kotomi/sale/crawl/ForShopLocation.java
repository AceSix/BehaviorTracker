package com.kotomi.sale.crawl;

import com.kotomi.sale.model.ShopInfo;
import com.kotomi.sale.service.ShopInfoService;
import com.kotomi.sale.service.ShopLocationService;
import com.kotomi.sale.util.SpiderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
public class ForShopLocation {

    public static  void main(String[] args){
        ShopInfoService shopInfoService=new ShopInfoService();
        ShopLocationService shopLocationService=new ShopLocationService();
        List<ShopInfo> shoplist=shopInfoService.getShopInfoList();
        String shopid="";
        String  url1="http://m.dianping.com/shop/";
        String  url2="/map?msource=qianbaowaimai";


        for(ShopInfo shop :shoplist){
            shopid=shop.getShopId();
            //数据库已有数据，则不需要查询
            if(shopLocationService.getShopLocation(shopid)!=null) continue;
            String url=url1+shopid+url2;
            String htmlContent=SpiderUtil.getHtml(url);
            if(htmlContent=="无结果") continue;
            int lat=htmlContent.indexOf("lat:");
            int lng=htmlContent.indexOf("lng:");
            int id=htmlContent.indexOf("shopId:");
            Map<String, Object> fieldMap = new HashMap<String, Object>();
            if(lat!=-1 && lng!=-1 && id!=-1){
                fieldMap.put("shopid",shopid);
                fieldMap.put("latitude",htmlContent.substring(lat+5,lng-11));
                fieldMap.put("longitude",htmlContent.substring(lng+5,id-11));
                shopLocationService.createShopLocation(fieldMap);
                System.out.printf("%s插入成功\n",shopid);
            }else{
                System.out.printf("%s无地址\n",shopid);
            }
        }
    }
}
