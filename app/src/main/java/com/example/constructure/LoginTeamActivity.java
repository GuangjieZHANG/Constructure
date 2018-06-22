package com.example.constructure;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginTeamActivity extends Activity {

    private EditText name_in;
    private EditText psw_in;
    private Button login;
    private ImageView image;

    String name,psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_team);

        name_in = (EditText)findViewById(R.id.login_team_name);
        psw_in = (EditText)findViewById(R.id.login_team_psw);
        image = (ImageView)findViewById(R.id.login_worker_image);

        login = (Button)findViewById(R.id.login_team);

        name = name_in.getText().toString();
        psw = psw_in.getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
