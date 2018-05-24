package news_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zdj.souye.R;
public class MainActivity extends AppCompatActivity {
    private EditText admin;
    private EditText password;
    private Loginopenhelper loginopenhelper;
    private Button login_sure;
    private ImageButton login_back;
    private Button login_zhuce;

    private CheckView mMyView = null;
    private String[] res = new String[4];
    private EditText yanzheng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        loginopenhelper = new Loginopenhelper(getApplicationContext());
        admin= (EditText) findViewById(R.id.ed_dlad);
        password= (EditText) findViewById(R.id.ed_dlps);
        login_sure= (Button) findViewById(R.id.login_sure);
        login_back= (ImageButton) findViewById(R.id.login_back);
        login_zhuce= (Button) findViewById(R.id.login_zhuce);
        yanzheng= (EditText) findViewById(R.id.yanzheng);
        login_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad=admin.getText().toString().trim();
                String psd=password.getText().toString().trim();
                String psd_r=null;
                if (ad.isEmpty()||psd.isEmpty()){
                    Toast.makeText(MainActivity.this, "用户、密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String str="";
                    for (int i=0;i<res.length;i++){
                        str+=res[i];
                    }
                    System.out.println("*****************login"+str);
                    if (!yanzheng.getText().toString().equals(str)){
                        Toast.makeText(MainActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();
                        res = mMyView.getValidataAndSetImage();
                    }else{
                        SQLiteDatabase db= loginopenhelper.getWritableDatabase();
                        Cursor cursor=db.query("admins",null,null,null,null,null,null);
                        if(cursor!=null&&cursor.getCount()>0){
                            while(cursor.moveToNext()){
                                String name=cursor.getString(1);
                                String psdd=cursor.getString(2);
                                if(ad.equals(name)){
                                    psd_r=psdd;
                                }
                            }
                        }
                        db.close();
                        if (psd.equals(psd_r)){
                            Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            String pl=ad;
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                            intent.putExtra("pl",pl);
                            setResult(1001,intent);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, "登陆失败 请检查账号密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivityForResult(intent,1);
            }
        });

        mMyView = (CheckView)findViewById(R.id.checkView);
        //初始化验证码
        res = mMyView.getValidataAndSetImage();
        mMyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新初始化验证码
                res = mMyView.getValidataAndSetImage();
            }
        });
    }

}
