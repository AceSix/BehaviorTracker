package com.kotomi.sale.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Author:Kotomi
 * @Description
 * @Date:Created on 2017/5/12
 * @Modified By:
 */
public class ShopComment {
    Integer id;
    String useName;
    Date commentTime;
    String mtWmPoiId;
    String shopId;
    String shopName;
    Integer score;
    String content;
    String label;
    String praiseDish;
    Integer deliveryTime;
    String deliveryTime0;
    String shopRely;

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getId() {
        return id;
    }

    public String getUseName() {
        return useName;
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

    public Integer getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public String getLabel() {
        return label;
    }

    public String getPraiseDish() {
        return praiseDish;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public String getDeliveryTime0() {
        return deliveryTime0;
    }

    public String getShopRely() {
        return shopRely;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUseName(String useName) {
        this.useName = useName;
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

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPraiseDish(String praiseDish) {
        this.praiseDish = praiseDish;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryTime0(String deliveryTime0) {
        this.deliveryTime0 = deliveryTime0;
    }

    public void setShopRely(String shopRely) {
        this.shopRely = shopRely;
    }
}
