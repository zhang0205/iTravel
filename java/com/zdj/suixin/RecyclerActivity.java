package com.zdj.suixin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.zdj.myapplication.SqlBitmap;
import com.zdj.souye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RecyclerActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private SqlBitmap sqlBitmap;
    private List<Map<String , Object>> mDatas;
    private long[] arry=new long[200];
    private HashMap<String , Object> hashMap;
    public String T_ID = "_id";//字段名
    public String T_BLOB = "T_BLOB";//字段名
    public String CITY="city";//字段名
    public String TIME="time"; //字段名
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrecycleractivity);
        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        sqlBitmap=new SqlBitmap(getApplicationContext());
        mDatas=sqlBitmap.getData_xin();
        //设置适配器
        if (mDatas!=null){
            for (int i=0;i<mDatas.size();i++){
                hashMap= (HashMap<String, Object>) mDatas.get(i);
                arry[i]= (long) hashMap.get(T_ID);
            }
            mAdapter = new RecyclerAdapter(this,mDatas);
        }
        else{
            mDatas=new ArrayList<>();
            mAdapter = new RecyclerAdapter(this,mDatas);
        }
        mAdapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener()
        {
            @Override
            public boolean onItemClick(View view, int position)
            {
                Intent intent=new Intent(getApplicationContext(),Display.class);
                HashMap<String, Object> hash;
                hash=sqlBitmap.getItem_xin(arry[position]);
                Constant constant=new Constant((Bitmap) hash.get(T_BLOB),(String) hash.get(CITY),(String) hash.get(TIME));
                intent.putExtra("constant",constant);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(RecyclerActivity.this).setTitle("系统提示")
                        .setMessage("请确认删除该记录！")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqlBitmap.delete_xin(arry[position]);
                                mAdapter.removeData(position);
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                return false;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    public void backsouye(View v){
        finish();
    }
    public void add(View v){
        Intent intent=new Intent(getApplicationContext(),Add.class);
        startActivityForResult(intent,20);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10){
            Constant constant=data.getParcelableExtra("contest");
            Bitmap bitmap=constant.getMimage();
            String timer=constant.getTime();
            String content=constant.getCity();
            long id=sqlBitmap.createData_xin(bitmap,content,timer);
            mDatas=sqlBitmap.getData_xin();
            for (int i=0;i<mDatas.size();i++){
                hashMap= (HashMap<String, Object>) mDatas.get(i);
                arry[i]= (long) hashMap.get(T_ID);
            }
            mAdapter.setDatas(mDatas);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
