package com.zdj.suixin;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/20.
 */

public class Constant implements Parcelable {
    public Bitmap mimage=null; //传回回忆轴一级页面的图片
    public String city=null;    //传回回忆轴一级页面的城市景点名
    public String time=null;    //传回回忆轴一级页面的游玩时间

    public Constant(Bitmap bitmap,String city,String time) {
        this.mimage=bitmap;
        this.city=city;
        this.time=time;
    }

    public  Bitmap getMimage() {
        return mimage;
    }

    public  void setMimage(Bitmap mimage) {
        this.mimage = mimage;
    }

    public  String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public  String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected Constant(Parcel in) {
        mimage = in.readParcelable(Bitmap.class.getClassLoader());
        city = in.readString();
        time = in.readString();
    }
    public static final Creator<Constant> CREATOR = new Creator<Constant>() {
        @Override
        public Constant createFromParcel(Parcel in) {
            return new Constant(in);
        }

        @Override
        public Constant[] newArray(int size) {
            return new Constant[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mimage, i);
        parcel.writeString(city);
        parcel.writeString(time);
    }

}
