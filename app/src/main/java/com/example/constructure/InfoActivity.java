package com.example.constructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Map;

import data.Experience;
import data.Worker;
import views.ExperienceAdapter;

public class InfoActivity extends Activity {

    private TextView name;
    private ImageView image;
    private TextView updateCer;
    private TextView certifi;
    private ListView experience;
    private Button updateExp;
    private int worker_id;
    private EditText add_team;
    private EditText add_pro;
    private EditText add_start;
    private EditText add_end;
    private View layout;
    private boolean isCertificated = false;
    private ArrayList<Experience> experiences = new ArrayList<>();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        Intent it=this.getIntent();
        //worker_id=3;
        String s = it.getStringExtra("worker_id");
        double b = Double.parseDouble(s);
        worker_id = (int)b;


        name = (TextView)findViewById(R.id.info_name);
        image = (ImageView)findViewById(R.id.info_image);
        updateCer = (TextView)findViewById(R.id.info_update_certi);
        updateExp = (Button)findViewById(R.id.info_update_exp);
        experience = (ListView)findViewById(R.id.info_experience);
        certifi = (TextView)findViewById(R.id.certified);

        updateCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCertificated();
                if(isCertificated){
                    certifi.setText("证书已上传");
                }else{
                    certifi.setText("未认证");
                }
            }
        });
       // getExperience(2);
        experiences = getExperience(worker_id);
        System.out.println("----------");
        System.out.println(experiences.size());

     //   ExperienceAdapter adapter = new ExperienceAdapter(InfoActivity.this,R.layout.experience_item,experiences);
     //   experience.setAdapter(adapter);
        updateExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View layout = View.inflate(InfoActivity.this,R.layout.add_experience,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(InfoActivity.this);
                builder.setTitle("添加项目经历");//设置对话框的标题
                builder.setView(layout);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        add_team = (EditText)layout.findViewById(R.id.add_team);
                        String team = add_team.getText().toString();
                        add_pro = (EditText)layout.findViewById(R.id.add_pro);
                        String pro = add_pro.getText().toString();
                        add_start = (EditText)layout.findViewById(R.id.add_start);
                        String start = add_start.getText().toString();
                        add_end = (EditText)layout.findViewById(R.id.add_end);
                        String end = add_end.getText().toString();
                        postExperience(worker_id,team,pro,start,end);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog b=builder.create();
                b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                    }
                });

            }

  /*  private Worker getWorker(){

    }*/

    private void isCertificated(){
        String baseUrl = "http://34.226.141.56:8000/user/worker_certificate/?worker_id="+worker_id;
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
                Log.i("certificate",res);
                //{"certified": false}
                Gson gson= new GsonBuilder()
                        .create();
                Type type = new TypeToken<Map<String,Object>>(){}.getType();
                Map<String, Object> sList = gson.fromJson(res, type);
                final String result= sList.get("certified").toString();
                Log.i("res",result);
                if(result.equals("true")){
                    isCertificated = true;
                }else{
                    isCertificated = false;
                }
            }
        });
    }

    private void postExperience(final int workerId, String team, String project, String start, String end){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        String baseUrl = "http://34.226.141.56:8000/user/worker_exp/";
        FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();

        RequestBody requestBody = RequestBody.create(JSON,toJson(workerId,team,project,start,end));
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(baseUrl).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //输出出错信息
                Log.i("onFailure:",e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String res=response.body().string();
                Log.i("worker", res);
                //{"msg": "success"}
                try{
                   Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, Object>>(){}.getType();
                    Map<String, Object> sList = gson.fromJson(res, type);
                    final String result= sList.get("msg").toString();
                    Log.i("res",result);
                    if(result.equals("success")){
                        //toast("工作历史更新成功");
                        //更新界面
                      Intent i = new Intent(InfoActivity.this,InfoActivity.class);
                      System.out.println("--------"+worker_id);
                      //System.out.println("--------"+workerId);
                      i.putExtra("worker_id",String.valueOf(worker_id));
                      startActivity(i);
                      InfoActivity.this.finish();

                    }else if(result.equals("Team not found")){

                                AlertDialog.Builder builder=new AlertDialog.Builder(InfoActivity.this);
                                builder.setTitle("添加项目经历");//设置对话框的标题
                                builder.setMessage("项目未找到");
                                builder.setPositiveButton("确定",null);
                                AlertDialog b=builder.create();
                                b.show();

                    }else if(result.equals("error")){
                                AlertDialog.Builder builder=new AlertDialog.Builder(InfoActivity.this);
                                builder.setTitle("添加项目经历");//设置对话框的标题
                                builder.setMessage("添加发生错误");
                                builder.setPositiveButton("确定",null);
                                AlertDialog b=builder.create();
                                b.show();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    toast("服务器返回错误");
                }
            }
        });
    }

    public String toJson(int id,String team,String project,String start,String end){
        String jsonResult = "";
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("worker_id",id);
            jsonObj.put("team",team);
            jsonObj.put("project",project);
            jsonObj.put("starts",start);
            jsonObj.put("ends",end);
            jsonResult = jsonObj.toString();
        }catch (JSONException e){
            e.printStackTrace();
        }
        Log.i("Json:",jsonResult);
        return jsonResult;
    }

    public ArrayList<Experience> getExperience(int workerId){
        final ArrayList<Experience> exs = new ArrayList<>();
        String baseUrl = "http://34.226.141.56:8000/user/worker_exp/?worker_id="+workerId;
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
                Log.i("getExp",res);
                try{
                    JSONObject jsonObject=new JSONObject(res);//我们需要把json串看成一个大的对象
                    JSONArray jsonspecialty=jsonObject.getJSONArray("experiences");//这里获取的是装载有所有
                    for(int i = 0;i<jsonspecialty.length();i++){
                        JSONObject jsonEx = jsonspecialty.getJSONObject(i);

                        String epro = jsonEx.getString("project");
                        String estart = jsonEx.getString("start");
                        String eend = jsonEx.getString("end");
                        String eteam = jsonEx.getString("team");

                        Experience e = new Experience(eteam,epro,estart,eend);
                        exs.add(e);
                       /* System.out.println("----------");
                        System.out.println(epro+"  "+eteam+"  "+estart+"  "+eend);
                        System.out.println(e.toString());*/
                    }

                }catch (JSONException ex){
                    ex.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ExperienceAdapter adapter = new ExperienceAdapter(InfoActivity.this,R.layout.experience_item,experiences);
                        experience.setAdapter(adapter);
                    }
                });
            }
        });
        return exs;
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InfoActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
