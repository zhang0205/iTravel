package com.zdj.myapplication.fly.caldroid;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zdj.souye.R;
/**
 * Created by Administrator on 2017/9/23.
 */

public class PopWindow implements View.OnClickListener,View.OnTouchListener{
    private Activity activity;
    private View.OnClickListener onClickListener;
    private View mWindow;
    private Button price1,price2,price3,price4,price5,price6,shap1,shap2,shap3,shap4,shap5;
    private Button clear,sure;
    private PopupWindow popupWindow;
    private TextView mview,shap,textView;
    private String str1="",str2="",string="";
    private ImageButton delt2;
    public PopWindow(Activity activity, View.OnClickListener onClickListener){
        LayoutInflater inflater=LayoutInflater.from(activity);
        this.activity=activity;
        this.onClickListener=onClickListener;
        mWindow=inflater.inflate(R.layout.popwindow,null);
        price1=mWindow.findViewById(R.id.price1);
        price2=mWindow.findViewById(R.id.price2);
        price3=mWindow.findViewById(R.id.price3);
        price4=mWindow.findViewById(R.id.price4);
        price5=mWindow.findViewById(R.id.price5);
        price6=mWindow.findViewById(R.id.price6);
        shap1=mWindow.findViewById(R.id.shap1);
        shap2=mWindow.findViewById(R.id.shap2);
        shap3=mWindow.findViewById(R.id.shap3);
        shap4=mWindow.findViewById(R.id.shap4);
        shap5=mWindow.findViewById(R.id.shap5);
        mview=mWindow.findViewById(R.id.view);
        shap=mWindow.findViewById(R.id.shap);
        clear=mWindow.findViewById(R.id.clear);
        sure=mWindow.findViewById(R.id.sure);
        textView=activity.findViewById(R.id.price);
        delt2=activity.findViewById(R.id.delt2);
        price1.setOnClickListener(this);
        price2.setOnClickListener(this);
        price3.setOnClickListener(this);
        price4.setOnClickListener(this);
        price5.setOnClickListener(this);
        price6.setOnClickListener(this);
        shap1.setOnClickListener(this);
        shap2.setOnClickListener(this);
        shap3.setOnClickListener(this);
        shap4.setOnClickListener(this);
        shap5.setOnClickListener(this);
        clear.setOnClickListener(this);
        sure.setOnClickListener(this);
        popupWindow=new PopupWindow(mWindow,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.ccc));
        popupWindow.setBackgroundDrawable(dw);
        mWindow.setOnTouchListener(this);
        delt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                delt2.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.price1:mview.setText("¥150以下");str1="¥150以下";break;
            case R.id.price2:mview.setText("¥150-300");str1="¥150-300";break;
            case R.id.price3:mview.setText("¥300-450");str1="¥300-450";break;
            case R.id.price4:mview.setText("¥450-600");str1="¥450-600";break;
            case R.id.price5:mview.setText("¥600-1000");str1="¥600-1000";break;
            case R.id.price6:mview.setText("¥1000以上");str1="¥1000以上";break;
            case R.id.shap1:shap.setText("快捷连锁");str2="快捷连锁";break;
            case R.id.shap2:shap.setText("二星/经济");str2="二星/经济";break;
            case R.id.shap3:shap.setText("三星/舒适");str2="三星/舒适";break;
            case R.id.shap4:shap.setText("四星/高档");str2="四星/高档";break;
            case R.id.shap5:shap.setText("五星/豪华");str2="五星/豪华";break;
            case R.id.clear:
                str1="";
                str2="";
                mview.setText("");shap.setText("");
                break;
            case R.id.sure:
                popupWindow.dismiss();
                string=str1+str2;
                textView.setText(string);
                delt2.setVisibility(View.VISIBLE);
                break;
            default:onClickListener.onClick(view);break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int height = mWindow.findViewById(R.id.pop_layout).getTop();
        int y=(int) motionEvent.getY();
        if(motionEvent.getAction()==MotionEvent.ACTION_UP){
            if(y<height){
                popupWindow. dismiss();
            }
        }
        return true;
    }
    public void show(){
        //得到当前activity的rootView
        View rootView=((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
