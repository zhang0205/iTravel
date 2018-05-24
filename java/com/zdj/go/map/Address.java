package com.zdj.go.map;

/**
 * Created by Administrator on 2017/8/7.
 */

public class Address {
    private String city;
    private String address;
    private double startLat;
    private double startLon;
    public Address(String city,String address,double startLat,double endLon){
        this.city=city;
        this.address=address;
        this.startLat=startLat;
        this.startLon=endLon;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double endLon) {
        this.startLon = endLon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
