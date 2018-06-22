package com.example.constructure;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView name_zh;
    private Button login;
    private Button registre;
    private String role = null;
    private RadioGroup choiceRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Typeface youyuan = Typeface.createFromAsset(getAssets(),"/main/res/assets/youyuan.ttf");

        name_zh = (TextView)findViewById(R.id.name_zh);
 //       name_zh.setTypeface(youyuan);
        login = (Button)findViewById(R.id.login);
        registre = (Button)findViewById(R.id.registre);
        choiceRole = (RadioGroup)findViewById(R.id.choice);

        choiceRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.worker:
                        role = "worker";
                        break;
                    case R.id.team:
                        role = "team";
                        break;
                }

            }
        });

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role==null){
                    Toast.makeText(getApplicationContext(),"请选择您的登陆角色",Toast.LENGTH_SHORT).show();
                }else if(role.equals("team")){
                    Intent intent = new Intent(MainActivity.this,RegistreTeamActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }else if(role.equals("worker")){
                    Intent intent = new Intent(MainActivity.this,RegistreWorkerActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role==null){
                    Toast.makeText(getApplicationContext(),"请选择您的登陆角色",Toast.LENGTH_SHORT).show();
                }else if(role.equals("team")){
                    Intent intent = new Intent(MainActivity.this,LoginTeamActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }else if(role.equals("worker")){
                    Intent intent = new Intent(MainActivity.this,LoginWorkerActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        });

    }
}
