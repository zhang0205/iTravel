package com.minsu;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zdj.souye.R;
/**
 * Created by Administrator on 2017/9/30.
 */

public class ShapPopup extends PopupWindow {
    private Activity myContext;
    private View.OnClickListener onClickListener;
    private LayoutInflater inflater = null;
    private View myMenuView;
    private LinearLayout popupLL;
    private TextView shap1,shap2,shap3,shap4;
    public ShapPopup(Activity context, View.OnClickListener listener) {
        inflater=LayoutInflater.from(context);
        myMenuView = inflater.inflate(R.layout.shap_pop, null);
        this.myContext = context;
        this.onClickListener = listener;
        shap1=myMenuView.findViewById(R.id.shap1);
        shap2=myMenuView.findViewById(R.id.shap2);
        shap3=myMenuView.findViewById(R.id.shap3);
        shap4=myMenuView.findViewById(R.id.shap4);
        popupLL =  myMenuView.findViewById(R.id.popup_layout);
        shap1.setOnClickListener(onClickListener);
        shap2.setOnClickListener(onClickListener);
        shap3.setOnClickListener(onClickListener);
        shap4.setOnClickListener(onClickListener);
        setPopup();
    }
    /**
     * 设置popup的样式
     */
    private void setPopup() {
        this.setContentView(myMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimTopMiddle);
        ColorDrawable dw = new ColorDrawable(0x33000000);
        this.setBackgroundDrawable(dw);
        myMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = popupLL.getBottom();
                int left = popupLL.getLeft();
                int right = popupLL.getRight();
                int y = (int) event.getY();
                int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height || x < left || x > right) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 显示弹窗界面
     * @param view
     */
    public void show(View view) {
        showAsDropDown(view, 0, 0);
    }
}
