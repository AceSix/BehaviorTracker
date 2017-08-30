package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;

import com.kotomi.sale.model.Competition;
import com.kotomi.sale.model.ShopInfo;
import com.kotomi.sale.model.ShopLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/18
 * @Modified By:
 */
public class ShopLocationService {
/*根据shopid获取店铺地址*/
    public ShopLocation getShopLocation(String shopid){
        String sql="select * from shoplocation where shopId="+shopid;
        return DatabaseHelper.queryEntity(ShopLocation.class,sql);
    }
/*创建店铺地址*/
    public boolean createShopLocation(Map<String,Object> fieldMap){

        return DatabaseHelper.insertEntity(ShopLocation.class,fieldMap);
    }
/*结合shopinfo获取店铺地址和店铺信息*/
    public List<Map<String,Object>> getAllLocation(String sqlpath){
        return DatabaseHelper.executeSqlFile(sqlpath);
    }
    public List<Map<String,Object>> getCompetitiorLocation(String shopid){
        String sql = "SELECT a.origin , a.destination,c.shopname ,c.cate ,a.num AS count ,b.latitude ,b.longitude \n" +
                "                FROM competition a \n" +
                "                INNER JOIN shoplocation b ON a.destination = b.shopid \n" +
                "                INNER JOIN shopseg c ON a.destination = c.shopid \n" +
                "                WHERE a.origin = "+ shopid ;
        return DatabaseHelper.executeQuery(sql);
     }


/*计算两家店之间的距离*/
    public  static  void main(String[] args){
      ShopLocationService shopLocationService=new ShopLocationService();
        CompetitionService competitionService=new CompetitionService();
//        double lon1=116.4685611231089;
//        double lat1=39.947701305568;
        List<Map<String,Object>> shops=DatabaseHelper.executeQuery("select distinct origin from `competition` ");
        for(int j=0;j<shops.size();j++) {
            String shopid=shops.get(j).get("origin").toString();
            ShopLocation temp=shopLocationService.getShopLocation(shopid);
            if(temp==null) continue;
            double lon1=temp.getLongitude();
            double lat1=temp.getLatitude();
            System.out.print("---"+shopid+"开始\n");
            String sql = "SELECT shopid,`latitude`,`longitude`  FROM `shoplocation` WHERE shopid IN (SELECT destination FROM `competition` WHERE origin="+shopid+")";
            List<Map<String, Object>> locations = DatabaseHelper.executeQuery(sql);
            for (int i = 0; i < locations.size(); i++) {
                Double lon2 = Double.parseDouble(locations.get(i).get("longitude").toString());
                Double lat2 = Double.parseDouble(locations.get(i).get("latitude").toString());
                String destination=locations.get(i).get("shopid").toString();

                Double distance=LantitudeLongitudeDist(lon1, lat1, lon2, lat2);
                Map<String, Object> fieldMap = new HashMap<String, Object>();
                fieldMap.put("distance",distance);
                System.out.print(destination+","+lon2+","+lat2+","+distance+"\n");
                competitionService.updateCompetition(shopid,destination,fieldMap);
//                System.out.printf("%s,%f", locations.get(i).get("shopid").toString(), );
            }

        }

    }


    private static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m)

    /**
     * 转化为弧度(rad)
     * */
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    /**
     * 基于余弦定理求两经纬度距离
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位km
     * */
    public static double LantitudeLongitudeDist(double lon1, double lat1,double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double radLon1 = rad(lon1);
        double radLon2 = rad(lon2);

        if (radLat1 < 0)
            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
        if (radLat1 > 0)
            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
        if (radLon1 < 0)
            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
        if (radLat2 < 0)
            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
        if (radLat2 > 0)
            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
        if (radLon2 < 0)
            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);

        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);

        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));
        //余弦定理求夹角
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        double dist = theta * EARTH_RADIUS;
        return dist;
    }

}
