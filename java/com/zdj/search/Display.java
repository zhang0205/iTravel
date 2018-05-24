package com.zdj.search;

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
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 */

public class Display extends Activity {
    private ImageMemoryCache memoryCache=null;
    private Handler handler1;
    private ImageView imageView;
    boolean bool1=true;
    boolean bool2=true;
    boolean bool3=true;
    boolean bool4=true;
    boolean bool5=true;
    private ImageButton question;
    ImageButton imgbt1;
    ImageButton imgbt2;
    ImageButton imgbt3;
    ImageButton imgbt4;
    ImageButton imgbt5;
    private EditText word;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jindian);
        imgbt1=findViewById(R.id.bt1);
        imgbt2=findViewById(R.id.bt2);
        imgbt3=findViewById(R.id.bt3);
        imgbt4=findViewById(R.id.bt4);
        imgbt5=findViewById(R.id.bt5);
        word=findViewById(R.id.word);
        init();
    }
    public void send(View view){
        word.setFocusable(false);
    }
    public void iclick(View view){
        switch (view.getId()){
            case R.id.bt1:
                if (bool1){
                    imgbt1.setImageResource(R.drawable.viewed1);
                    bool1=false;
                }
                else {
                    imgbt1.setImageResource(R.drawable.view1);
                    imgbt2.setImageResource(R.drawable.view1);
                    imgbt3.setImageResource(R.drawable.view1);
                    imgbt4.setImageResource(R.drawable.view1);
                    imgbt5.setImageResource(R.drawable.view1);
                    bool5=true;
                    bool4=true;
                    bool3=true;
                    bool2=true;
                    bool1=true;
                }
                break;
            case R.id.bt2:
                if (bool2){
                    imgbt2.setImageResource(R.drawable.viewed1);
                    imgbt1.setImageResource(R.drawable.viewed1);
                    bool1=false;
                    bool2=false;
                }
                else {
                    imgbt2.setImageResource(R.drawable.view1);
                    imgbt3.setImageResource(R.drawable.view1);
                    imgbt4.setImageResource(R.drawable.view1);
                    imgbt5.setImageResource(R.drawable.view1);
                    bool5=true;
                    bool4=true;
                    bool3=true;
                    bool2=true;
                }
                break;
            case R.id.bt3:
                if (bool3){
                    imgbt3.setImageResource(R.drawable.viewed1);
                    imgbt2.setImageResource(R.drawable.viewed1);
                    imgbt1.setImageResource(R.drawable.viewed1);
                    bool1=false;
                    bool2=false;
                    bool3=false;
                }
                else {
                    imgbt3.setImageResource(R.drawable.view1);
                    imgbt4.setImageResource(R.drawable.view1);
                    imgbt5.setImageResource(R.drawable.view1);
                    bool5=true;
                    bool4=true;
                    bool3=true;
                }
                break;
            case R.id.bt4:
                if (bool4){
                    imgbt4.setImageResource(R.drawable.viewed1);
                    imgbt3.setImageResource(R.drawable.viewed1);
                    imgbt2.setImageResource(R.drawable.viewed1);
                    imgbt1.setImageResource(R.drawable.viewed1);
                    bool1=false;
                    bool2=false;
                    bool3=false;
                    bool4=false;
                }
                else {
                    imgbt4.setImageResource(R.drawable.view1);
                    imgbt5.setImageResource(R.drawable.view1);
                    bool5=true;
                    bool4=true;
                }
                break;
            case R.id.bt5:
                if (bool5){
                    imgbt5.setImageResource(R.drawable.viewed1);
                    imgbt4.setImageResource(R.drawable.viewed1);
                    imgbt3.setImageResource(R.drawable.viewed1);
                    imgbt2.setImageResource(R.drawable.viewed1);
                    imgbt1.setImageResource(R.drawable.viewed1);
                    bool1=false;
                    bool2=false;
                    bool3=false;
                    bool4=false;
                    bool5=false;
                }
                else {
                    imgbt5.setImageResource(R.drawable.view1);
                    bool5=true;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void init(){
        question=findViewById(R.id.question);
        memoryCache=new ImageMemoryCache(this);
        Intent intent=getIntent();
        ((TextView)findViewById(R.id.mudi)).setText(intent.getStringExtra("Name"));
        ((TextView)findViewById(R.id.didian)).setText(intent.getStringExtra("Address"));
        ((TextView)findViewById(R.id.introduce)).setText(intent.getStringExtra("Introduce"));
        ((TextView)findViewById(R.id.time)).setText(intent.getStringExtra("Suitable"));
        ((TextView)findViewById(R.id.phone)).setText(intent.getStringExtra("Telephone"));
        ((TextView)findViewById(R.id.price)).setText(intent.getStringExtra("PriceDesc"));
        imageView=findViewById(R.id.disimage);
        returnBitMap(intent.getStringExtra("BigImg"));
        final String phone=intent.getStringExtra("Telephone");
        if (!phone.equals("暂无!")){
            question.setVisibility(View.VISIBLE);
            question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
                    intent.setData(Uri.parse("tel:" + phone));
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
                if (bitmap!=null){
                    Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 600,214, true);
                    msg.obj=newBmp;
                    handler1.sendMessage(msg);
                }
            }
        }.start();
    }
}
