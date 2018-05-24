package com.minsu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;
/**
 */

public class Add extends Activity {
    private ImageView imageView;
    private ImageView imageView1;
    private TextView show,miaoshu;
    private TextView price,shap;
    private EditText dis,phone,name;
    private Bitmap photo=null;
    private Bitmap photo1=null;
    private ImageButton bt1,bt2;
    private PricePopup pricePopup;
    private ShapPopup shapPopup;
    private static final int PHOTO_RESOULT = 2;// 结果
    private Button finishadd;
    private ConstantMinsu constantMinsu;
    private ConstantBitmap constantBitmap;
    private TextView root;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add5);
        imageView=findViewById(R.id.photo);
        imageView1=findViewById(R.id.photo1);
        show=findViewById(R.id.show);
        price=findViewById(R.id.price);
        shap=findViewById(R.id.shap);
        miaoshu=findViewById(R.id.miaoshu);
        bt1=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        dis=findViewById(R.id.dis);
        phone=findViewById(R.id.phone);
        name=findViewById(R.id.name);
        root=findViewById(R.id.root);
        finishadd=findViewById(R.id.finishadd);
        Intent intent=getIntent();
        root.setText(intent.getStringExtra("name"));
        finishadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                constantMinsu=new ConstantMinsu(photo,price.getText().toString(),shap.getText().toString(),
                        dis.getText().toString(),phone.getText().toString(),name.getText().toString());
                constantBitmap=new ConstantBitmap(photo1);
                intent.putExtra("constantMinsu",constantMinsu);
                intent.putExtra("constantBitmap",constantBitmap);
                setResult(40,intent);
                Add.this.finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 3);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 4);
            }
        });
    }
    public void backadd(View view){
        this.finish();
    }
    public void write(View view){
        switch (view.getId()){
            case R.id.liner1:
                pricePopup=new PricePopup(Add.this,listener1);
                pricePopup.show(miaoshu);
                break;
            case R.id.liner2:
                shapPopup=new ShapPopup(Add.this,listener2);
                shapPopup.show(miaoshu);
                break;
        }
    }

    private View.OnClickListener listener1=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.home1:price.setText("¥200以下");
                    pricePopup.dismiss();
                    bt1.setImageResource(R.drawable.up);
                    break;
                case R.id.home2:price.setText("¥200-300");
                    pricePopup.dismiss();
                    bt1.setImageResource(R.drawable.up);break;
                case R.id.home3:price.setText("¥301-400");
                    pricePopup.dismiss();
                    bt1.setImageResource(R.drawable.up);break;
                case R.id.home4:price.setText("¥401-500");
                    pricePopup.dismiss();
                    bt1.setImageResource(R.drawable.up);break;
                case R.id.home5:price.setText("¥501-800");
                    pricePopup.dismiss();
                    bt1.setImageResource(R.drawable.up);break;
                case R.id.home6:price.setText("¥800以上");
                    pricePopup.dismiss();
                    bt1.setImageResource(R.drawable.up);break;
            }
        }
    };
    private View.OnClickListener listener2=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.shap1:shap.setText("一室");
                    shapPopup.dismiss();
                    bt2.setImageResource(R.drawable.up);
                    break;
                case R.id.shap2:shap.setText("二室");
                    shapPopup.dismiss();
                    bt2.setImageResource(R.drawable.up);break;
                case R.id.shap3:shap.setText("三室");
                    shapPopup.dismiss();
                    bt2.setImageResource(R.drawable.up);break;
                case R.id.shap4:shap.setText("四室以上");
                    shapPopup.dismiss();
                    bt2.setImageResource(R.drawable.up);break;
            }
        }
    };
    public void back(View view){
        this.finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 3){
            Bundle extras = intent.getExtras();
            Bitmap bm = extras.getParcelable("data");
            photo=bm;
            imageView.setImageBitmap(photo);//把图片显示在ImageView控件上
        }
        else if (requestCode == 4){
            Bundle extras = intent.getExtras();
            Bitmap bm = extras.getParcelable("data");
            photo1=bm;
            imageView1.setImageBitmap(photo1);//把图片显示在ImageView控件上
        }
//        if (requestCode == PHOTO_RESOULT) {
//            Bundle extras = intent.getExtras();
//
//            if (extras != null) {
//                Bitmap bm = extras.getParcelable("data");
//                int width = bm.getWidth();
//                int height = bm.getHeight();
//                int newWidth uhrur=imageView.getWidth();
//                int newHeight = imageView.getHeight();
//                float scaleWidth =((float) newWidth) /width  ;
//                float scaleHeight =((float) newHeight)  /height ;
//                Matrix matrix = new Matrix();
//                matrix.postScale(scaleWidth, scaleHeight);
//                photo = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
//                imageView.setImageBitmap(photo);//把图片显示在ImageView控件上
//                photo=bm;
//                imageView.setImageBitmap(bm);//把图片显示在ImageView控件上
//            }
//        }
//        else if(resultCode==RESULT_OK) {
//            Bundle extras=intent.getExtras();//从Intent中获取附加值
//            Bitmap bm=(Bitmap) extras.get("data");//从附加值中获取返回的图像
//            int width = bm.getWidth();
//            int height = bm.getHeight();
//            int newWidth = imageView.getWidth();
//            int newHeight = imageView.getHeight();
//            float scaleWidth =((float) newWidth) /width  ;
//            float scaleHeight =((float) newHeight)  /height ;
//            Matrix matrix = new Matrix();
//            matrix.postScale(scaleWidth, scaleHeight);
//            photo = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
//            imageView.setImageBitmap(photo);//显示图像
//            photo=bm;
//            imageView.setImageBitmap(bm);//显示图像
//        }
    }
}
