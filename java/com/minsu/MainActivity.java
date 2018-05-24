package com.minsu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zdj.myapplication.SqlBitmap;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.ADInfo;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.CycleViewPager;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.ViewFactory;
import com.zdj.souye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private String[] imageUrls=new String[]{
            "http://img1.imgtn.bdimg.com/it/u=1723315345,584516988&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=672919612,2476214753&fm=27&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=819206896,4042972249&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3551084798,3069714782&fm=27&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=153457852,2181333952&fm=27&gp=0.jpg"
    };
    private ImageView imageView;
    private SqlBitmap sqlBitmap;
    private List<Map<String , Object>> list;
    public String ID_minsu="_id";//字段名
    public String PRICE_minsu="price";//字段名
    public String SHAP_minsu="shap";//字段名
    public String DIS_minsu="dis";//字段名
    public String PHONE_minsu="phone";//字段名
    public String PHOTO_minsu="photo";//字段名
    public String PHOTO_minsu1="photo1";//字段名
    public String NAME_minsu="name";//字段名
    private ListView listView;
    private long[] idarr=new long[200];
    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main5);
        configImageLoader();
        initialize();
        sqlBitmap=new SqlBitmap(getApplicationContext());
        listView= (ListView) findViewById(R.id.ls);
        login= (TextView) findViewById(R.id.login);
        list=sqlBitmap.getDate_minsu();
        if (list.size()>0){
            HashMap<String , Object> hashMap;
            for (int i=0;i<list.size();i++){
                hashMap= (HashMap<String, Object>) list.get(i);
                idarr[i]= (long) hashMap.get(ID_minsu);
            }
            listView.setAdapter(new MyAdapter());
            setListViewHeightBasedOnChildren(listView);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Object> hashMap= (HashMap<String, Object>) list.get(i);
                ConstantMinsu constantMinsu=new ConstantMinsu((Bitmap) hashMap.get(PHOTO_minsu),
                        (String)hashMap.get(PRICE_minsu),
                        (String)hashMap.get(SHAP_minsu),(String)hashMap.get(DIS_minsu),(String)hashMap.get(PHONE_minsu),(String)hashMap.get(NAME_minsu));
                ConstantBitmap constantBitmap=new ConstantBitmap((Bitmap) hashMap.get(PHOTO_minsu1));
                System.out.println("***************PHOTO_minsu="+(Bitmap) hashMap.get(PHOTO_minsu)+
                "******************PHOTO_minsu1="+(Bitmap) hashMap.get(PHOTO_minsu1));
                Intent intent=new Intent(getApplicationContext(),DisItem.class);
                intent.putExtra("constantMinsu",constantMinsu);
                intent.putExtra("constantBitmap",constantBitmap);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")
                        .setMessage("请确认删除该记录！")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqlBitmap.delete_minsu(idarr[i]);
                                list=sqlBitmap.getDate_minsu();
                                listView.setAdapter(new MyAdapter());
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                return false;
            }
        });
        imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DisExam.class);
                startActivity(intent);
            }
        });
    }
    public void backmi(View view){
        this.finish();
    }
    public void start(View view){
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        if (name.equals("登录")){
            login.setText("请先登录");
        }
        else {
            Intent intent1=new Intent(MainActivity.this,Add.class);
            intent1.putExtra("name",name);
            startActivityForResult(intent1,1);
        }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 40){
            ConstantMinsu constantMinsu=data.getParcelableExtra("constantMinsu");
            ConstantBitmap constantBitmap=data.getParcelableExtra("constantBitmap");
            Bitmap photo,photo1;
            String price,shap,dis,phone,name;
            photo=constantMinsu.getMimage();
            photo1=constantBitmap.getMimage1();
            shap=constantMinsu.getShap();
            dis=constantMinsu.getDis();
            price=constantMinsu.getPrice();
            phone=constantMinsu.getPhone();
            name=constantMinsu.getName();
            if ( !phone.equals("") && !dis.equals("") && !price.equals("") && !shap.equals("") && !name.equals("")){
                long id=sqlBitmap.create_minsu(photo,photo1,price,shap,dis,phone,name);
                list=sqlBitmap.getDate_minsu();
                HashMap<String , Object> hashMap;
                for (int i=0;i<list.size();i++){
                    hashMap= (HashMap<String, Object>) list.get(i);
                    idarr[i]= (long) hashMap.get(ID_minsu);
                }
                listView.setAdapter(new MyAdapter());
                setListViewHeightBasedOnChildren(listView);
            }
        }
    }
    public class MyAdapter extends BaseAdapter{

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
            if (view == null)
                view1=View.inflate(getApplicationContext(),R.layout.minsu_item,null);
            else
                view1=view;
            HashMap<String , Object> hashMap= (HashMap<String, Object>) list.get(i);
            Bitmap photo_item= (Bitmap) hashMap.get(PHOTO_minsu);
            String price_item= (String) hashMap.get(DIS_minsu);
            String shap_item= (String) hashMap.get(SHAP_minsu);
            String name_item= (String) hashMap.get(NAME_minsu);
            long id= (long) hashMap.get(ID_minsu);
            ImageView imageView=view1.findViewById(R.id.imageView_item);
            TextView name=view1.findViewById(R.id.name_item);
            TextView shap=view1.findViewById(R.id.shap_item);
            TextView price=view1.findViewById(R.id.price_item);
            imageView.setImageBitmap(photo_item);
            name.setText(name_item);
            shap.setText(shap_item);
            price.setText(price_item);
            return view1;
        }
    }
    @SuppressLint("NewApi")
    private void initialize() {
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
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
                Toast.makeText(MainActivity.this,
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
