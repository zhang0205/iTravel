package news_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zdj.souye.R;
public class Register extends AppCompatActivity {
    private EditText admin;
    private EditText password;
    private Loginopenhelper loginopenhelper;
    private Button zhuce_sure;
    private ImageButton zhuce_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        loginopenhelper = new Loginopenhelper(getApplicationContext());
        admin= (EditText) findViewById(R.id.ed_zcad);
        password= (EditText) findViewById(R.id.ed_zcps);
        zhuce_sure= (Button) findViewById(R.id.zhuce_sure);
        zhuce_back= (ImageButton) findViewById(R.id.zhuce_back);
        zhuce_back= (ImageButton) findViewById(R.id.zhuce_back);
        zhuce_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                String  pl=null;
                intent.putExtra("pl",pl);
                setResult(10,intent);
                finish();
            }
        });
        zhuce_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad=admin.getText().toString().trim();
                String psd=password.getText().toString().trim();
                if (ad.isEmpty()||psd.isEmpty()){
                    Toast.makeText(Register.this, "用户、密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    SQLiteDatabase db= loginopenhelper.getWritableDatabase();
                    Cursor cursor=db.query("admins",null,null,null,null,null,null);
                    if(cursor!=null&&cursor.getCount()>0){
                        while(cursor.moveToNext()){
                            String name=cursor.getString(1);
                            if(ad.equals(name)){
                                Toast.makeText(Register.this, "该账号已注册", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                    db.execSQL("insert into admins(admin,password) values(?,?)",new Object[]{ad,psd});
                    db.close();
                }
                finish();
            }
        });
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
