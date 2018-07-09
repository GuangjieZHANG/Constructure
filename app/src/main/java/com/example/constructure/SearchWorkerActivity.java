package com.example.constructure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import data.Worker;
import data.WorkerSearched;
import views.WorkerSearchedAdapter;

public class SearchWorkerActivity extends Activity {

    private Spinner type;
    private ListView workers1;
    private Button search;
    private String typeChoosen;
    private int team_id;
    ArrayList<String> types = new ArrayList<>();
    private WorkerSearchedAdapter workerSearchedAdapter;
    String speciality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_worker);
        Intent it=this.getIntent();
        //team_id=3;

        double d =Double.parseDouble(it.getStringExtra("team_id"));
        team_id = (int)d;
        System.out.println(team_id);

        type = (Spinner)findViewById(R.id.type);
        workers1 = (ListView)findViewById(R.id.workers);
        search = (Button)findViewById(R.id.search);
       // types.add("建筑工");
        getTypes();
        System.out.println("---------here start to get specialities-------");


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                speciality = type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                typeChoosen = type.getSelectedItem().toString();
                //在这里将数据发回到服务器以获取信息
                System.out.println(typeChoosen);
                getWorkerData();

            }
        });

        workers1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkerSearched w = (WorkerSearched)workerSearchedAdapter.getItem(position);
               // System.out.println("======"+w.getId());
                Intent intent = new Intent(SearchWorkerActivity.this,WorkerActivity.class);
                intent.putExtra("worker",w.getId()+"");
                System.out.println("======"+w.getId());
                startActivity(intent);
            }
        });


    }

    //将来这两个函数需要向服务器请求数据  然后才能得到相应的数据
    private void getTypes(){
        //String[] types;
        String baseUrl = "http://34.226.141.56:8000/user/specialty";
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
                Log.i("reponse",res);
               try{
                    JSONObject jsonObject=new JSONObject(res);//我们需要把json串看成一个大的对象
                    JSONArray jsonspecialty=jsonObject.getJSONArray("specialty");//这里获取的是装载有所有
                    for (int i = 1;i<jsonspecialty.length();i++){
                        String test = jsonspecialty.getString(i).toString();
                        types.add(test);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SpinnerAdapter adapter = new ArrayAdapter<String>(SearchWorkerActivity.this,R.layout.support_simple_spinner_dropdown_item,types);
                                type.setAdapter(adapter);
                            }
                        });
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void getWorkerData(){
        final ArrayList<WorkerSearched> workers = new ArrayList<>();

        String baseUrl = "http://34.226.141.56:8000/user/worker_match/?team_id="+team_id+"&specialty="+speciality;
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
                Log.i("reponse",res);
                //{"msg": "success", "workers": [{"name": "\u5f20\u4e09", "hometown": "\u6cb3\u5357", "notes": "", "cci": 0.0, "id": 2, "certified": false}]}
                Gson gson= new GsonBuilder()
                        .create();
                Type type = new TypeToken<Map<String,Object>>(){}.getType();
                Map<String, Object> sList = gson.fromJson(res, type);
                final String result= sList.get("msg").toString();
                if(result.equals("success")){
                    final String json = sList.get("workers").toString();
                    try{
                      //  JSONObject jsonObject=new JSONObject(json);//我们需要把json串看成一个大的对象
                        JSONArray jsonspecialty = new JSONArray(json);//这里获取的是装载有所有
                        for (int i = 0;i<jsonspecialty.length();i++){
                            JSONObject worker = jsonspecialty.getJSONObject(i);
                            String name = worker.getString("name");
                            String hometown = worker.getString("hometown");
                            String notes = worker.getString("notes");
                            int cci = worker.getInt("cci");
                            int id = worker.getInt("id");
                            boolean certifited = worker.getBoolean("certified");

                            WorkerSearched worker1 = new WorkerSearched();
                            worker1.setName(name);
                            worker1.setHometown(hometown);
                            worker1.setNotes(notes);
                            worker1.setCci(cci);
                            worker1.setId(id);
                            worker1.setCertified(certifited);

                            workers.add(worker1);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    workerSearchedAdapter = new WorkerSearchedAdapter(SearchWorkerActivity.this,R.layout.worker_item,workers);
                                    workers1.setAdapter(workerSearchedAdapter);
                                }
                            });
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else if(result.equals("error")){
                }
            }
        });
    }


}
