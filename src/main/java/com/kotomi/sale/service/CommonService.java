package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;

import java.util.List;
import java.util.Map;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/7/15
 * @Modified By:
 */
public class CommonService {

    public List<Map<String,Object>> getSqlResultFromPath(String sqlpath){
        return DatabaseHelper.executeSqlFile(sqlpath);
    }
    public List<Map<String,Object>> getSqlResult(String sql){
        return DatabaseHelper.executeQuery(sql);
    }



}
