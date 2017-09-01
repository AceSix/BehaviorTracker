package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;
import com.kotomi.sale.model.ShopInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/9
 * @Modified By:
 */
public class ShopInfoService {
    public List<ShopInfo> getShopInfoList(){
        String sql="select * from ShopInfo";
        return DatabaseHelper.queryEntityList(ShopInfo.class,sql);
    }

//    public ShopInfo getShopInfo(String shopid,String mtWmPoiId,String cateid){
//        String sql="select * from ShopInfo  where shopId="+shopid+" and mtWmPoiId="+mtWmPoiId+" and cateId="+cateid;
//        return DatabaseHelper.queryEntity(ShopInfo.class,sql);
//    }

    public List<Map<String,Object>> getShopInfo(String shopid){
        String sql="select a.shopid,a.shopname, b.latitude , b.longitude from ShopInfo a inner join shoplocation b on a.shopid = b.shopid where a.shopId="+shopid+" limit 1" ;
        return DatabaseHelper.executeQuery(sql);
    }


    //创建店铺信息
    public boolean createShopInfo(Map<String,Object> fieldMap){

        return DatabaseHelper.insertEntity(ShopInfo.class,fieldMap);
    }
    //更新店铺信息
//    public boolean updateShopInfo(long id,Map<String,Object> fieldMap){
//        return DatabaseHelper.updateEntity(ShopInfo.class,id,fieldMap);
//    }

    //删除店铺信息
    public boolean deleteShopInfo(long id){

        return DatabaseHelper.deleteEntity(ShopInfo.class,id);
    }
}
