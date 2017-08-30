package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;
import com.kotomi.sale.model.Location;

import java.util.*;

/**
 * Created by Kotomi on 2017/3/17.
 */
public class LocationService {

    public List<Location> getLocationBycity(String city){

        String sql="select * from location where city="+city;
        return DatabaseHelper.queryEntityList(Location.class, sql);
    }

    public Location getLocationByid(String id){

        String sql="select * from location where id="+id;
        return DatabaseHelper.queryEntity(Location.class, sql);
    }

    public List<Location> getLocation( ){

        String sql="select * from location ";
        return DatabaseHelper.queryEntityList(Location.class, sql);
    }

    public boolean insertLocation(Map<String,Object> fieldMap){
       return  DatabaseHelper.insertEntity(Location.class, fieldMap);
    }

        public static  void main(String args[]) {
            LocationService locationService = new LocationService();
            List<Location> locationList = locationService.getLocationBycity("\"北京\"");
            for(int i=0;i<locationList.size();i++){
                Location a=locationList.get(i);
                System.out.println("参考点："+a.getPlace());
                double minDistance=Double.MAX_VALUE;
                Map<Location,Double> distancelist=new HashMap<Location,Double>();
                for(int j=0;j<locationList.size();j++){
                        Location b=locationList.get(j);
                        if(a.getLatitude()!=b.getLatitude()&&a.getLongitude()!=b.getLongitude()){
                            double x=Math.abs(a.getLatitude()-b.getLatitude());
                            double y=Math.abs(a.getLongitude()-b.getLongitude());
                            double distance=Math.sqrt(x*x+y*y);
                            distancelist.put(b,distance);
//                            if(distance<=minDistance){
//                                minDistance=distance;
//                            }
                        }
                }
                List<Map.Entry<Location,Double>> list=new ArrayList<Map.Entry<Location,Double>>(distancelist.entrySet());

                Collections.sort(list, new Comparator<Map.Entry<Location,Double>>() {
                    public int compare(Map.Entry<Location,Double> o1,
                                       Map.Entry<Location,Double> o2) {
                        if ((o2.getValue() - o1.getValue())>0)
                            return -1;
                        else if((o2.getValue() - o1.getValue())==0)
                            return 0;
                        else
                            return 1;
                    }
                });
                for(int m=0;m<5;m++) {
                    Location location1 = list.get(m).getKey();
                    System.out.println(list.get(m).getValue()+"  地点:" + location1.getPlace() + " 纬度:" + location1.getLatitude() + " 经度:" + location1.getLongitude()
                    +"划分："+ (location1.getLatitude()+a.getLatitude())/2+","+(location1.getLongitude()+a.getLongitude())/2);
                }
            }

           // JSONArray jsonArray2 = JSONArray.fromObject(locationList);
//            System.out.print(jsonArray2);
        }

}
