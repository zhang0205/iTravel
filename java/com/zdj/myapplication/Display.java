package com.zdj.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/8/17.
 */

public class Display extends Activity {
    private ImageView imageView;
    private Handler handler1;
    private ImageButton question;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disxml);
        init();
        question=findViewById(R.id.question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
                intent.setData(Uri.parse("tel:" + "022-63862643"));
                if (ActivityCompat.checkSelfPermission(Display.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init(){
        Intent intent=getIntent();
        String hotel_name=intent.getStringExtra("hotelName");
        String price=intent.getStringExtra("highestPrice");
        String address=intent.getStringExtra("address");
        String roomtype=intent.getStringExtra("roomType");
        String start=intent.getStringExtra("starRatedName");
        String one=intent.getStringExtra("oneWord");
        String intro=intent.getStringExtra("intro");
        String img=intent.getStringExtra("img");
        TextView hotelName=findViewById(R.id.hotelName);
        TextView highestPrice=findViewById(R.id.highestPrice);
        TextView addresstext=findViewById(R.id.address);
        TextView roomTypetext=findViewById(R.id.roomType);
        TextView starRatedName=findViewById(R.id.starRatedName);
        TextView oneWord=findViewById(R.id.oneWord);
        TextView introtext=findViewById(R.id.intro);
        hotelName.setText(hotel_name);
        highestPrice.setText(price+"元起");
        addresstext.setText(address);
        roomTypetext.setText(roomtype);
        starRatedName.setText(start);
        oneWord.setText(one);
        introtext.setText(intro);
        imageView=findViewById(R.id.img);
        returnBitMap(img);
        handler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==1){
                    imageView.setImageBitmap((Bitmap) msg.obj);
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
                msg.what=1;
                if (bitmap==null)
                    msg.obj=null;
                else{
                    Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 400,300, true);
                    msg.obj=newBmp;
                }
                handler1.sendMessage(msg);
            }
        }.start();
    }

}
