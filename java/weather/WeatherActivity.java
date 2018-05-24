package weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zdj.souye.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
public class WeatherActivity extends Activity{
    private ImageButton back_more;
    private TextView title_weather_more;
    private ListView lvFutureWeather;
    public static final int SHOW_PESPONSE = 1;
    private List<Weather> data;


    private void parseWithJSON(String response){
        data = new ArrayList<Weather>();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(response);
        String resultcode = obj.get("resultcode").getAsString();
        if(resultcode != null && resultcode.equals("200")){
            JsonObject resultObj = obj.get("result").getAsJsonObject();
            JsonArray futureWeatherArray = resultObj.get("future").getAsJsonArray();
            for(int i=0;i<futureWeatherArray.size();i++){
                Weather weather = new Weather();
                JsonObject weatherObject = futureWeatherArray.get(i).getAsJsonObject();
                weather.setDayOfWeek(weatherObject.get("week").getAsString());
                weather.setDate(weatherObject.get("date").getAsString());
                weather.setTemperature(weatherObject.get("temperature").getAsString());
                weather.setWeather(weatherObject.get("weather").getAsString());
                data.add(weather);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setListeners();
        initView();
    }
    private void setListeners() {
        back_more= (ImageButton) findViewById(R.id.id_back_week);
        lvFutureWeather = (ListView) findViewById(R.id.lvFutureWeather);
        title_weather_more= (TextView) findViewById(R.id.title_weather_more);
    }
    private void initView() {
                Bundle bundle=this.getIntent().getExtras();
                String city=bundle.getString("city");
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
        back_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SHOW_PESPONSE:
                    String response = (String) msg.obj;
                    if(response != null){
                        parseWithJSON(response);
                        WeatherAdapter weatherAdapter = new WeatherAdapter(WeatherActivity.this, R.layout.activity_weather_listitem,data);
                        lvFutureWeather.setAdapter(weatherAdapter);
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1);
                        scaleAnimation.setDuration(1000);
                        LayoutAnimationController animationController = new LayoutAnimationController(scaleAnimation,0.6f);
                        lvFutureWeather.setLayoutAnimation(animationController);
                    }
                    break;
                default:break;
            }
        }
    };
    public class WeatherAdapter extends ArrayAdapter<Weather> {
        private int resourceId;
        public WeatherAdapter(Context context, int textViewResourceId, List<Weather> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }
        public View getView(int position,View convertView,ViewGroup viewgroup){
            Weather weather = getItem(position);
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
                viewHolder.tvDayOdWeek =  (TextView) convertView.findViewById(R.id.tvDayofWeek);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                viewHolder.tvTemperature = (TextView) convertView.findViewById(R.id.tvTemperature);
                viewHolder.tvWeather = (TextView) convertView.findViewById(R.id.tvWeather);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.tvDayOdWeek.setText(weather.getDayOfWeek());
            viewHolder.tvDate.setText(weather.getDate());
            viewHolder.tvTemperature.setText(weather.getTemperature());
            viewHolder.tvWeather.setText(weather.getWeather());
            return convertView;
        }

        private class ViewHolder{
            TextView tvDayOdWeek;
            TextView tvDate;
            TextView tvTemperature;
            TextView tvWeather;
        }


    }

}
