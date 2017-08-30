package com.kotomi.sale.model;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/7
 * @Modified By:
 */
public class Competition {
    private String origin;
    private String destination;
    private Integer num;
    private  Double distance;

    public Double getDistance() {
        return distance;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
