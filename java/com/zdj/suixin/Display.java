package com.zdj.suixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;

/**
 * Created by Administrator on 2017/8/20.
 */

public class Display extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        Intent intent=getIntent();
        Constant constant=intent.getParcelableExtra("constant");
        TextView textView=findViewById(R.id.title);
        TextView textView1=findViewById(R.id.city);
        ImageView img=findViewById(R.id.imageView2);
        textView.setText(constant.getTime());
        textView1.setText(constant.getCity());
        img.setImageBitmap(constant.getMimage());
    }
    public void back(View v){
        finish();
    }
}
