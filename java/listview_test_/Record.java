package listview_test_;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.zdj.myapplication.fly.caldroid.CaldroidActivity;
import com.zdj.souye.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import weather.Weather_today;

public class Record extends Activity {

    public Button class_add;
    public Button close;
    private EditText ed_p;
    private Button time;
    private ImageButton back;
    private long seleteTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        ed_p= (EditText) findViewById(R.id.ed_p);
        time=findViewById(R.id.timego);
        class_add = (Button) findViewById(R.id.close_add);
        close = (Button) findViewById(R.id.close);
        back=findViewById(R.id.id_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                String  pl=null;
                intent.putExtra("pl",pl);
                setResult(10,intent);
                finish();
            }
        });
        class_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(time.getText().toString().equals("2017年10月1日")){
                    Toast toast=Toast.makeText(getApplicationContext(),"内容不能为空",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    String p=ed_p.getText().toString().trim();
                    String pl=p+"##"+time.getText().toString();
                    Intent intent=new Intent();
                    intent.putExtra("pl",pl);
                    setResult(10,intent);
                    finish();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                String  pl=null;
                intent.putExtra("pl",pl);
                setResult(10,intent);
                finish();
            }
        });
    }
    public void timergo(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putLong("selete_time", seleteTime);
        intent.putExtras(bundle);
        intent.setClass(Record.this, CaldroidActivity.class);
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
                    time.setText(t1);
                } else {
                    return;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void voice_locgo(View view){
        initSpeech(Record.this);
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
                    ed_p.setText(result);
                    ed_p.setSelection(ed_p.getText().toString().length());
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