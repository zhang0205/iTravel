package huiyizhou;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.zdj.souye.R;
/**
 * Created by Administrator on 2017/7/22.
 */

public class ThridHuiYi extends Activity implements View.OnClickListener{
    private static String TAG = Add.class.getSimpleName();
    private SpeechSynthesizer mTts;
    private String voicer = "xiaoyan";
    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue ;
    private SharedPreferences mSharedPreferences;
    private String text=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thridview);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(ThridHuiYi.this, mTtsInitListener);
        // 云端发音人名称列表
        mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);

        findViewById(R.id.voicethrid).setOnClickListener(ThridHuiYi.this);
        findViewById(R.id.speakthrid).setOnClickListener(ThridHuiYi.this);

        Intent intent=getIntent();
        ContestHui contestHui=intent.getParcelableExtra("contest");
        text=intent.getStringExtra("voice");
        ImageView imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(contestHui.getMimage());
        TextView textView1= (TextView) findViewById(R.id.textView1);
        textView1.setText(contestHui.getCity());
        TextView textView2= (TextView) findViewById(R.id.textView2);
        textView2.setText(contestHui.getTime());

    }
    public void back_Click(View view){
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ThridHuiYi.this.finish();
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
            case R.id.voicethrid:
                FlowerCollector.onEvent(ThridHuiYi.this, "voicethrid");
                setParam();
                mTts.startSpeaking(text, mTtsListener);
                break;
            case R.id.speakthrid:
                showPresonSelectDialog();
                break;
        }
    }
    public void Click(View view){
        ThridHuiYi.this.finish();
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
                                    text="iFLYTEK is a national key software enterprise dedicated to the " +
                                            "research of intelligent speech and language technologies, development of software a" +
                                            "nd chip products, provision of speech information services, and integration of E-government systems. The intelligen" +
                                            "t speech technology of iFLYTEK, the core technology of the company, represents the top level in the world";
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
        FlowerCollector.onResume(ThridHuiYi.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }
    @Override
    protected void onPause() {
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(ThridHuiYi.this);
        super.onPause();
    }
}
