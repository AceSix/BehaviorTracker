package com.kotomi.sale.model;

/**
 * Created by Kotomi on 2017/3/19.
 */
public class ShopLocation {

    private String ShopID;
    private float  latitude;
    private float  longitude;

    public String getShopId() {
        return ShopID;
    }

    public void setShopId(String shopID) {
        ShopID = shopID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
