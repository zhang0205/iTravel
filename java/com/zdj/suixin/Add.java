package com.zdj.suixin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zdj.souye.R;

import java.util.Calendar;
/**
 * Created by Administrator on 2017/8/20.
 */

public class Add extends Activity {
    final static int CAMERA_RESULT = 3;//声明一个常量，代表结果码
    private ImageView imageView;
    private static final int PHOTO_RESOULT = 2;// 结果
    private Bitmap photo=null;
    private Constant constant;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        imageView=findViewById(R.id.id_img);
    }
    //手机拍照获取图片
    public void click3(View v)
    {
        //实例化Intent对象,使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //开启相机，传入上面的Intent对象
        startActivityForResult(intent, CAMERA_RESULT);
    }
    public void back(View v){
        finish();
    }
    public void finish(View v){
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        String timer=mYear+"."+mMonth+"."+mDay;
        String content=((EditText)findViewById(R.id.id_content)).getText().toString();
        constant=new Constant(photo,content,timer);
        Intent intent=new Intent();
        intent.putExtra("contest",constant);
        setResult(10,intent);
        finish();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Bitmap bm = extras.getParcelable("data");
                // 获得图片的宽高
                int width = bm.getWidth();
                int height = bm.getHeight();
                // 设置想要的大小
                int newWidth = 100;
                int newHeight = 100;
                // 计算缩放比例
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // 得到新的图片
                photo = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                        true);
                imageView.setImageBitmap(photo);//把图片显示在ImageView控件上
            }
        }
        else if(resultCode==RESULT_OK)
        {
            Bundle extras=intent.getExtras();//从Intent中获取附加值
            Bitmap bm=(Bitmap) extras.get("data");//从附加值中获取返回的图像
            // 获得图片的宽高
            int width = bm.getWidth();
            int height = bm.getHeight();
            // 设置想要的大小
            int newWidth = 100;
            int newHeight = 100;
            // 计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            photo = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                    true);
            imageView.setImageBitmap(photo);//显示图像
        }
    }
}
