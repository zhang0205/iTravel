package com.zdj.jiudian;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by Administrator on 2017/7/31.
 */

public class Display extends Activity {
    private Handler handler1;
    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meishidis);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void init(){
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String recommended_dishes=intent.getStringExtra("recommended_dishes");
        String address=intent.getStringExtra("address");
        String phone=intent.getStringExtra("phone");
        String avg_price=intent.getStringExtra("avg_price");
        String all_remarks=intent.getStringExtra("all_remarks");
        String very_good_remarks=intent.getStringExtra("very_good_remarks");
        String good_remarks=intent.getStringExtra("good_remarks");
        String common_remarks=intent.getStringExtra("common_remarks");
        String bad_remarks=intent.getStringExtra("bad_remarks");
        String very_bad_remarks=intent.getStringExtra("very_bad_remarks");
        String product_rating=intent.getStringExtra("product_rating");
        String environment_rating=intent.getStringExtra("environment_rating");
        String service_rating=intent.getStringExtra("service_rating");
        ((TextView)findViewById(R.id.name)).setText(name);
        if (recommended_dishes.equals(""))
            ((TextView)findViewById(R.id.recommended_dishes)).setText("暂无!");
        else
            ((TextView)findViewById(R.id.recommended_dishes)).setText(recommended_dishes);
        if (address.equals(""))
            ((TextView)findViewById(R.id.address)).setText("暂无!");
        else
            ((TextView)findViewById(R.id.address)).setText(address);
        if (phone.equals(""))
            ((TextView)findViewById(R.id.phone)).setText("暂无!");
        else
            ((TextView)findViewById(R.id.phone)).setText(phone);
        if (avg_price.equals(""))
            ((TextView)findViewById(R.id.avg_price)).setText("暂无!");
        else
            ((TextView)findViewById(R.id.avg_price)).setText("人均"+avg_price+"元");
        if (all_remarks.equals(""))
            ((TextView)findViewById(R.id.remarks)).setText("暂无!");
        else
            ((TextView)findViewById(R.id.remarks)).setText("总评价（"+all_remarks+"）\n超级好评（"+very_good_remarks+"）" +
                    " 好评（"+good_remarks+"） 中评（"+common_remarks+"） 差评（"+bad_remarks+"） 超级差评（"+very_bad_remarks+"）");
        if (product_rating.equals(""))
            ((TextView)findViewById(R.id.rating)).setText("暂无!");
        else
            ((TextView)findViewById(R.id.rating)).setText("食品评分 "+product_rating+"   " +
                    " 环境评分 "+environment_rating+" \n服务评分 "+service_rating+"     综合评分");
        imageView=findViewById(R.id.photos);
        returnBitMap(intent.getStringExtra("photos"));
        handler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==1){
                    imageView.setImageBitmap((Bitmap) msg.obj);
                }
                else {
                    imageView.setBackground(Display.this.getResources().getDrawable(R.drawable.meishipng));
                }
            }
        };
    }
    public void returnBitMap(final String url){
        new Thread(){
            @Override
            public void run() {
                URL myFileURL;
                Bitmap bitmap=null;
                try{
                    myFileURL = new URL(url);
                    HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Message msg=new Message();

                if (bitmap==null){
                    msg.obj=null;
                    msg.what=-1;
                }
                else{
                    Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 400,300, true);
                    msg.what=1;
                    msg.obj=newBmp;
                }
                handler1.sendMessage(msg);
            }
        }.start();
    }
}
