package com.example.administrator.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.zdj.souye.R;

import weather.Weather_today;
public class MainActivity extends AppCompatActivity {
    private ImageButton weather_button;
    private ImageButton xingcheng_button;
    private ImageButton huiyi_button;
    private Button login_button;
    private ImageButton zhuce_button;
    private ImageButton home;
    private Button minsu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setListeners();
        initView();
    }

    private void setListeners() {
        weather_button= (ImageButton) findViewById(R.id.weather_button);
        xingcheng_button= (ImageButton) findViewById(R.id.xincheng_buttom);
        huiyi_button= (ImageButton) findViewById(R.id.huiyi_button);
        login_button= (Button) findViewById(R.id.login_button);
        zhuce_button= (ImageButton) findViewById(R.id.zhuce_button);
        home= (ImageButton) findViewById(R.id.home);
        minsu= (Button) findViewById(R.id.minsu);
    }

    private void initView() {
        weather_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Weather_today.class);
                startActivity(intent);
            }
        });
        xingcheng_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, listview_test_.MainActivity.class);
                startActivity(intent);
            }
        });
       huiyi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, huiyizhou.HuiYi.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, news_app.MainActivity.class);
                startActivityForResult(intent,1000);
            }
        });
        zhuce_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, news_app.Register.class);
                startActivity(intent);
            }
        });
        minsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,com.minsu.MainActivity.class);
                intent.putExtra("name",login_button.getText().toString());
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000 && resultCode == 1001)
        {
            String pl=data.getStringExtra("pl");
            login_button.setText(pl);
        }
    }
}
