package com.zdj.souye;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.zdj.souye.view.souye;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main0);
        AlphaAnimation animation=new AlphaAnimation(1.0f,1.0f);
        animation.setDuration(2000);
        linearLayout= (LinearLayout) findViewById(R.id.ll_start);
        linearLayout.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
                                           @Override
                                           public void onAnimationStart(Animation animation) {
                                               linearLayout.setVisibility(View.VISIBLE);
                                           }

                                           @Override
                                           public void onAnimationEnd(Animation animation) {
                                               linearLayout.setVisibility(View.GONE);  //隐藏起来，不需要任何布局空间
                                           }

                                           @Override
                                           public void onAnimationRepeat(Animation animation) {
                                               //linearLayout.setVisibility(View.GONE);
                                           }
                                       }
        );
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.ll, new souye());
        ft.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            Intent i= new Intent(Intent.ACTION_MAIN);  //主启动，不期望接收数据
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       //新的activity栈中开启，或者已经存在就调到栈前
            i.addCategory(Intent.CATEGORY_HOME);            //添加种类，为设备首次启动显示的页面
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
}
