package com.zdj.myapplication;

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

import com.alibaba.cloudapi.sdk.BaseApiClientBuildParam;
import com.alibaba.cloudapi.sdk.SdkConstant;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zdj.souye.R;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends Activity {
    private static AsyncApiClient_酒店查询 asyncClient_酒店查询 = null;
    private static SyncApiClient_酒店查询 syncApiClient_酒店查询=null;
    private SqlBitmap sqlBitmap=null;
    private ProgressBar progressBar;
    private Handler handle1;
    private Handler handler2;
    private Handler handler3;
    private List<Map<String , String>> data;
    private List<HashMap<String, Bitmap>> list;
    private HashMap<String , String> hashMap;
    private HashMap<String, Bitmap> hashMap1;
    private ListView listView;
    private static int count=1;
    private String[] key=new String[]{"36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53",
            "54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77",
            "78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101",
            "102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121",
            "122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","6321","139","140",
            "141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160",
            "161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180",
            "3143","181","182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199",
            "200","201","202","203","204","205","206","207","208","209","210","211","212","213","214","215","216","217","218","219",
            "220","4569","221","222","223","224","225","226","227","228","229","230","231","232","233","234","235","236","237","238",
            "239","240","241","242","243","244","245","246","247","248","249","250","251","252","253","254","255","256","257","258",
            "4580","259","260","261","262","263","264","265","266","267","268","269","270","271","272","273","274","3105","275","276",
            "277","278","279","280","281","282","283","284","285","286","287","288","289","290","291","292","293","294","295","296",
            "297","298","299","300","301","302","303","304","305","306","307","308","309","310","311","312","313","314","315","316",
            "317","318","319","320","321","322","323","324","325","326","327","328","329","330","331","332","333","334","335","336",
            "337","338","339","340","341","342","343","344","345","346","347","348","349","350","351","352","353","354","355","356",
            "357","358","359","360","361","362","363","364","365","366","3113","3114","367","368","369","370","371","372","373","374",
            "375","376","377","378","379","380","381","382","383","384","385","386","387","388","389","390","391","392","393","395",
            "396","397","398","399","400","401","402","403","404","5114","5115","5116","5117","5118","5119","5120","5121","5127","5130","6571"};
    private String[] value=new String[]{"安庆","蚌埠","巢湖","池州","滁州","阜阳","合肥","淮北","淮南","黄山","六安","马鞍山","宿州","铜陵","芜湖",
            "宣城","亳州","北京","福州","龙岩","南平","宁德",
            "莆田","泉州","三明","厦门","漳州","白银",
            "定西","甘南","嘉峪关","金昌","酒泉","兰州","临夏","陇南","平凉","庆阳","天水","武威","张掖",
            "潮州","东莞","佛山","广州","河源","惠州","江门","揭阳","茂名","梅州","清远","汕头","汕尾","韶关","深圳","阳江","云浮","湛江","肇庆",
            "中山","珠海","百色","北海","崇左","防城港","桂林","贵港","河池","贺州","来宾","柳州","南宁",
            "钦州","梧州","玉林","安顺","毕节","贵阳","六盘水","黔东南","黔南","黔西南","铜仁","遵义",
            "白沙","保亭","昌江","澄迈县","定安县","东方","海口","乐东","临高县","陵水","琼海","琼中",
            "三亚","屯昌县","万宁","文昌","五指山","儋州","三沙市","保定","沧州","承德","邯郸","衡水",
            "廊坊","秦皇岛","石家庄","唐山","邢台","张家口","安阳","鹤壁","济源","焦作","开封","洛阳",
            "南阳","平顶山","三门峡","商丘","新乡","信阳","许昌","郑州","周口","驻马店","漯河","濮阳",
            "大庆","大兴安岭","哈尔滨","鹤岗","黑河","鸡西","佳木斯","牡丹江","七台河","齐齐哈尔","双鸭山",
            "绥化","伊春","乌苏里江","鄂州","恩施","黄冈","黄石","荆门","荆州","潜江","神农架林区","十堰","随州","天门","武汉","仙桃","咸宁","襄阳",
            "孝感","宜昌","常德","长沙","郴州","衡阳","怀化","娄底","邵阳","湘潭","湘西","益阳","永州","岳阳","张家界","株洲","白城","白山","长春",
            "吉林","辽源","四平","松原","通化","延边","长白山","常州","淮安","连云港","南京","南通",
            "苏州","宿迁","泰州","无锡","徐州","盐城","扬州","镇江","抚州","赣州","吉安","景德镇","九江",
            "南昌","萍乡","上饶","新余","宜春","鹰潭","鞍山","本溪","朝阳","大连","丹东","抚顺","阜新",
            "葫芦岛","锦州","辽阳","盘锦","沈阳","铁岭","营口","兴城","阿拉善盟","巴彦淖尔市","包头","赤峰",
            "鄂尔多斯","呼和浩特","呼伦贝尔","通辽","乌海","乌兰察布市","锡林郭勒盟","兴安盟","固原",
            "石嘴山","吴忠","银川","中卫","果洛","海北","海东","海南藏族","海西","黄南","西宁","玉树","滨州",
            "德州","东营","菏泽","济南","济宁","莱芜","聊城","临沂","青岛","日照","泰安","威海","潍坊",
            "烟台","枣庄","淄博","长治","大同","晋城","晋中","临汾","吕梁","朔州","太原","忻州","阳泉",
            "运城","安康","宝鸡","汉中","商洛","铜川","渭南","西安","咸阳","延安","榆林","上海","阿坝",
            "巴中","成都","达州","德阳","甘孜","广安","广元","乐山","凉山","眉山","绵阳","南充","内江",
            "攀枝花","遂宁","雅安","宜宾","资阳","自贡","泸州","天津","阿里","昌都","拉萨","林芝","那曲",
            "日喀则","山南","阿克苏","阿拉尔","巴音郭楞","博尔塔拉","昌吉","哈密","和田","喀什","克拉玛依","克孜勒苏柯尔克孜","石河子","图木舒克",
            "吐鲁番","乌鲁木齐","五家渠","伊犁","塔城","阿勒泰","保山","楚雄","大理","德宏","迪庆","红河","昆明","丽江","临沧","怒江","曲靖","普洱","文山",
            "西双版纳","玉溪","昭通","杭州","湖州","嘉兴","金华","丽水","宁波","绍兴","台州","温州","舟山","衢州","香港","澳门","高雄","花莲","基隆","嘉义","台北","台东","台南","台中","新竹","宜兰","桃园","苗栗","彰化","南投","云林","屏东","金门","澎湖","新北"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiudianxml);
        sqlBitmap=new SqlBitmap(getApplicationContext());
        sqlBitmap=new SqlBitmap(getApplicationContext());
        if (sqlBitmap.getSize()<=0){
            for (int i=0;i<key.length;i++){
                sqlBitmap.insertData(key[i],value[i]);
            }
        }
        listView= (ListView) findViewById(R.id.ls);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        setHandler();
        init();
//        drink_id_AsyncTest();
        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        if (!city.equals("")){
            Message msg=new Message();
            msg.what=-3;
            msg.obj=city;
            handler3.sendMessage(msg);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void click(View view){
//        Intent intent=new Intent(getApplicationContext(),com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.MainActivity.class);
//        startActivity(intent);
        finish();
    }

    private void setHandler(){
        handle1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==1){
                    data=new ArrayList<>();
                    list=new ArrayList<>();
                    String result= (String) msg.obj;
                    parseWithJSON_city(result);
                    Message msg1=new Message();
                    msg1.what=-2;
                    handler2.sendMessage(msg1);
                }
            }
        };
        handler2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap;
                if (msg.what==-2&&data.size()>0){
                    hashMap= (HashMap<String, String>) data.get(0);
                    getBitmap(hashMap.get("img"));
                    count=1;
                }else if (data.size()>0){
                    if ((msg.what-1)<data.size()){
                        hashMap1=new HashMap<>();
                        bitmap= (Bitmap) msg.obj;
                        hashMap1.put("img",bitmap);
                        list.add(hashMap1);
                    }
                    if (msg.what<data.size()){
                        hashMap= (HashMap<String, String>) data.get(msg.what);
                        getBitmap(hashMap.get("img"));
                    }
                    if (msg.what==data.size()){
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setAdapter(new MyAdapter());
                    }
                }
            }
        };
        handler3=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==-3){
                    progressBar.setVisibility(View.VISIBLE);
                    String id=sqlBitmap.queryID((String) msg.obj);
                    Calendar c = Calendar.getInstance();//
                    int mYear = c.get(Calendar.YEAR); // 获取当前年份
                    int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                    int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                    int leMonth=mMonth+1;
                    if (mMonth==12){
                        mYear+=1;
                        leMonth=1;
                    }
                    String leaveData;
                    String comeData;
                    String str_mMonth="";
                    String str_leMonth="";
                    String str_Day="";
                    if (mMonth<10)
                        str_mMonth=0+""+mMonth;
                    else
                        str_mMonth=""+mMonth;
                    if (leMonth<10)
                        str_leMonth=0+""+leMonth;
                    else
                        str_leMonth=""+leMonth;
                    if (mDay<10)
                        str_Day=0+""+mDay;
                    else
                        str_Day=""+mDay;
                    leaveData=""+mYear+str_leMonth+str_Day;
                    comeData=""+mYear+str_mMonth+str_Day;
                    asyncClient_酒店查询.酒店查询("30" ,id , leaveData , comeData , new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            String result = e.getMessage();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result= getResultString(response);
                            Message msg=new Message();
                            msg.what=1;
                            msg.obj=result;
                            handle1.sendMessage(msg);
                        }
                    });
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Display.class);
                hashMap= (HashMap<String, String>) data.get(i);
                intent.putExtra("img",hashMap.get("img"));
                intent.putExtra("hotelName",hashMap.get("hotelName"));
                intent.putExtra("highestPrice",hashMap.get("highestPrice"));
                intent.putExtra("address",hashMap.get("address"));
                intent.putExtra("roomType",hashMap.get("roomType"));
                intent.putExtra("starRatedName",hashMap.get("starRatedName"));
                intent.putExtra("oneWord",hashMap.get("oneWord"));
                intent.putExtra("intro",hashMap.get("intro"));
                startActivity(intent);
            }
        });
    }
    /**
     * 获取网络景点图片
     * @param url 图片的地址
     */
    public void getBitmap(final String url){
        new Thread(){
            @Override
            public void run() {
                Bitmap bitmap = ImageGetFromHttp.downloadBitmap(url);
                Message msg=new Message();
                msg.what=count;
                count++;
                if (bitmap != null) {
                    Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 100, 75, true);
                    msg.obj=newBmp;
                }else {
                    msg.obj=null;
                }
                handler2.sendMessage(msg);
            }
        }.start();
    }
    private class MyAdapter extends BaseAdapter{

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
        public View getView(int i, View v, ViewGroup viewGroup) {
            View view;
            if (v==null)
                view=View.inflate(getApplicationContext(),R.layout.baseadapter_item,null);
            else
                view=v;
            hashMap1=list.get(i);
            ImageView imageView=view.findViewById(R.id.img);
            imageView.setImageBitmap(hashMap1.get("img"));
            hashMap= (HashMap<String, String>) data.get(i);
            TextView hotelName=view.findViewById(R.id.hotelName);
            TextView street=view.findViewById(R.id.street);
            TextView price_start=view.findViewById(R.id.price_start);
            String hotel_name=hashMap.get("hotelName");
            String street_name=hashMap.get("street");
            String price_name="价格:"+hashMap.get("highestPrice")+"  类型:"+hashMap.get("starRatedName");
            hotelName.setText(hotel_name);
            street.setText(street_name);
            price_start.setText(price_name);
            return view;
        }
    }
    private void init(){
        BaseApiClientBuildParam param = new BaseApiClientBuildParam();
        param.setAppKey("24585404");
        param.setAppSecret("50f8f08c3d7d98662bb61478b80e7579");

        /**
         * 以HTTPS方式提交请求
         * 本DEMO采取忽略证书的模式,目的是方便大家的调试
         * 为了安全起见,建议采取证书校验方式
         */
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        param.setSslSocketFactory(sslContext.getSocketFactory());
        param.setX509TrustManager(xtm);
        param.setHostnameVerifier(DO_NOT_VERIFY);
        asyncClient_酒店查询 = AsyncApiClient_酒店查询.build(param);
        syncApiClient_酒店查询=SyncApiClient_酒店查询.build(param);
    }
//    public void drink_id_AsyncTest(){
//        new Thread(){
//            @Override
//            public void run() {
//                String id=sqlBitmap.queryID("北京");
//                System.out.println("********id"+id);
//                Calendar c = Calendar.getInstance();//
//                int mYear = c.get(Calendar.YEAR); // 获取当前年份
//                int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
//                int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
//                int leMonth=mMonth+1;
//                if (mMonth==12){
//                    mYear+=1;
//                    leMonth=1;
//                }
//                String leaveData;
//                String comeData;
//                String str_mMonth="";
//                String str_leMonth="";
//                String str_Day="";
//                if (mMonth<10)
//                    str_mMonth=0+""+mMonth;
//                else
//                    str_mMonth=""+mMonth;
//                if (leMonth<10)
//                    str_leMonth=0+""+leMonth;
//                else
//                    str_leMonth=""+leMonth;
//                if (mDay<10)
//                    str_Day=0+""+mDay;
//                else
//                    str_Day=""+mDay;
//                leaveData=""+mYear+str_leMonth+str_Day;
//                comeData=""+mYear+str_mMonth+str_Day;
//                asyncClient_酒店查询.酒店查询( "30", id , leaveData , comeData , new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        String result = e.getMessage();
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String result= getResultString(response);
//                        Message msg=new Message();
//                        msg.what=1;
//                        msg.obj=result;
//                        System.out.println("*****result"+result);
//                        handle1.sendMessage(msg);
//                    }
//                });
//            }
//        }.start();
//    }

    private void parseWithJSON_city(String response){
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(response);
        JsonObject resultObj = obj.get("showapi_res_body").getAsJsonObject();
        JsonArray futureWeatherArray = resultObj.get("list").getAsJsonArray();
        HashMap<String , String> hashMap;
        for(int i=0;i<futureWeatherArray.size();i++){
            hashMap=new HashMap<>();
            JsonObject weatherObject = futureWeatherArray.get(i).getAsJsonObject();
            hashMap.put("highestPrice",weatherObject.get("highestPrice").getAsString());
            hashMap.put("starRatedName",weatherObject.get("starRatedName").getAsString());
            hashMap.put("street",weatherObject.get("street").getAsString());
            hashMap.put("img",weatherObject.get("img").getAsString());
            hashMap.put("hotelName",weatherObject.get("hotelName").getAsString());
            hashMap.put("intro",weatherObject.get("intro").getAsString());
            hashMap.put("roomType",weatherObject.get("roomType").getAsString());
            hashMap.put("address",weatherObject.get("address").getAsString());
            hashMap.put("oneWord",weatherObject.get("oneWord").getAsString());
            data.add(hashMap);
        }
    }
    private String getResultString(Response response) throws IOException{
        StringBuilder result = new StringBuilder();
        if(response.code() != 200){
            return "fail";
        }
        result.append(new String(response.body().bytes() , SdkConstant.CLOUDAPI_ENCODING));
        return result.toString();
    }
}
