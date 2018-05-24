package com.zdj.myapplication.fly.caldroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zdj.souye.R;
/**
 */

public class Guanjian extends Activity {
    String key="如家";
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanjianzi);
        textView=findViewById(R.id.textView);
    }
    public void Back(View view){
        switch (view.getId()){
            case R.id.back:
                Intent intent1=new Intent();
                setResult(-2,intent1);
                Guanjian.this.finish();
                break;
            case R.id.finish:
                Intent intent=new Intent();
                key=textView.getText().toString();
                if (!key.equals("")){
                    intent.putExtra("key",key);
                    setResult(2,intent);
                    Guanjian.this.finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(-2,intent);
        Guanjian.this.finish();
    }

    public void word_select(View view){
        switch (view.getId()){
            case R.id.id_1:textView.setText("七天");break;
            case R.id.id_2:textView.setText("速8");break;
            case R.id.id_3:textView.setText("如家");break;
            case R.id.id_4:textView.setText("机场");break;
            case R.id.id_5:textView.setText("温泉");break;
            case R.id.id_6:textView.setText("国贸");break;
            case R.id.id_7:textView.setText("云栖");break;
            case R.id.id_8:textView.setText("怀柔");break;
            case R.id.id_9:textView.setText("锦江之星");break;
            case R.id.id_10:textView.setText("华佳");break;
            case R.id.id_11:textView.setText("七天(铂涛)");break;
            case R.id.id_12:textView.setText("莫泰");break;
            case R.id.id_13:textView.setText("金广快捷");break;
            case R.id.id_14:textView.setText("速8");break;
            case R.id.id_15:textView.setText("布丁");break;
            case R.id.id_16:textView.setText("洲际(IHG)");break;
            case R.id.id_17:textView.setText("中关村、五道口");break;
            case R.id.id_18:textView.setText("天安门、王府井地区");break;
            case R.id.id_19:textView.setText("首都机场、新国展地区");break;
            case R.id.id_20:textView.setText("国贸地区、新国际");break;
            case R.id.id_21:textView.setText("东直门、王体");break;
            case R.id.id_22:textView.setText("亚运村、奥体中心");break;
            case R.id.id_23:textView.setText("燕莎、三里屯商业区");break;
            case R.id.id_24:textView.setText("西单、金融街地区");break;
            case R.id.id_25:textView.setText("四合院");break;
            case R.id.id_26:textView.setText("休闲度假");break;
            case R.id.id_27:textView.setText("亲子酒店");break;
            case R.id.id_28:textView.setText("浪漫情侣");break;
            case R.id.id_29:textView.setText("商务出行");break;
            case R.id.id_30:textView.setText("公寓");break;
            case R.id.id_31:textView.setText("客栈");break;
            case R.id.id_32:textView.setText("美食林");break;
            case R.id.id_33:textView.setText("西城区");break;
            case R.id.id_34:textView.setText("东城区");break;
            case R.id.id_35:textView.setText("朝阳区");break;
            case R.id.id_36:textView.setText("房山区");break;
            case R.id.id_37:textView.setText("海淀区");break;
            case R.id.id_38:textView.setText("石景山区");break;
            case R.id.id_39:textView.setText("顺义区");break;
            case R.id.id_40:textView.setText("丰台区");break;
            case R.id.id_41:textView.setText("王府井");break;
            case R.id.id_42:textView.setText("东直门");break;
            case R.id.id_43:textView.setText("前门");break;
            case R.id.id_44:textView.setText("朝阳门");break;
            case R.id.id_45:textView.setText("农业展览馆");break;
            case R.id.id_46:textView.setText("西单");break;
            case R.id.id_47:textView.setText("北京南站");break;
            case R.id.id_48:textView.setText("三元桥");break;
            case R.id.id_49:textView.setText("南苑机场");break;
            case R.id.id_50:textView.setText("首都机场");break;
            case R.id.id_51:textView.setText("国际T3航");break;
            case R.id.id_52:textView.setText("北京火车站");break;
            case R.id.id_53:textView.setText("北京西站");break;
            case R.id.id_54:textView.setText("北京南站");break;
            case R.id.id_55:textView.setText("北京北站");break;
            case R.id.id_56:textView.setText("京东火车站");break;
        }
    }
}
