package com.zdj.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zdj.souye.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private ListView listView;
    private List<Map<String,String>> data=null;
    private List<Map<String,Bitmap>> list=null;
    private HashMap<String,String> hashMap;
    private HashMap<String,Bitmap> hashMap1;
    private int SHOW_PESPONSE=1;
    private Handler handler;

    private ImageMemoryCache memoryCache=null;
    private Handler handler1;
    private static int count=1;//用来计数请求多少次网络图片

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {//这个handleMessage用于处理网络请求的景点信息字符串
                if (msg.what == SHOW_PESPONSE) {
                    String response = (String) msg.obj;
                    if (response != null) {
                        parseWithJSON(response);
                        Message msg1=new Message();
                        msg1.what=-2;
                        handler1.sendMessage(msg1);
                    }
                }
            }
        };
        handler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap;
                if (msg.what==-2&&data.size()>0){
                    hashMap= (HashMap<String, String>) data.get(0);
                    getBitmap(hashMap.get("PicImg"));
                    count=1;
                }else {
                    if ((msg.what-1)<data.size()){
                        hashMap1=new HashMap<>();
                        bitmap= (Bitmap) msg.obj;
                        hashMap1.put("PicImg",bitmap);
                        list.add(hashMap1);
                    }
                    if (msg.what<data.size()){
                        hashMap= (HashMap<String, String>) data.get(msg.what);
                        getBitmap(hashMap.get("PicImg"));
                    }
                    if (msg.what==data.size()){
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setAdapter(new MyAdapter());
                    }
                }
            }
        };
        init();
    }
    public void click(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * 获取网络景点图片
     * @param url 图片的地址
     */
    public void getBitmap(final String url) {
        // 从内存缓存中获取图片
        new Thread(){
            @Override
            public void run() {
                Bitmap bitmap = memoryCache.getBitmapFromCache(url);
                if (bitmap == null) {
                    bitmap = ImageGetFromHttp.downloadBitmap(url);
                    if (bitmap != null) {
                        memoryCache.addBitmapToCache(url, bitmap);
                        Message msg=new Message();
                        msg.what=count;
                        Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
                        msg.obj=newBmp;
                        count++;
                        handler1.sendMessage(msg);
                    }
                }
            }
        }.start();
    }

    public void init() { //初始化控件
        listView = (ListView) findViewById(R.id.listView);
        memoryCache=new ImageMemoryCache(this);
        data = new ArrayList<>();
        list=new ArrayList<>();
        Intent intent=getIntent();
        String city_name=intent.getStringExtra("city");
        try {
            city_name = URLEncoder.encode(city_name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String weatherUrl = "http://v.juhe.cn/tour_v2.0/jingqushuju.php?area="+city_name+"&name=&page=&pagesize=&key=6cdcc154163a9d4f70bece883439f3e8";
        HttpUtil.sendHttpRequest(weatherUrl, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message = new Message();
                message.what = SHOW_PESPONSE;
                message.obj = response.toString();
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("访问失败");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,Display.class);
                hashMap= (HashMap<String, String>) data.get(i);
                String name=hashMap.get("Name");
                String price=hashMap.get("PriceDesc");
                String introduce=hashMap.get("Introduce");
                String img=hashMap.get("BigImg");
                String suit=hashMap.get("Suitable");
                String address=hashMap.get("Address");
                String phone=hashMap.get("Telephone");

                intent.putExtra("Name",name);
                intent.putExtra("PriceDesc",price);
                intent.putExtra("Introduce",introduce);
                intent.putExtra("BigImg",img);
                intent.putExtra("Suitable",suit);
                intent.putExtra("Address",address);
                intent.putExtra("Telephone",phone);
                startActivity(intent);
            }
        });
    }

    /**
     * 解析景点信息
     * @param response 请求解析的地址
     */
    private void parseWithJSON(String response){
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(response);
        String resultcode = obj.get("reason").getAsString();
        if(resultcode != null && resultcode.equals("success")){
            JsonObject resultObj = obj.get("result").getAsJsonObject();
            JsonArray futureWeatherArray = resultObj.get("value").getAsJsonArray();
            HashMap<String,String> hashMap;
            for(int i=0;i<futureWeatherArray.size();i++){
                JsonObject weatherObject = futureWeatherArray.get(i).getAsJsonObject();
                hashMap=new HashMap<>();
                hashMap.put("Name",weatherObject.get("Name").getAsString());
                hashMap.put("Address",weatherObject.get("Address").getAsString());
                hashMap.put("Introduce",weatherObject.get("Introduce").getAsString());
                hashMap.put("Telephone",weatherObject.get("Telephone").getAsString());
                hashMap.put("PriceDesc",weatherObject.get("PriceDesc").getAsString());
                hashMap.put("Suitable",weatherObject.get("Suitable").getAsString());
                String urlbig=weatherObject.get("BigImg").getAsString();
                String[] urls=urlbig.split(",");
                hashMap.put("BigImg",urls[0]);
                hashMap.put("PicImg",weatherObject.get("PicImg").getAsString());
                data.add(hashMap);
            }
        }
    }

   class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1;
            if (view==null){
                view1= View.inflate(getApplicationContext(),R.layout.avtivity_list_item,null);
            }else {
                view1=view;
            }
             hashMap= (HashMap<String, String>) data.get(i);
             hashMap1= (HashMap<String, Bitmap>) list.get(i);
             ImageView img=view1.findViewById(R.id.image);
             TextView name=view1.findViewById(R.id.name);
             TextView areaname=view1.findViewById(R.id.areaname);
             TextView mtelephone=view1.findViewById(R.id.telephone);
            img.setImageBitmap(hashMap1.get("PicImg"));
            name.setText(hashMap.get("Name"));
            areaname.setText(hashMap.get("Address"));
            mtelephone.setText(hashMap.get("Telephone"));
            return view1;
        }
    }
}
