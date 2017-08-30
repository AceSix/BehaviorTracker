package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;
import com.kotomi.sale.model.ShopComment;
import com.kotomi.sale.model.ShopInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/9
 * @Modified By:
 */
public class ShopCommentService {
    public List<ShopComment> getShopList(){
        String sql="SELECT DISTINCT shopid,mtWmPoiId FROM shopcomment";
        return DatabaseHelper.queryEntityList(ShopComment.class,sql);
    }

    public List<ShopComment> getShopCommentListByShopInfo(String shopid, String mtWmPoiId){
        String sql="select * from shopcomment where shopId="+shopid+" and mtWmPoiId="+mtWmPoiId;
        return DatabaseHelper.queryEntityList(ShopComment.class,sql);
    }
    public List<ShopComment> getShopCommentList(){
        String sql="select * from shopcomment";
        return DatabaseHelper.queryEntityList(ShopComment.class,sql);
    }

    public  List<Map<String,Object>> getShopTime(String shopid ){
        String sql = "SELECT a.shopid , a.shopname ,c.cate , a.maxtime , a.mintime , a.orders, b.num AS count ,b.distance * 1000 as distance FROM (SELECT shopid , shopname , MAX(commentTime)  AS maxtime, MIN(commentTime)  AS mintime, COUNT(*)  AS orders\n" +
                "FROM shopcomment  WHERE shopid IN (SELECT destination FROM competition WHERE origin = '"+ shopid +"' AND num > 9  )\n" +
                "AND  commentTime >= \"2015-01-01\" \n" +
                "AND  commentTime <= \"2017-06-31\" \n" +
                "GROUP BY shopid) a INNER JOIN competition b ON a.shopid = b.destination inner join shopseg c on b.destination = c.shopid WHERE b.origin = '"+ shopid +"'";
        return DatabaseHelper.executeQuery(sql);
    }

    public List<Map<String,Object>> getMaxCommentTime(String shopid, String mtWmPoiId){
        String sql="select MAX(commentTime) as dbtime from shopcomment where shopId="+shopid+" and mtWmPoiId="+mtWmPoiId;
        return DatabaseHelper.executeQuery(sql);
    }

    //创建店铺评论信息
    public boolean createShopComment(Map<String,Object> fieldMap){

        return DatabaseHelper.insertEntity(ShopComment.class,fieldMap);
    }
    //更新店铺评论信息
//    public boolean updateShopComment(long id,Map<String,Object> fieldMap){
//        return DatabaseHelper.updateEntity(ShopComment.class,id,fieldMap);
//    }

    //删除店铺评论信息
    public boolean deleteShopComment(String shopid,String mtWmPoiId){

        return DatabaseHelper.deleteEntity1(ShopComment.class,shopid,mtWmPoiId);
    }
}
