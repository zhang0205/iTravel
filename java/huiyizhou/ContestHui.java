package huiyizhou;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 回忆轴二级页面传回的常量
 */

public class ContestHui implements Parcelable {
    public  Bitmap mimage=null; //传回回忆轴一级页面的图片
    public  String city=null;    //传回回忆轴一级页面的城市景点名
    public  String time=null;    //传回回忆轴一级页面的游玩时间

    public ContestHui(Bitmap bitmap,String city,String time) {
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

    protected ContestHui(Parcel in) {
        mimage = in.readParcelable(Bitmap.class.getClassLoader());
        city = in.readString();
        time = in.readString();
    }
    public static final Creator<ContestHui> CREATOR = new Creator<ContestHui>() {
        @Override
        public ContestHui createFromParcel(Parcel in) {
            return new ContestHui(in);
        }

        @Override
        public ContestHui[] newArray(int size) {
            return new ContestHui[size];
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
