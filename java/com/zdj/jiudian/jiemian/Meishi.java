package com.zdj.jiudian.jiemian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.ADInfo;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.CycleViewPager;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.ViewFactory;
import com.zdj.souye.R;

import java.util.ArrayList;
import java.util.List;

import weather.Weather_today;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Meishi extends Activity {
    String[] strings=new String[]{
            "安庆","蚌埠","巢湖","池州","滁州","阜阳","合肥","淮北","淮南","黄山","六安","马鞍山","宿州","铜陵","芜湖","宣城","亳州","福州","龙岩","南平","宁德","莆田","泉州","三明","漳州","鞍山","本溪","阿拉善盟","巴彦淖尔市","包头","赤峰","长治","白银","定西","甘南","嘉峪关","金昌","酒泉","兰州"
            ,"临夏","陇南","平凉","庆阳","天水","武威","张掖","潮州","东莞","佛山",
            "河源","惠州","江门","揭阳","茂名","梅州","清远","汕头","汕尾","韶关","阳江","云浮","湛江","肇庆","中山","珠海","百色","北海","崇左","防城港","桂林","贵港","河池","贺州","来宾","柳州","南宁","钦州","梧州","玉林","安顺","毕节","贵阳","六盘水","黔东南","黔南","黔西南","铜仁","遵义","白沙","保亭","昌江","澄迈县","定安县","东方","海口","乐东","临高县",
            "陵水","琼海","琼中","屯昌县","万宁","文昌","五指山","儋州","三沙市","保定","沧州","承德","邯郸","衡水","廊坊","秦皇岛","石家庄","唐山","邢台","张家口","安阳","鹤壁","济源","焦作","开封","洛阳","南阳","平顶山","三门峡","商丘","新乡","信阳","许昌","郑州","周口","驻马店","漯河","濮阳","大庆","大兴安岭","哈尔滨","鹤岗","黑河","鸡西","佳木斯","牡丹江","七台河","齐齐哈尔","双鸭山",
            "绥化","伊春","乌苏里江","鄂州","恩施","黄冈","黄石","荆门","荆州","潜江","神农架林区","十堰","随州","天门","仙桃","咸宁","襄阳","孝感","宜昌","常德","长沙","郴州","衡阳","怀化","娄底","邵阳","湘潭","湘西","益阳","永州","岳阳","张家界","株洲","白城","白山","长春","吉林","辽源","四平","松原","通化","延边","长白山","常州","淮安","连云港","南京","南通",
            "宿迁","泰州","无锡","徐州","盐城","扬州","镇江","抚州","赣州","吉安","景德镇","九江","南昌","萍乡","上饶","新余","宜春","鹰潭","重庆"
    };
    boolean flag=true;
    ImageButton more;
    ListView listView;
    EditText editText;
    private ImageButton voice;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private String[] imageUrls=new String[]{
            "http://img4.imgtn.bdimg.com/it/u=218838359,732111377&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2027857221,4046233147&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=860514567,3960688957&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2968723112,2539346968&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3764457546,1005831707&fm=27&gp=0.jpg"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meishi);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5988041f");
        configImageLoader();
        initialize();
        more=findViewById(R.id.more);
        listView=findViewById(R.id.listView);
        editText=findViewById(R.id.search_edit);
        voice=findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSpeech(Meishi.this);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    MyAdapter myAdapter=new MyAdapter();
                    listView.setAdapter(myAdapter);
                    setListViewHeightBasedOnChildren(listView);
                    more.setImageResource(R.drawable.goback);
                    flag=false;
                }
                else {
                    more.setImageResource(R.drawable.more);
                    listView.setAdapter(null);
                    ViewGroup.LayoutParams params = listView.getLayoutParams();
                    params.height = 0;
                    listView.setLayoutParams(params);
                    flag=true;
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=view.findViewById(R.id.city);
                editText.setText(textView.getText());
                editText.setSelection(editText.getText().toString().length());
            }
        });
    }
    public void initSpeech(final Context context) {
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    String result = parseVoice(recognizerResult.getResultString());
                    editText.setText(result);
                    editText.setSelection(editText.getText().toString().length());
                }
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        mDialog.show();
    }
    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Weather_today.Voice voiceBean = gson.fromJson(resultString, Weather_today.Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Weather_today.Voice.WSBean> ws = voiceBean.ws;
        for (Weather_today.Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void back(View view){
        finish();
    }
    public void search(View view){
        Intent intent=new Intent(getApplicationContext(),com.zdj.jiudian.MainActivity.class);
        intent.putExtra("city",editText.getText().toString());
        startActivity(intent);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public void select(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                editText.setText("天津");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt2:
                editText.setText("北京");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt3:
                editText.setText("上海");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt5:
                editText.setText("深圳");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt6:
                editText.setText("成都");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt7:
                editText.setText("杭州");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt9:
                editText.setText("苏州");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt10:
                editText.setText("珠海");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt11:
                editText.setText("九江");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt13:
                editText.setText("三亚");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt14:
                editText.setText("济南");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt15:
                editText.setText("广州");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt16:
                editText.setText("沈阳");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt12:
                editText.setText("厦门");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt8:
                editText.setText("武汉");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt17:
                editText.setText("大连");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt18:
                editText.setText("南京");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt19:
                editText.setText("宁波");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt20:
                editText.setText("西安");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt21:
                editText.setText("重庆");
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.bt22:
                editText.setText("黄山");
                editText.setSelection(editText.getText().toString().length());
                break;
        }
    }
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return strings.length;
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
            if (view == null)
                view1=View.inflate(getApplicationContext(),R.layout.item1,null);
            else
                view1=view;
            TextView textView=view1.findViewById(R.id.city);
            textView.setText(strings[i]);
            return view1;
        }
    }
    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.meishi);
        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);
        }
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
        cycleViewPager.setCycle(true);
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        cycleViewPager.setWheel(true);
        cycleViewPager.setTime(2000);
        cycleViewPager.setIndicatorCenter();
    }
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(Meishi.this,
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };
    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
