package com.minsu;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/20.
 */

public class ConstantMinsu implements Parcelable {
    public Bitmap mimage=null;
    public String price;
    public String shap;
    public String dis;
    public String phone;
    public String name;

    public ConstantMinsu(Bitmap bitmap,String price,String shap,String dis,String phone,String name) {
        this.mimage=bitmap;
        this.price=price;
        this.shap=shap;
        this.phone=phone;
        this.dis=dis;
        this.name=name;
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

    public String getShap() {
        return shap;
    }

    public void setShap(String shap) {
        this.shap = shap;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public  Bitmap getMimage() {
        return mimage;
    }

    public  void setMimage(Bitmap mimage) {
        this.mimage = mimage;
    }

    protected ConstantMinsu(Parcel in) {
        mimage = in.readParcelable(Bitmap.class.getClassLoader());
        dis = in.readString();
        shap = in.readString();
        price=in.readString();
        phone=in.readString();
        name=in.readString();
    }
    public static final Creator<ConstantMinsu> CREATOR = new Creator<ConstantMinsu>() {
        @Override
        public ConstantMinsu createFromParcel(Parcel in) {
            return new ConstantMinsu(in);
        }

        @Override
        public ConstantMinsu[] newArray(int size) {
            return new ConstantMinsu[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mimage, i);
        parcel.writeString(price);
        parcel.writeString(shap);
        parcel.writeString(dis);
        parcel.writeString(phone);
        parcel.writeString(name);
    }

}
