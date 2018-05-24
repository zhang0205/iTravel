package huiyizhou;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.zdj.myapplication.fly.caldroid.CaldroidActivity;
import com.zdj.souye.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 回忆轴二级页面生成图片
 */

public class Add extends Activity implements View.OnClickListener {
    private ImageView image; //添加的图片
    private ImageButton imageButtonAdd;   //添加图片按钮
    private Button btfinish;     //完成按钮
    private EditText edit1;      //城市景点名编辑框
    private Button edit2;      //游玩时间编辑框
    /**
     * 相册处理
     */
    private static final int NONE = 0;
    private static final int PHOTO_ZOOM = 1; // 缩放
    private static final int PHOTO_RESOULT = 2;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private int height=150;
    private int width=300;
    Bitmap photo=null;

    /**
     * 语音处理
     */
    private static String TAG = Add.class.getSimpleName();
    private ImageButton voice;
    private Button voice_person;
    private EditText editText;
    private SpeechSynthesizer mTts;
    private String voicer = "xiaoyan";
    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue ;
    private SharedPreferences mSharedPreferences;
    private String text=null;
    private long seleteTime = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.huiframlayout);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
        init();
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(Add.this, mTtsInitListener);
        // 云端发音人名称列表
        mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            setResult(20,intent);
            Add.this.finish();
        }
        return false;
    }
    @Override
    public void onClick(View view) {
        if( null == mTts ){
//            this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
            return;
        }
        switch(view.getId()){
            case R.id.voice:
                FlowerCollector.onEvent(Add.this, "voice");
                text = ((EditText) findViewById(R.id.id_editText)).getText().toString();
                setParam();
                mTts.startSpeaking(text, mTtsListener);
                break;
            case R.id.speak:
                showPresonSelectDialog();
                break;
        }
    }
    /**
     * 初始化控件
     */
    private void init() {
        image= (ImageView) findViewById(R.id.id_ico1);
        imageButtonAdd= (ImageButton) findViewById(R.id.image_add);
        btfinish= (Button) findViewById(R.id.id_finish);
        edit1= (EditText) findViewById(R.id.id_edit1);
        edit2=  findViewById(R.id.id_edit2);

        voice= (ImageButton) findViewById(R.id.voice);
        voice_person= (Button) findViewById(R.id.speak);
        editText= (EditText) findViewById(R.id.id_editText);
        //开始合成语音
        voice.setOnClickListener(Add.this);
        //选择发音人
        voice_person.setOnClickListener(Add.this);


        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从相册获取图片
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTO_ZOOM);
            }
        });
        btfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=edit1.getText().toString().trim();
                String time=edit2.getText().toString().trim();
                ContestHui contest=null;
                if (photo!=null&&!city.equals("")&&!time.equals("")){
                    contest=new ContestHui(photo,city,time);
                }
                text = ((EditText) findViewById(R.id.id_editText)).getText().toString();
                Intent intent=new Intent();
                intent.putExtra("contest",contest);
                intent.putExtra("voice",text);
                setResult(10,intent);
                finish();
            }
        });
    }
    public void select_calander(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putLong("selete_time", seleteTime);
        intent.putExtras(bundle);
        intent.setClass(Add.this, CaldroidActivity.class);
        startActivityForResult(intent,5);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5){
            if (resultCode == 2) {
                seleteTime = data.getLongExtra("SELETE_DATA_TIME", 0);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                Date d1 = new Date(seleteTime);
                String t1 = format.format(d1);
                if (seleteTime > 0) {
                    edit2.setText(t1);
                } else {
                    return;
                }
            }
        }
        if (resultCode == NONE)
            return;
        if (data == null)
            return;
        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                photo = extras.getParcelable("data");
                image.setImageBitmap(photo);//把图片显示在ImageView控件上
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 收缩图片
     * @param uri
     */
    public void startPhotoZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }
    private int selectedNum = 0;
    /**
     * 发音人选择。
     */
    private void showPresonSelectDialog() {
        new AlertDialog.Builder(this).setTitle("在线合成发音人选项")
                .setSingleChoiceItems(mCloudVoicersEntries, // 单选框有几项,各是什么名字
                        selectedNum, // 默认的选项
                        new DialogInterface.OnClickListener() { // 点击单选框后的处理
                            public void onClick(DialogInterface dialog,
                                                int which) { // 点击了哪一项
                                voicer = mCloudVoicersValue[which];
                                if ("catherine".equals(voicer) || "henry".equals(voicer) || "vimary".equals(voicer)) {
                                    ((EditText) findViewById(R.id.id_editText)).setText(R.string.text_tts_source_en);
                                }
                                selectedNum = which;
                                dialog.dismiss();
                            }
                        }).show();
    }

//    /**
//     * 初始化监听
//     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
        }
    };
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
        }
        @Override
        public void onSpeakPaused() {
        }
        @Override
        public void onSpeakResumed() {
        }
        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }
        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }
        @Override
        public void onCompleted(SpeechError error) {
        }
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };
    /**
     * 参数设置
     * @param
     * @return
     */
    private void setParam(){
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
        mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
        mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( null != mTts ){
            mTts.stopSpeaking();
            mTts.destroy();
        }
    }
    @Override
    protected void onResume() {
        FlowerCollector.onResume(Add.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }
    @Override
    protected void onPause() {
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(Add.this);
        super.onPause();
    }
}
