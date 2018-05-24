package com.zdj.jiudian;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MainActivity extends Activity {
    private static int count=1;
    private ListView listView;
    private Handler handler;
    private Handler handler1;
    private List<Map<String,String>> data;
    private List<Map<String,Bitmap>> list;
    private HashMap<String,String> hashMap;
    private HashMap<String,Bitmap> hashMap1;
    private ProgressBar progressBar;
    String city;
    String city_name;
    private String[] urls=new String[]{
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2020835094,1023231499&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2920168766,3122816556&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2967368893,452093439&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=673964525,601895093&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=909872632,1398636978&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=205490846,2532168015&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=573538832,3797388940&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2435766167,3462519856&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=463560661,3465076413&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4218830640,4065965490&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2795521132,3663066371&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2081031016,3855740581&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3558867724,2244365409&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2286938688,3458792039&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=406196330,2105663513&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=635449141,3004392332&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2508945280,3895368342&fm=27&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2959097578,2509691656&fm=27&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3915000894,3470547187&fm=27&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=179666257,470555329&fm=27&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3665443104,1924030647&fm=27&gp=0.jpg"
    };
    private String[] beijinURL=new String[]{
            "http://img1.imgtn.bdimg.com/it/u=3393275538,3276363707&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1610009616,1980401144&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=57416142,325160636&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1592304919,4164409932&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2137832360,1064965472&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1151502910,856397669&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=62472252,388950055&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2131893445,1322672000&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1734197132,3681972756&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1230374623,1007750534&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2621107281,3292831848&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=4076655199,3053142705&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1212773766,1784665937&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1122841404,2186208825&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1662994342,1109630388&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2837087447,2523741236&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3414243075,3898684381&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=175751648,3299109701&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=985687805,1750473517&fm=200&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=108914811,3816050027&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3987784372,982190473&fm=27&gp=0.jpg"
    };
    private String[] tianjinURL=new String[]{
            "http://img0.imgtn.bdimg.com/it/u=4040837647,553118079&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3187509217,1037906604&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=79241053,1319668031&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=619993135,924373304&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3685904044,3163031529&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1863354908,2317917063&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1590292621,1616863332&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3612548139,2269436461&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=421943669,4213493157&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3981593708,292372150&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2263039001,3281221737&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3906748487,3636187760&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1981377972,1775513842&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=206638178,2002910032&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2174074091,4233585835&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=913218719,249355886&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2613087941,51382384&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2008327772,1516795818&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=753371547,3169600101&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=270515939,766841722&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=444515645,1730579017&fm=27&gp=0.jpg"
    };
    private String[] shanghaiURL=new String[]{
            "http://img4.imgtn.bdimg.com/it/u=599924555,2241505469&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=4093337034,226774379&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1664299820,121702258&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=561938299,3819583857&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=836828829,1567982290&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1797804414,2124576934&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1619548182,1975884796&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=170390052,2709382013&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1288991760,3682907418&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1136573371,1298023363&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1991057477,1276296084&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3630274649,1871158189&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1251625797,3164918842&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2447138288,582004181&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1547190886,1429688359&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3491589900,3532783973&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4103164104,3494396684&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3470921907,3303950844&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2363626413,2958989877&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3951589162,2870064403&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1544110534,1704513114&fm=27&gp=0.jpg"
    };
    private String[] sanyaURL=new String[]{
            "http://img3.imgtn.bdimg.com/it/u=3721891224,3273011335&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4287905290,4264109714&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3720056853,705838526&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=604457756,2630857896&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3847572539,2051624888&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4032493562,2295389560&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3295952944,210961669&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=4093911412,1537608954&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2138091720,3028814855&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2733153122,420665904&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=665587720,3051683003&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3875459025,2263781300&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1215227999,1543219437&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=209800272,711020091&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2618196680,701864672&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2613311631,1360896758&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3644151861,4068031179&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3770066942,2215623258&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=864239549,644336685&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=134412742,4171108406&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=962969386,1476858693&fm=27&gp=0.jpg"
    };
    private String[] jinanURL=new String[]{
            "http://img3.imgtn.bdimg.com/it/u=4038788994,1622740489&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3095252209,1279972027&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3021466554,1033716962&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3865758817,2599507600&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2331849723,3089915733&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2398512236,1392468321&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=4282013698,2822315241&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=932528729,1330425321&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=17158786,1794428183&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3975640599,1799509428&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1076622143,1800580468&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2444542781,560632742&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3477725904,3462716497&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1634207074,278940000&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3389790641,3279400010&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=929190073,2118992585&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3353359639,1832420660&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=803111424,2502877486&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=930735971,1654888597&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=91595929,2398390728&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4262935021,2930992703&fm=27&gp=0.jpg"
    };
    private String[] guangzhouURL=new String[]{
            "http://img5.imgtn.bdimg.com/it/u=6270296,1515330015&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1201791323,3239683523&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3493488887,1530065723&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2851358593,133360197&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=372692318,4215782335&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3283974963,1968472528&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2090941257,251811156&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=550926655,1080726322&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3010384027,1489150770&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2152622628,2410008281&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=863408339,3714134748&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2131771394,1003608144&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1545926007,1571349713&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1179054380,3120538715&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4285151112,3945461046&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=411751581,875998348&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2411952187,3790559459&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1148107146,1924874108&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3144290044,3265753656&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1650463204,3292184662&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1611441176,3999957717&fm=27&gp=0.jpg"
    };
    private String[] shenzhenURL=new String[]{
            "http://img1.imgtn.bdimg.com/it/u=1611441176,3999957717&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3342168320,2731920648&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3895206562,968830719&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3191973407,3660720378&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2070197791,270816352&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2688045330,2866127461&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2042844401,80394&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3463627994,4086274123&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1330458006,2656924912&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=876351121,1694448419&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=276113749,595700514&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3332805660,3592029945&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=63700624,921170509&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2432946051,184193157&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3287235186,2549602727&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1682714111,1904176784&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3445658978,2641617367&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2969517072,2114248662&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2087139726,4131879622&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1843524479,3414022896&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=173399514,1018856546&fm=27&gp=0.jpg",
    };
    private String[] chengduURL=new String[]{
            "http://img1.imgtn.bdimg.com/it/u=1638675823,2397541620&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1701487058,1174510156&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=4180096635,193854164&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3443773934,810171933&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2082176613,2954543934&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2011888144,2782283334&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2871635661,146983877&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2341339553,2457881676&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4134906659,3481438293&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2493705921,3110702001&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2071768218,1894817540&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1939373609,2967928114&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3594260089,2412744350&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1631133979,3113912622&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1066939849,248814322&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1429006559,840951823&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=4111840739,3188551764&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1862507221,3373404297&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3328019585,3532374752&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2385599381,1355695165&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1611441176,3999957717&fm=27&gp=0.jpg"
    };
    private String[] hangzhouURL=new String[]{
            "http://img4.imgtn.bdimg.com/it/u=4188710915,2189256831&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3934942980,871231360&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3585453815,1160338298&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2960574450,3670405073&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1671232214,4179058943&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3168710276,4107163880&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3093252589,176751632&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3488262530,3206484342&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3542848555,3204047548&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2635971984,3865567857&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1806098859,2867248926&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=629928422,2373928199&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1009188389,3809250832&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3786531114,3342392765&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2255830267,4163861064&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1849177567,3583158801&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=4284771362,2962385275&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1766321798,1651934105&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2562759010,2676344922&fm=11&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=544659806,2767857824&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1611441176,3999957717&fm=27&gp=0.jpg"
    };
    private String[] suzhouURL=new String[]{
            "http://img0.imgtn.bdimg.com/it/u=3688552162,1709956374&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=993231239,10105095&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4208353432,1951964582&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1192481644,2971338016&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1815975406,3503297091&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3291435374,2136699398&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=83261008,2022551528&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1613526992,2902171015&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2402721846,2365624154&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3125623850,1801416140&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1606168868,2859432139&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1304864783,2300557357&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3854076253,1016727527&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1679251442,816469979&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=587816765,2656389462&fm=27&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=896997147,4207666332&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2822092083,3606975795&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3390031791,1107214774&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3673024655,1720158977&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=518901096,4187461826&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1611441176,3999957717&fm=27&gp=0.jpg"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView= (ListView) findViewById(R.id.listView);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        init();
        data=new ArrayList<>();
        list=new ArrayList<>();
        Intent intent=getIntent();
        city=intent.getStringExtra("city");
        try {
            city_name = URLEncoder.encode(city, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String URL="http://apis.juhe.cn/catering/querybycity?key=af70ad" +
                "42e3e6dc190548b0988a8d77f9&city="+city_name+"&page=1";
        HttpUtil.sendHttpRequest(URL, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message = new Message();
                message.what = 1;
                message.obj = response.toString();
                handler.sendMessage(message);
            }
            @Override
            public void onError(Exception e) {
                System.out.println("访问失败");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void click(View view){
        this.finish();
    }
    public void init(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if (msg.what==1){
                    String response= (String) msg.obj;
                    parseWithJSON(response);
                    Message msg1=new Message();
                    msg1.what=-2;
                    handler1.sendMessage(msg1);

                }
            }
        };
        handler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap;
                if (msg.what==-2&&data.size()>0){
                    hashMap= (HashMap<String, String>) data.get(0);
                    returnBitMap(hashMap.get("photos"));
                    count=1;
                }else {
                    if ((msg.what-1)<data.size()){
                        hashMap1=new HashMap<>();
                        bitmap= (Bitmap) msg.obj;
                        hashMap1.put("photos",bitmap);
                        list.add(hashMap1);
                    }
                    if (msg.what<data.size()){
                        hashMap= (HashMap<String, String>) data.get(msg.what);
                        returnBitMap(hashMap.get("photos"));
                    }
                    if (msg.what==data.size()){
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setAdapter(new MyAddapter());
                    }
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,Display.class);
                hashMap= (HashMap<String, String>) data.get(i);

                intent.putExtra("name",hashMap.get("name"));
                intent.putExtra("recommended_dishes",hashMap.get("recommended_dishes"));
                intent.putExtra("photos",hashMap.get("photos"));
                intent.putExtra("address",hashMap.get("address"));
                intent.putExtra("phone",hashMap.get("phone"));
                intent.putExtra("avg_price",hashMap.get("avg_price"));
                intent.putExtra("all_remarks",hashMap.get("all_remarks"));
                intent.putExtra("very_good_remarks",hashMap.get("very_good_remarks"));
                intent.putExtra("good_remarks",hashMap.get("good_remarks"));
                intent.putExtra("common_remarks",hashMap.get("common_remarks"));
                intent.putExtra("bad_remarks",hashMap.get("bad_remarks"));
                intent.putExtra("very_bad_remarks",hashMap.get("very_bad_remarks"));
                intent.putExtra("product_rating",hashMap.get("product_rating"));
                intent.putExtra("environment_rating",hashMap.get("environment_rating"));
                intent.putExtra("service_rating",hashMap.get("service_rating"));
                startActivity(intent);
            }
        });
    }
    /**
     * 获取网络景点图片
     * @param url 图片的地址
     */
    public void returnBitMap(final String url){
        new Thread(){
            @Override
            public void run() {
                URL myFileURL;
                Bitmap bitmap=null;
                try{
                    myFileURL = new URL(url);
                    HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.what=count;
                count++;
                if (bitmap!=null){
                    Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
                    msg.obj=newBmp;
                }else {
                    msg.obj=null;
                }
                handler1.sendMessage(msg);
            }
        }.start();
    }
    private void parseWithJSON(String response){
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(response);
        String resultcode = obj.get("resultcode").getAsString();
        if(resultcode != null && resultcode.equals("200")){
            JsonArray futureWeatherArray = obj.get("result").getAsJsonArray();
            HashMap<String,String> hashMap;
            for(int i=0;i<futureWeatherArray.size();i++){
                JsonObject weatherObject = futureWeatherArray.get(i).getAsJsonObject();
                hashMap=new HashMap<>();
                hashMap.put("name",weatherObject.get("name").getAsString());
                hashMap.put("address",weatherObject.get("address").getAsString());
                hashMap.put("phone",weatherObject.get("phone").getAsString());
                if (city.equals("北京"))
                    hashMap.put("photos",beijinURL[i]);
                else if (city.equals("天津"))
                    hashMap.put("photos",tianjinURL[i]);
                else if (city.equals("上海"))
                    hashMap.put("photos",shanghaiURL[i]);
                else if (city.equals("深圳"))
                    hashMap.put("photos",shenzhenURL[i]);
                else if (city.equals("成都"))
                    hashMap.put("photos",chengduURL[i]);
                else if (city.equals("杭州"))
                    hashMap.put("photos",hangzhouURL[i]);
                else if (city.equals("苏州"))
                    hashMap.put("photos",suzhouURL[i]);
                else if (city.equals("三亚"))
                    hashMap.put("photos",sanyaURL[i]);
                else if (city.equals("济南"))
                    hashMap.put("photos",jinanURL[i]);
                else if (city.equals("广州"))
                    hashMap.put("photos",guangzhouURL[i]);
                else
                    hashMap.put("photos",urls[i]);
                hashMap.put("avg_price",weatherObject.get("avg_price").getAsString());
                hashMap.put("all_remarks",weatherObject.get("all_remarks").getAsString());
                hashMap.put("very_good_remarks",weatherObject.get("very_good_remarks").getAsString());
                hashMap.put("good_remarks",weatherObject.get("good_remarks").getAsString());
                hashMap.put("common_remarks",weatherObject.get("common_remarks").getAsString());
                hashMap.put("bad_remarks",weatherObject.get("bad_remarks").getAsString());
                hashMap.put("very_bad_remarks",weatherObject.get("very_bad_remarks").getAsString());
                hashMap.put("product_rating",weatherObject.get("product_rating").getAsString());
                hashMap.put("environment_rating",weatherObject.get("environment_rating").getAsString());
                hashMap.put("service_rating",weatherObject.get("service_rating").getAsString());
                hashMap.put("recommended_dishes",weatherObject.get("recommended_dishes").getAsString());
                data.add(hashMap);
            }
        }
    }
    public class MyAddapter extends BaseAdapter{

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
                view1=View.inflate(MainActivity.this,R.layout.list_item,null);
            }else {
                view1=view;
            }
            ImageView imageView=view1.findViewById(R.id.imageView);
            hashMap1= (HashMap<String, Bitmap>) list.get(i);
            Bitmap bitmap=hashMap1.get("photos");
            Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
            imageView.setImageBitmap(newBmp);
            TextView name=view1.findViewById(R.id.name);
            TextView address=view1.findViewById(R.id.address);
            TextView telephone=view1.findViewById(R.id.telephone);
            hashMap= (HashMap<String, String>) data.get(i);
            name.setText(hashMap.get("name"));
            address.setText(hashMap.get("address"));
            telephone.setText(hashMap.get("phone"));
            return view1;
        }
    }
}
