package com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zdj.myapplication.fly.caldroid.CaldroidActivity;
import com.zdj.myapplication.fly.caldroid.Guanjian;
import com.zdj.myapplication.fly.caldroid.JiuDian;
import com.zdj.myapplication.fly.caldroid.PopWindow;
import com.zdj.myapplication.zdj.test_jiemian.cycleviewpager.lib.CycleViewPager.ImageCycleViewListener;
import com.zdj.souye.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 */
public class MainActivity extends Activity implements View.OnClickListener{

	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	private PopWindow popWindow;
	private String[] imageUrls = {"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=120451252,4147789190&fm=27&gp=0.jpg",
			"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1169998231,1051859829&fm=27&gp=0.jpg",
			"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2682739589,3917273324&fm=27&gp=0.jpg",
			"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2233965298,3193249561&fm=27&gp=0.jpg",
			"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1860744943,2724812124&fm=27&gp=0.jpg"};

	private long seleteTime = 0;
	private Button come;
	private Button leave;
	private Button city;
	private TextView guanjian;
	private TextView price;
	private ImageButton delt1;
	private ImageButton back_souye;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_main);
		configImageLoader();
		initialize();
		come= (Button) findViewById(R.id.come);
		leave= (Button) findViewById(R.id.leave);
		city= (Button) findViewById(R.id.city);
		guanjian= (TextView) findViewById(R.id.guanjianzi);
		delt1= (ImageButton) findViewById(R.id.delt1);
		back_souye= (ImageButton) findViewById(R.id.back_souye);
		back_souye.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Intent intent=new Intent(getApplicationContext(),com.zdj.souye.MainActivity.class);
//				startActivity(intent);
				finish();
			}
		});
		delt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				guanjian.setText("");
				delt1.setVisibility(View.INVISIBLE);
			}
		});
		guanjian.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(MainActivity.this, Guanjian.class);
				startActivityForResult(intent,2);
			}
		});
		price= (TextView) findViewById(R.id.price);
		price.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}



	public void search_jiudian(View view){
		Intent intent=new Intent(getApplicationContext(),com.zdj.myapplication.MainActivity.class);
		intent.putExtra("city",city.getText().toString());
		startActivity(intent);
	}
	//日期选择
	public void select(View view){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putLong("selete_time", seleteTime);
		intent.putExtras(bundle);
		intent.setClass(MainActivity.this, CaldroidActivity.class);
		switch (view.getId()){
			case R.id.come:
				startActivityForResult(intent, 5);
				break;
			case R.id.leave:
				startActivityForResult(intent, 4);
				break;
			case R.id.city:
				Intent intent1=new Intent(MainActivity.this, JiuDian.class);
				startActivityForResult(intent1,3);
		}


	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 5) {
			if (resultCode == 2) {
				seleteTime = data.getLongExtra("SELETE_DATA_TIME", 0);
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				Date d1 = new Date(seleteTime);
				String t1 = format.format(d1);
				if (seleteTime > 0) {
					come.setText(t1);
				} else {
					return;
				}
			}
		}
		if (requestCode == 4) {
			if (resultCode == 2) {
				seleteTime = data.getLongExtra("SELETE_DATA_TIME", 0);
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				Date d1 = new Date(seleteTime);
				String t1 = format.format(d1);
				if (seleteTime > 0) {
					leave.setText(t1);
				} else {
					return;
				}
			}
		}
		if (requestCode == 3){
			if (resultCode == 2){
				String name=data.getStringExtra("name");
				if (!name.equals(""))
					city.setText(name);
			}
		}
		if (requestCode == 2){
			if (resultCode == 2){
				String key=data.getStringExtra("key");
				if (!key.equals("")){
					guanjian.setText(key);
					delt1.setVisibility(View.VISIBLE);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
		
		// 将最后一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
		}
		// 将第一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
		
		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		//设置轮播
		cycleViewPager.setWheel(true);

	    // 设置轮播时间，默认5000ms
		cycleViewPager.setTime(2000);
		//设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}
	
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

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
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
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

	@Override
	public void onClick(View view) {
		popWindow=new PopWindow(MainActivity.this,onClickListener);
		popWindow.show();
	}
	private View.OnClickListener onClickListener=new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()){
				case R.id.price1:break;
				case R.id.price2:break;
				case R.id.price3:break;
				case R.id.price4:break;
				case R.id.price5:break;
				case R.id.price6:break;
				case R.id.shap1:break;
				case R.id.shap2:break;
				case R.id.shap3:break;
				case R.id.shap4:break;
				case R.id.shap5:break;
				default:break;
			}
		}
	};
}
