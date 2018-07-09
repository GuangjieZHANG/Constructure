package com.example.constructure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

import data.CurrentWorker;
import data.Project;
import data.Team;
import data.Worker;
import views.CurrentWorkerAdapter;
import views.HorizontalListView;
import views.ProjectAdapter;
import views.TeamAdapter;

public class TeamActivity extends Activity {

    private HorizontalListView hListViewCurrentWorker;
    private HorizontalListView hListViewProjects;
    private TextView teamName;
    private TextView laborCom;
    private ImageView teamImage;
    private int teamId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        Intent intent = getIntent();
        teamId = Integer.parseInt(intent.getStringExtra("team_id"));
        //teamId = 3;

        hListViewCurrentWorker = (HorizontalListView)findViewById(R.id.current_worker_team);
        hListViewProjects = (HorizontalListView)findViewById(R.id.ex_projects_team);
        teamName = (TextView)findViewById(R.id.team_name);
        teamImage = (ImageView)findViewById(R.id.team_image);
        laborCom = (TextView)findViewById(R.id.team_labor);

        getData();

     /*   */

    }

    private void getData(){

        final ArrayList<Project> projects = new ArrayList<>();
        final ArrayList<CurrentWorker> currentWorkers = new ArrayList<>();
        final Team teamReceived = new Team();
/*
        team.setName("上海建设");
        team.setLabor_companys("舜杰劳务");

        CurrentWorker c1 = new CurrentWorker("建筑工",5,"共同合作");
        CurrentWorker c2 = new CurrentWorker("油漆工",4,"共同合作");
        CurrentWorker c3 = new CurrentWorker("水泥工",3,"共同合作");
        CurrentWorker c4 = new CurrentWorker("木工",1,"共同合作");
        CurrentWorker c5 = new CurrentWorker("建筑工",2,"共同合作");
        CurrentWorker c6 = new CurrentWorker("建筑工",6,"共同合作");

        currentWorkers.add(c1);
        currentWorkers.add(c2);
        currentWorkers.add(c3);
        currentWorkers.add(c4);
        currentWorkers.add(c5);
        currentWorkers.add(c6);

        Project p1 = new Project("明珠");
        Project p2 = new Project("和谐城");
        Project p3 = new Project("世纪嘉园");
        Project p4 = new Project("博物馆");
        Project p5 = new Project("世博园");
        projects.add(p1);
        projects.add(p2);
        projects.add(p3);
        projects.add(p4);
        projects.add(p5);*/



        String baseUrl = "http://34.226.141.56:8000/user/team_match/?team_id="+teamId;
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
                Log.i("team",res);
                // {"picture": "testtesttest", "laborcompany": "\u52b3\u52a1\u516c\u53f8\u4e00\u53f7", "current_workers": [{"note": "\u5171\u540c\u5408\u4f5c", "specialty": "\u6728\u5de5", "number": 1}], "name": "\u897f\u5b89\u8d5a\u94b1\u6709\u9650\u516c\u53f8", "ex_projects": [{"picture": "alsdfkjadslfj", "name": "\u4e07\u79d1\u57ce\u4e00\u671f"}]}
                try{
                    JSONObject team = new JSONObject(res);
                    String picture = team.getString("picture");
                    String name = team.getString("name");
                    String laborcompany = team.getString("laborcompany");
                    JSONArray currentWork = team.getJSONArray("current_workers");
                    JSONArray ex_projects = team.getJSONArray("ex_projects");
                    teamReceived.setLabor_companys(laborcompany);
                    teamReceived.setName(name);
                    for(int i = 0;i<currentWork.length();i++){
                        JSONObject worker = currentWork.getJSONObject(i);
                        String note = worker.getString("note");
                        String speciality = worker.getString("specialty");
                        int num = worker.getInt("number");

                        CurrentWorker w1 = new CurrentWorker(speciality,num,note);
                        currentWorkers.add(w1);
                    }
                    for(int j = 0;j<ex_projects.length();j++){
                        JSONObject project = ex_projects.getJSONObject(j);
                        String pic = project.getString("picture");
                        String namePro = project.getString("name");
                        Project p = new Project(namePro);
                        projects.add(p);
                    }
                    teamReceived.setCurrent_workers(currentWorkers);
                    teamReceived.setEx_projects(projects);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        teamName.setText(teamReceived.getName());
                        laborCom.setText(teamReceived.getLabor_companys());

                        CurrentWorkerAdapter currentWorkerAdapter = new CurrentWorkerAdapter(TeamActivity.this,R.layout.current_worker_item,teamReceived.getCurrent_workers());
                        hListViewCurrentWorker.setAdapter(currentWorkerAdapter);

                        ProjectAdapter projectAdapter = new ProjectAdapter(TeamActivity.this,R.layout.project_item,teamReceived.getEx_projects());
                        hListViewProjects.setAdapter(projectAdapter);
                    }
                });

            }
        });
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap

                .createBitmap(

                        drawable.getIntrinsicWidth(),

                        drawable.getIntrinsicHeight(),

                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                                : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

// canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

}
