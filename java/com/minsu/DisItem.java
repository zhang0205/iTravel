package com.minsu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;

/**
 * Created by Administrator on 2017/10/1.
 */

public class DisItem extends Activity {
    private Intent intent;
    private ConstantMinsu constantMinsu;
    private ConstantBitmap constantBitmap;
    private ImageView imageView,imageView1;
    private TextView price,name,shap,ren,zhang,zhang1,dis;
    private String phone=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disexam1);
        intent=getIntent();
        constantMinsu=intent.getParcelableExtra("constantMinsu");
        constantBitmap=intent.getParcelableExtra("constantBitmap");
        imageView=findViewById(R.id.photo_click);
        imageView1=findViewById(R.id.photo_click1);
        price=findViewById(R.id.price_click);
        name=findViewById(R.id.name_click);
        shap=findViewById(R.id.shap_click);
        ren=findViewById(R.id.ren);
        zhang=findViewById(R.id.zhang);
        zhang1=findViewById(R.id.zhang1);
        dis=findViewById(R.id.dis_click);
        price.setText(constantMinsu.getPrice());
        name.setText(constantMinsu.getName());
        shap.setText(constantMinsu.getShap());
        dis.setText(constantMinsu.getDis());
        imageView.setImageBitmap(constantMinsu.getMimage());
        imageView1.setImageBitmap(constantBitmap.getMimage1());
        zhang.setText("");
        zhang1.setText("");
        phone=constantMinsu.getPhone();
    }
    public void back(View view){
        this.finish();
    }
    public void textClick(View view){
        Intent intent = new Intent(Intent.ACTION_CALL);// 调用系统的CALL
        intent.setData(Uri.parse("tel:"+phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
}
