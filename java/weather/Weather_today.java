package weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.zdj.souye.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class Weather_today extends Activity {
    private String city;
    private String date;
    private String week;
    private String temperature;
    private String weather;
    private String uv_index;
    private String dressing_advice;
    private String travel_index;
    private Button btnQuery_today;
    private TextView etCity_today;
    private TextView today_tem;
    private TextView today_date;
    private TextView weekcity;
    private TextView today_uv_index;
    private TextView today_travel_index;
    private TextView today_advice;
    private ImageButton yuyin_today;
    private Button today_more;
    private ImageButton back_today;
    private ImageView weather_img;
    private ImageView weather_img2;
    private TextView weather_type;
    public static final int SHOW_PESPONSE = 1;
    private void parseWithJSON(String response){
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(response);
        String resultcode = obj.get("resultcode").getAsString();
        if(resultcode != null && resultcode.equals("200")){
            JsonObject resultObj = obj.get("result").getAsJsonObject();
            JsonObject todayweather=resultObj.get("today").getAsJsonObject();
            city=todayweather.get("city").getAsString();
            date=todayweather.get("date_y").getAsString();
            week=todayweather.get("week").getAsString();
            temperature=todayweather.get("temperature").getAsString();
            uv_index=todayweather.get("uv_index").getAsString();
            dressing_advice=todayweather.get("dressing_advice").getAsString();
            travel_index=todayweather.get("travel_index").getAsString();
            weather=todayweather.get("weather").getAsString();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detailed);
        String city="天津";
        try{
            city = URLEncoder.encode(city, "utf-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String weatherUrl = "http://v.juhe.cn/weather/index?format=2&cityname="+city+"&key=7e84ab6d997617baaf5fd7ef13a0b005";
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
        setListeners();
        initView();
    }
    private void setListeners() {
        btnQuery_today = (Button) findViewById(R.id.btnQuery_today);
        etCity_today= (TextView) findViewById(R.id.etCity_today);
        today_tem= (TextView) findViewById(R.id.today_tem);
        today_date= (TextView) findViewById(R.id.today_date);
        weekcity= (TextView) findViewById(R.id.today_weekcity);
        today_uv_index= (TextView) findViewById(R.id.today_uv_index);
        today_travel_index= (TextView) findViewById(R.id.today_travel_index);
        today_advice= (TextView) findViewById(R.id.today_advice);
        today_more= (Button) findViewById(R.id.today_more);
        yuyin_today= (ImageButton) findViewById(R.id.yuyin_today);
        back_today= (ImageButton) findViewById(R.id.id_back_today);
        weather_img= (ImageView) findViewById(R.id.weather_img);
        weather_img2= (ImageView) findViewById(R.id.weather_img2);
        weather_type= (TextView) findViewById(R.id.weather_type);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/SIMYOU.TTF");
        weather_type.setTypeface(face);
        today_tem.setTypeface(face);
        today_date.setTypeface(face);
        weekcity.setTypeface(face);
        today_uv_index.setTypeface(face);
        today_travel_index.setTypeface(face);
        today_advice.setTypeface(face);
    }
    private void initView(){
        back_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        today_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Weather_today.this,WeatherActivity.class);
                intent.putExtra("city",etCity_today.getText().toString().trim());
                startActivity(intent);
            }
        });
        yuyin_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech(Weather_today.this);
            }
        });
        btnQuery_today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String city = etCity_today.getText().toString();
                try{
                    city = URLEncoder.encode(city, "utf-8");
                }catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                String weatherUrl = "http://v.juhe.cn/weather/index?format=2&cityname="+city+"&key=7e84ab6d997617baaf5fd7ef13a0b005";
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
            }
        });
    }
    public void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    String result = parseVoice(recognizerResult.getResultString());
                    etCity_today.setText(result);
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();

    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }

        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }
    public int weatherwhat(String weathers){
        int flag;
        if(weathers.contains("雪")){
            flag=1;
        }else{
            if (weathers.contains("雨")){
                flag=2;
            }else{
                if (weathers.contains("多云")||weathers.contains("多云")){
                    flag=3;
                }
                else{
                    flag=4;
                }
            }
        }
        return flag;
    }
    public void changeimg(String weathers){
        if(weathers.contains("转")){
            weather_img2.setVisibility(View.VISIBLE);
            String[] splits=weathers.split("转");
            switch (weatherwhat(splits[0])){
                case 1:weather_img.setBackground(this.getResources().getDrawable(R.drawable.snow));break;
                case 2:weather_img.setBackground(this.getResources().getDrawable(R.drawable.rain));break;
                case 3:weather_img.setBackground(this.getResources().getDrawable(R.drawable.cloudy));break;
                default:weather_img.setBackground(this.getResources().getDrawable(R.drawable.sun));break;
            }
            switch (weatherwhat(splits[1])){
                case 1:weather_img2.setBackground(this.getResources().getDrawable(R.drawable.snow));break;
                case 2:weather_img2.setBackground(this.getResources().getDrawable(R.drawable.rain));break;
                case 3:weather_img2.setBackground(this.getResources().getDrawable(R.drawable.cloudy));break;
                default:weather_img2.setBackground(this.getResources().getDrawable(R.drawable.sun));break;
            }
        }else{
            weather_img2.setVisibility(View.INVISIBLE);
            switch (weatherwhat(weathers)){
                case 1:weather_img.setBackground(this.getResources().getDrawable(R.drawable.snow));break;
                case 2:weather_img.setBackground(this.getResources().getDrawable(R.drawable.rain));break;
                case 3:weather_img.setBackground(this.getResources().getDrawable(R.drawable.cloudy));break;
                default:weather_img.setBackground(this.getResources().getDrawable(R.drawable.sun));break;
            }
        }
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SHOW_PESPONSE:
                    String response = (String) msg.obj;
                    if(response != null){
                        parseWithJSON(response);
                        today_tem.setText(temperature);
                        today_date.setText(date);
                        weekcity.setText(week+"   "+city);
                        today_uv_index.setText("紫外线强度:"+uv_index);
                        today_travel_index.setText("旅游推荐度:"+travel_index);
                        today_advice.setText(dressing_advice);
                        weather_type.setText(weather);
                        changeimg(weather);
                        final ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        final ScaleAnimation animation2 =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        final ScaleAnimation animation3 =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(500);//设置动画持续时间
                        today_tem.setAnimation(animation);
                        animation2.setStartOffset(400);
                        animation3.setStartOffset(800);
                        animation2.setDuration(500);//设置动画持续时间
                        animation3.setDuration(500);//设置动画持续时间
                        today_date.setAnimation(animation2);
                        weekcity.setAnimation(animation2);
                        today_uv_index.setAnimation(animation2);
                        today_travel_index.setAnimation(animation2);
                        today_advice.setAnimation(animation3);
                        weather_img.setAnimation(animation3);
                        weather_img2.setAnimation(animation3);
                        weather_type.setAnimation(animation3);
                    }
                    break;
                default:break;
            }
        }
    };
}
