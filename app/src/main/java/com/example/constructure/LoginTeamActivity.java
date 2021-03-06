package com.example.constructure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class LoginTeamActivity extends Activity {

    private EditText name_in;
    private EditText psw_in;
    private Button login;
    private ImageView image;

    String id,psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_team);

        name_in = (EditText)findViewById(R.id.login_team_name);
        psw_in = (EditText)findViewById(R.id.login_team_psw);
        image = (ImageView)findViewById(R.id.login_worker_image);

        login = (Button)findViewById(R.id.login_team);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = name_in.getText().toString();
                psw = psw_in.getText().toString();
                // get
                String baseUrl = "http://34.226.141.56:8000/user/team_logon/?id="+id+"&pwd="+psw;
                OkHttpClient okHttpClient=new OkHttpClient();
                final Request request=new Request.Builder().url(baseUrl).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String res=response.body().string();
                        Gson gson= new GsonBuilder()
                                .create();
                        Type type = new TypeToken<Map<String, Object>>(){}.getType();
                        Map<String, Object> sList = gson.fromJson(res, type);
                        final String result= sList.get("msg").toString();
                        Log.i("res",result);
                        if(result.equals("success")){
                            final String team_id = sList.get("team_id").toString();
                            //带着id一起跳转
                            Intent intent = new Intent(LoginTeamActivity.this,SearchWorkerActivity.class);
                            intent.putExtra("team_id",team_id);
                            startActivity(intent);
                            LoginTeamActivity.this.finish();

                        }else if(result.equals("team not found")){
                            toast("请先注册");
                        }else if(result.equals("wrong password")){
                            toast("密码错误");
                        }
                    }
                });

            }
        });

    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginTeamActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
