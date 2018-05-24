package huiyizhou;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zdj.souye.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 回忆轴一级页面
 */
public class HuiYi extends AppCompatActivity {
    private ImageButton ib; //点击生成图片
    private ListView listView;
    private ImageButton huiyi_button;
    private List<Map<String, Object>> listdata;
    private SqlBitmap sqlBitmap;
    public String T_BLOB = "T_BLOB";
    public String CITY="city";
    public String TIME="time";
    public String T_ID = "_id";
    public String VOICE="voice";
    private long[] arry=new long[200];
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            long position=arry[msg.what];
            sqlBitmap.delete(position);
            listdata=sqlBitmap.getData();
            listView.setAdapter(new MyAdapter());
        }
    };
    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            long position=arry[msg.what];
            HashMap<String, Object> hashMap;
            hashMap =sqlBitmap.getItem(position);
            Bitmap bitmap= (Bitmap) hashMap.get(T_BLOB);
            String city= (String) hashMap.get(CITY);
            String time= (String) hashMap.get(TIME);
            String voice= (String) hashMap.get(VOICE);
            ContestHui contest;
            contest=new ContestHui(bitmap,city,time);
            Intent intent=new Intent(HuiYi.this,ThridHuiYi.class);
            intent.putExtra("contest",contest);
            intent.putExtra("voice",voice);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hui_yi);
        sqlBitmap=new SqlBitmap(getApplicationContext());
        huiyi_button= (ImageButton) findViewById(R.id.huiyi_back);
        init();
    }

    private void init() {
        huiyi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                String  pl=null;
                intent.putExtra("pl",pl);
                setResult(10,intent);
                finish();
            }
        });
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HuiYi.this,Add.class);
                startActivityForResult(intent,1);
            }
        };
        ib= (ImageButton) findViewById(R.id.id_add);
        ib.setOnClickListener(listener);
        listView= (ListView) findViewById(R.id.id_lv);
        listdata=sqlBitmap.getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message msg=new Message();
                msg.what=i;
                handler1.sendMessage(msg);
//                Bitmap bitmap= (Bitmap) hashMap.get(T_BLOB);
//                String city= (String) hashMap.get(CITY);
//                String time= (String) hashMap.get(TIME);
//                String voice= (String) hashMap.get(VOICE);
//                ContestHui contest;
//                contest=new ContestHui(bitmap,city,time);
//                Intent intent=new Intent(HuiYi.this,ThridHuiYi.class);
//                intent.putExtra("contest",contest);
//                intent.putExtra("voice",voice);
//                startActivity(intent);
//                ImageView ig=(ImageView)findViewById(R.id.test);
//                ig.setImageBitmap(bitmap);


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(HuiYi.this).setTitle("系统提示")
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
        if (listdata.size()>0){
            listView.setAdapter(new MyAdapter());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContestHui contestHui=data.getParcelableExtra("contest");
        String voice=data.getStringExtra("voice");
        if (contestHui!=null){
            sqlBitmap.createData(contestHui.getMimage(),contestHui.getCity(),contestHui.getTime(),voice);
            listdata=sqlBitmap.getData();
            listView.setAdapter(new MyAdapter());
        }
    }
    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return listdata.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View view1;
            if (view==null){
                view1=View.inflate(getApplicationContext(),R.layout.simpleadapter,null);
            }else {
                view1=view;
            }
            ImageView imageView= (ImageView) view1.findViewById(R.id.image);
            TextView cityView= (TextView) view1.findViewById(R.id.city);
            TextView timeView= (TextView) view1.findViewById(R.id.timer);
            HashMap<String, Object> hash;
            hash= (HashMap<String, Object>) listdata.get(i);
            Bitmap bit= (Bitmap) hash.get(T_BLOB);
            String city= (String) hash.get(CITY);
            String time= (String) hash.get(TIME);
            String voice= (String) hash.get(VOICE);
            long id= (long) hash.get(T_ID);
            imageView.setImageBitmap(bit);
            cityView.setText(city);
            timeView.setText(time);
            arry[i]=id;
            return view1;
        }
    }
    public void back(View v){
        Intent intent=new Intent();
        String  pl=null;
        intent.putExtra("pl",pl);
        setResult(10,intent);
        finish();
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
