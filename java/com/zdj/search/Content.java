package com.zdj.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/7/30.
 */

public class Content implements Parcelable{
    private String name;
    private String price;
    private String introduce;
    private String bigimg;
    public Content(String na,String pr,String intr,String big){
        this.name=na;
        this.price=pr;
        this.introduce=intr;
        this.bigimg=big;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getBigimg() {
        return bigimg;
    }

    public void setBigimg(String bigimg) {
        this.bigimg = bigimg;
    }

    protected Content(Parcel in) {
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
