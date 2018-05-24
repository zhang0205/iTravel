package com.zdj.suixin;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdj.souye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2017/8/20.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Map<String , Object>> mDatas;
    private List<Integer> mHeights;
    /**
     * ItemClick的回调接口
     * @author zhy
     *
     */
    public interface OnItemClickLitener
    {
        boolean onItemClick(View view, int position);
        boolean onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public RecyclerAdapter(Context context,List<Map<String , Object>> mDatas)
    {
        this.mDatas=mDatas;
        mHeights=new ArrayList<>();
        for (int i=0;i<mDatas.size();i++){
            mHeights.add( (int) (100 + Math.random() * 300));
        }
        mInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }
        ImageView mImg;
        TextView mContent;
        TextView mTime;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.item_adapter,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mImg =view.findViewById(R.id.img);
        viewHolder.mContent=view.findViewById(R.id.content);
        viewHolder.mTime=view.findViewById(R.id.timer);
        return viewHolder;
    }
    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        ViewGroup.LayoutParams lp = viewHolder.mContent.getLayoutParams();
        lp.height = mHeights.get(i);
        viewHolder.mContent.setLayoutParams(lp);
        HashMap<String , Object> hashMap= (HashMap<String, Object>) mDatas.get(i);
        Bitmap bitmap=(Bitmap) hashMap.get("T_BLOB");
        String time=(String)hashMap.get("time");
        String content=(String)hashMap.get("city");
        viewHolder.mImg.setImageBitmap(bitmap);
        viewHolder.mTime.setText(time);
        viewHolder.mContent.setText(content);
        //如果设置了回调事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, pos);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(viewHolder.itemView, pos);
                    return false;
                }
            });
        }
    }

    public void setDatas(List<Map<String , Object>> mDatas)
    {
        this.mDatas=mDatas;
        System.out.println("*********setDatas"+this.mDatas+"***********mDatas"+mDatas);
        mHeights=new ArrayList<>();
        for (int i=0;i<mDatas.size();i++){
            mHeights.add( (int) (100 + Math.random() * 300));
        }
    }

    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}
