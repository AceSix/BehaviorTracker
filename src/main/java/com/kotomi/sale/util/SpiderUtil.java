package com.kotomi.sale.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/9
 * @Modified By:
 */
public final class SpiderUtil {
    public static JSONObject getJson(String path){
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        String result = "";
        JSONObject jsonObject = new JSONObject();
        try {
            //解析url 获取类别信息
            url = new URL(path);
            urlconn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(urlconn.getInputStream(), "utf8"));//uft8避免乱码
            String line;
            while ((line = br.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面；
                // 需要注意的是：返回json，只有一行
                result += line;
            }
            jsonObject = JSONObject.fromObject(result);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            return jsonObject;
        }

    }

    public static String getHtml(String path){
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        String result="";
        try {
            url = new URL(path);
            urlconn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(urlconn.getInputStream(), "utf8"));//uft8避免乱码
            String line;
            while ((line = br.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面；
                // 需要注意的是：返回json，只有一行
                result += line;
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
//            e.printStackTrace();
            result="无结果";
        }
        return result;
    }


}
