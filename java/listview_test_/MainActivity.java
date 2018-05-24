package listview_test_;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zdj.souye.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private Myopenhelper myopenhelper;
    private ImageButton xingcheng_back;
    private List<Place> lists;
    public ListView lv;
    public MyListAdapter adapter;
    public Button class_add;
    public TextView title;
    public Calendar c = Calendar.getInstance();
    private long[] arry=new long[200];
    NotificationManager manager;
    int notification_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xinchengactivity_main);

        lists = new ArrayList<Place>();
        adapter=new MyListAdapter();
        xingcheng_back= (ImageButton) findViewById(R.id.xingcheng_back);
        xingcheng_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                String  pl=null;
                intent.putExtra("pl",pl);
                setResult(10,intent);
                finish();
            }
        });


        // 设置标题日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
        title= (TextView) findViewById(R.id.title);
        title.setText("行程提醒  "+sdf.format(calendar.getTime()));

        String sy = String.valueOf(c.get(Calendar.YEAR));
        String sm = String.valueOf(c.get(Calendar.MONTH)+1);
        String sd = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        //sqlite
        myopenhelper = new Myopenhelper(getApplicationContext());

        //listview
        lv= (ListView)findViewById(R.id.lv);
        SQLiteDatabase db= myopenhelper.getWritableDatabase();
        Cursor cursor=db.query("xingcheng",null,null,null,null,null,null);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                String id=cursor.getString(0);
                String name=cursor.getString(1);
                String year=cursor.getString(2);
                String month=cursor.getString(3);
                String day=cursor.getString(4);
                if(sy.equals(year)&&sm.equals(month)&&sd.equals(day)){
                    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(MainActivity.this);
                    builder.setSmallIcon(R.drawable.image1);
                    builder.setTicker("World");
                    builder.setWhen(System.currentTimeMillis());
                    builder.setContentTitle("今日有预定行程");
                    builder.setContentText("目的地: "+name);
                    db.delete("xingcheng", "_id=?",new String[]{id});
                    Intent intent = new Intent(MainActivity.this, Activity.class);
                    PendingIntent ma = PendingIntent.getActivity(MainActivity.this,0,intent,0);
                    builder.setContentIntent(ma);//设置点击过后跳转的activity

                /*builder.setDefaults(Notification.DEFAULT_SOUND);//设置声音
                builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
                builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动*/
                    builder.setDefaults(Notification.DEFAULT_ALL);//设置全部

                    Notification notification = builder.build();//4.1以上用.build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                    manager.notify(notification_id,notification);
                }else{
                    Place place=new Place();
                    place.setId(id);
                    place.setName(name);
                    place.setYear(year);
                    place.setMonth(month);
                    place.setDay(day);
                    lists.add(place);
                }
            }
        }
        db.close();
        lv.setAdapter(adapter);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1);
        scaleAnimation.setDuration(1000);
        LayoutAnimationController animationController = new LayoutAnimationController(scaleAnimation,0.6f);
        lv.setLayoutAnimation(animationController);

        //长按删除

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")
                        .setMessage("请确认删除该记录！")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Message msg=new Message();
                                msg.what=position;
                                handler.sendMessage(msg);
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                return true;
            }
        });
        //VibratorUtil.Vibrate(MainActivity.this, 1000);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            long position=arry[msg.what];
            myopenhelper.delete(position);
            SQLiteDatabase db= myopenhelper.getWritableDatabase();
            //刷新
            lists.clear();
            Cursor cursor=db.query("xingcheng",null,null,null,null,null,null);
            if(cursor!=null&&cursor.getCount()>0){
                while(cursor.moveToNext()){
                    String id=cursor.getString(0);
                    String name=cursor.getString(1);
                    String year=cursor.getString(2);
                    String month=cursor.getString(3);
                    String day=cursor.getString(4);
                    Place place=new Place();
                    place.setId(id);
                    place.setName(name);
                    place.setYear(year);
                    place.setMonth(month);
                    place.setDay(day);
                    lists.add(place);
                }
            }
            lv.setAdapter(adapter);
            db.close();
        }
    };

    public void click_add(View v){
        Intent intent = new Intent(MainActivity.this, Record.class);
        MainActivity.this.startActivityForResult(intent,1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String pl=data.getStringExtra("pl");
        if (pl!=null){
            String[] splits=pl.split("##");
            String[] str1=splits[1].split("年");
            String[] str2=str1[1].split("月");
            String[] str3=str2[1].split("日");
            System.out.println("*********sp"+splits[0]+"**str1"+str1[0]+"***str2"+str2[0]+"***str3"+str3[0]);
            SQLiteDatabase db= myopenhelper.getWritableDatabase();
            db.execSQL("insert into xingcheng(name,year,month,day) values(?,?,?,?)",new Object[]{splits[0],str1[0],str2[0],str3[0]});
            //刷新
            lists.clear();
            Cursor cursor=db.query("xingcheng",null,null,null,null,null,null);
            if(cursor!=null&&cursor.getCount()>0){
                while(cursor.moveToNext()){
                    String id=cursor.getString(0);
                    String name=cursor.getString(1);
                    String year=cursor.getString(2);
                    String month=cursor.getString(3);
                    String day=cursor.getString(4);
                    Place place=new Place();
                    place.setId(id);
                    place.setName(name);
                    place.setYear(year);
                    place.setMonth(month);
                    place.setDay(day);
                    lists.add(place);
                }
            }
            lv.setAdapter(adapter);
            db.close();
        }else{
            return;
        }
    }

    private class MyListAdapter extends BaseAdapter {
        private class ViewHolder{
            TextView plcae_;
            TextView time_;
        }
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
                viewHolder.plcae_=(TextView) convertView.findViewById(R.id.name);
                viewHolder.time_=(TextView) convertView.findViewById(R.id.time);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            Place place=lists.get(position);
            viewHolder.plcae_.setText(place.getName());
            viewHolder.time_.setText(place.getYear()+"."+place.getMonth()+"."+place.getDay());
            long id=Long.valueOf(place.getId());
            arry[position]=id;
            return convertView;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            Intent intent=new Intent();
            String  pl=null;
            intent.putExtra("pl",pl);
            setResult(10,intent);
            finish();
        }
        return false;
    }
}
