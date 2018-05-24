package com.zdj.souye.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdj.go.map.LocationMap;
import com.zdj.souye.R;
import com.zdj.suixin.RecyclerActivity;

/**
 */

public class souye extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.souye,null);
        init();
        return view;
    }

    public void init(){
        ImagebtListener listener=new ImagebtListener();
        (view.findViewById(R.id.imageButton0)).setOnClickListener(listener);
        (view.findViewById(R.id.imageButton1)).setOnClickListener(listener);
        (view.findViewById(R.id.imageButton2)).setOnClickListener(listener);
        (view.findViewById(R.id.imageButton3)).setOnClickListener(listener);
        (view.findViewById(R.id.imageButton4)).setOnClickListener(listener);
        (view.findViewById(R.id.imageButton5)).setOnClickListener(listener);
    }
    private class ImagebtListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent;
            switch (view.getId()){
                case R.id.imageButton5:
                    intent=new Intent(getActivity(), RecyclerActivity.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton4:
                    intent=new Intent(getActivity(), com.zdj.search.jiemian.MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton3:
                    intent=new Intent(getActivity(), LocationMap.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton1:
                    intent=new Intent(getActivity(), com.zdj.jiudian.jiemian.Meishi.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton2:
                    intent=new Intent(getActivity(), com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton0:
                    intent=new Intent(getActivity(), com.example.administrator.mine.MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
