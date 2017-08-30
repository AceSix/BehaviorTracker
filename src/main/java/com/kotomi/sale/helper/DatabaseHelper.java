package com.kotomi.sale.helper;

import com.kotomi.sale.util.CollectionUtil;
import com.kotomi.sale.util.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Properties;
import  org.slf4j. Logger;

/**
 * Created by Kotomi on 2017/3/12.
 */
public class DatabaseHelper {
//    静态代码块，读取db信息
    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);

    //线程控制
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

   //数据库连接池,因为数据库的连接次数有限，通过这种方式解决频繁连接问题
    private static final BasicDataSource DATA_SOURCE;

    private static final QueryRunner QUERY_RUNNER;

    static{

        CONNECTION_HOLDER=new ThreadLocal<Connection>();
        QUERY_RUNNER=new QueryRunner();

        Properties conf= PropsUtil.loadProps("config.properties");
        String driver =conf.getProperty("jdbc.driver");
        String url=conf.getProperty("jdbc.url");
        String username=conf.getProperty("jdbc.username");
        String password=conf.getProperty("jdbc.password");

        DATA_SOURCE=new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);

        try{
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            LOGGER.error("can not load jdbc driver",e);
        }
    }



    public static Connection getConnection(){
        Connection conn=CONNECTION_HOLDER.get();
        if(conn==null){
            try{
                conn= DATA_SOURCE.getConnection();
            }catch(SQLException e){
                LOGGER.error("get connection failure",e);
                throw  new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }
//因为用了BasicDataSource，所以删除closeConnection方法
//    public static void closeConnection(){
//        Connection conn=CONNECTION_HOLDER.get();
//        if(conn!=null){
//            try{
//                conn.close();
//            }catch(SQLException e){
//                LOGGER.error("close connection failure",e);
//                throw new RuntimeException(e);//????
//            }finally {
//                CONNECTION_HOLDER.remove();
//            }
//        }
//    }


    public static <T> List<T>  queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList;
        try{
            Connection conn=getConnection();
            entityList=QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        }catch(SQLException e){
            LOGGER.error("query  entity list failure");
            throw new RuntimeException(e);
        }
        return entityList;
    }
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity;
        try{
            Connection conn=getConnection();
            //BeanHandler 返回bean对象；BeanListHandler返回list对象
            entity=QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        }catch(SQLException e){
            LOGGER.error("query entity  failure");
            throw new RuntimeException(e);
        }
        return entity;
    }
    public static int executeUpdate(String sql,Object... params){
        int rows=0;
        try{
            Connection conn=getConnection();
            //BeanHandler 返回bean对象；BeanListHandler返回list对象
            rows=QUERY_RUNNER.update(conn,sql,params);
        }catch(SQLException e){
            LOGGER.error("update  failure");
            throw new RuntimeException(e);
        }
        return rows;
    }

    public static List<Map<String,Object>> executeQuery(String sql,Object... params){
        List<Map<String,Object>> entityList;
        try{
            Connection conn=getConnection();
            //BeanHandler 返回bean对象；BeanListHandler返回list对象
            entityList=QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        }catch(SQLException e){
            LOGGER.error("execute query  failure");
            throw new RuntimeException(e);
        }
        return entityList;
    }



    public static <T> boolean insertEntity(Class<T> entityClass, Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity;fieldMap is empty");
            return false;
        }
        String sql="INSERT INTO "+getTableName(entityClass);
        StringBuilder columns=new StringBuilder("(");
        StringBuilder values=new StringBuilder("(");
        for(String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql +=columns+"VALUES"+values;

        Object[] params=fieldMap.values().toArray();
        return  executeUpdate(sql,params)==1;

    }

    public static <T> boolean updateEntity(Class<T> entityClass,String origin,String destination, Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not update entity;fieldMap is empty");
            return false;
        }
        String sql="UPDATE "+getTableName(entityClass)+" SET ";
        StringBuilder columns=new StringBuilder();
        for(String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append("=?,");

        }
        sql +=columns.substring(0,columns.lastIndexOf(","))+" WHERE origin=?"+" AND destination=?";

        List<Object> paramList=new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(origin);
        paramList.add(destination);
        Object[] params=paramList.toArray();
        return  executeUpdate(sql,params)==1;

    }

    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql="DELETE FROM "+ getTableName(entityClass)+" WHERE id=?";
        return executeUpdate(sql,id)==1;
    }
    public static <T> boolean deleteEntity1(Class<T> entityClass,String id1,String id2){
        String sql="DELETE FROM "+ getTableName(entityClass)+" WHERE shopid=? and mtWmPoiId=? ";
        return executeUpdate(sql,id1,id2)==1;
    }


    public static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }

    public static  List<Map<String,Object>> executeSqlFile(String filePath){
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String sql="";
            String temp=reader.readLine();
            while(temp!=null){
                sql=sql+temp+" ";
                temp=reader.readLine();
            }
            return executeQuery(sql);
        }catch(Exception e){
            LOGGER.error("execute sql file failure",e);
            throw new  RuntimeException(e);
        }

    }









}
