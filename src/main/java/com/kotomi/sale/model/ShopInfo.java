package com.kotomi.sale.model;

import java.util.Date;

/**
 * @Author:Kotomi
 * @Description  店铺信息：配送时间、配送距离、最小配送费、起送费、商店pointid、商店id、商店名、商店是否营业（1表示营业）、月销售量、店铺评级、分类id、分类名
 * @Date:Created on 2017/5/9
 * @Modified By:
 */
public class ShopInfo {

    Integer id;
    Integer deliverTime;
    Integer distance;
    Double minDeliverFee;
    Double minFee;
    String mtWmPoiId;
    String shopId;
    String shopName;
    Boolean shopOpenStatus;
    Integer sold;
    Integer star;
    String cateId;
    String cateName;
    String shoparea;
    String shopcity;

    public String getShoparea() {
        return shoparea;
    }

    public String getShopcity() {
        return shopcity;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDeliverTime() {
        return deliverTime;
    }

    public Integer getDistance() {
        return distance;
    }

    public Double getMinDeliverFee() {
        return minDeliverFee;
    }

    public Double getMinFee() {
        return minFee;
    }

    public String getMtWmPoiId() {
        return mtWmPoiId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public Boolean getShopOpenStatus() {
        return shopOpenStatus;
    }

    public Integer getSold() {
        return sold;
    }

    public Integer getStar() {
        return star;
    }

    public String getCateId() {
        return cateId;
    }

    public String getCateName() {
        return cateName;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setDeliverTime(Integer deliverTime) {
        this.deliverTime = deliverTime;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setMinDeliverFee(Double minDeliverFee) {
        this.minDeliverFee = minDeliverFee;
    }

    public void setMinFee(Double minFee) {
        this.minFee = minFee;
    }

    public void setMtWmPoiId(String mtWmPoiId) {
        this.mtWmPoiId = mtWmPoiId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopOpenStatus(Boolean shopOpenStatus) {
        this.shopOpenStatus = shopOpenStatus;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public void setShoparea(String shoparea) {
        this.shoparea = shoparea;
    }

    public void setShopcity(String shopcity) {

        this.shopcity = shopcity;
    }
}
