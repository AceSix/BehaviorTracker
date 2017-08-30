package com.kotomi.sale.util;

import com.kotomi.sale.model.Competition;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/7
 * @Modified By:
 */
public final class CsvUtil {
//    public static BufferedWriter bw=new BufferedWriter();
    public static File createFileAndColName(String filePath, String fileName, String[] colNames){
        File directory = new File("src\\main\\resources\\data");
        String root="";
        try {
            root=directory.getCanonicalPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        File csvFile = new File(root+filePath, fileName);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(csvFile, "UTF-8");
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<colNames.length; i++){
                if( i<colNames.length-1 )
                    sb.append(colNames[i]+",");
                else
                    sb.append(colNames[i]+"\r\n");

            }
            pw.print(sb.toString());
            pw.flush();
            pw.close();
            return csvFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean appendData(File csvFile, List<String> data){
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile, true), "GBK"), 1024);
            StringBuffer sb = new StringBuffer();
            for(int j=0; j<data.size(); j++){
                if(j<data.size()-1)
                    sb.append(data.get(j)+",");
                else
                    sb.append(data.get(j)+"\r\n");
            }
            bw.write(sb.toString());
            bw.flush();//是把缓冲区的数据强行输出
            bw.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
/*format of one String:“row1,row2,row3...”*/
    public static boolean appendAllData(File csvFile, List<String> data){
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile, true), "GBK"), 1024);
            StringBuffer sb = new StringBuffer();
            for(int j=0; j<data.size(); j++){
                    sb.append(data.get(j)+"\r\n");
            }
            bw.write(sb.toString());
            bw.flush();//是把缓冲区的数据强行输出
            bw.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public  static  List<String> readFile(String path){
        List<String> list=new ArrayList<String>();
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        try {

            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            reader.readLine();
            String line = null;
            while((line=reader.readLine())!=null) {
                String items[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                for(int i=0;i<items.length;i++){
                    list.add(items[i]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }


    /*format of one List:[row1,row2,row3...]*/
    public static boolean appendAllData2(File csvFile, List<List<String>> data){
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile, true), "GBK"), 1024);
            for(int i=0; i<data.size(); i++){
                List tempData = data.get(i);
                StringBuffer sb = new StringBuffer();
                for(int j=0; j<tempData.size(); j++){
                    if(j<tempData.size()-1)
                        sb.append(tempData.get(j)+",");
                    else
                        sb.append(tempData.get(j)+"\r\n");
                }
                bw.write(sb.toString());
                if(i%1000==0)
                    bw.flush();
            }
            bw.flush();
            bw.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
