package com.minsu;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/10/22.
 */

public class ConstantBitmap implements Parcelable {
    public Bitmap mimage1=null;

    public ConstantBitmap(Bitmap mimage1) {
        this.mimage1 = mimage1;
    }

    public Bitmap getMimage1() {
        return mimage1;
    }

    public void setMimage1(Bitmap mimage1) {
        this.mimage1 = mimage1;
    }
    protected ConstantBitmap(Parcel in){
        mimage1=in.readParcelable(Bitmap.class.getClassLoader());

    }
    public static final Creator<ConstantBitmap> CREATOR = new Creator<ConstantBitmap>() {
        @Override
        public ConstantBitmap createFromParcel(Parcel in) {
            return new ConstantBitmap(in);
        }

        @Override
        public ConstantBitmap[] newArray(int size) {
            return new ConstantBitmap[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mimage1, i);
    }
}
