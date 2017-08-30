package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by Kotomi on 2017/3/19.
 */
public class Z_ShopLocationService {

    public List<Map<String,Object>> getShopLocation(String sqlpath){
      return DatabaseHelper.executeSqlFile(sqlpath);
    }

    public static  void main(String args[]){
        List<Map<String,Object>> list= DatabaseHelper.executeSqlFile("sql/ADshoplocation_get.sql");
        for(int i=0;i<list.size();i++) {
            System.out.print(list.get(i));
        }
    }
}
