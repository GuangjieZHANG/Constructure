package com.example.constructure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import views.WorkerConnectedAdapter;

public class WorkerActivity extends Activity {

    private ImageView worker_image;
    private TextView worker_name;
    private TextView worker_speciality;
    private TextView worker_cci;
    private HorizontalListView workers_connected;
    private HorizontalListView ex_projects;
    private HorizontalListView ex_teams;
    private int workerId;
    private  ProjectAdapter projectAdapter;
    private TeamAdapter teamAdapter;
    private WorkerConnectedAdapter workerConnectedAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker);
        Intent intent=this.getIntent();
        //workerId=3;
        String t =intent.getStringExtra("worker");
        System.out.println("======"+t);
        workerId =  Integer.parseInt(t);

        worker_image = (ImageView)findViewById(R.id.worker_image);
        worker_name = (TextView)findViewById(R.id.worker_name);
        worker_speciality = (TextView)findViewById(R.id.worker_speciality);

        workers_connected = (HorizontalListView)findViewById(R.id.worker_connected);
        ex_projects = (HorizontalListView)findViewById(R.id.ex_projects_worker);
        ex_teams = (HorizontalListView)findViewById(R.id.ex_teams_worker);

        getData();

        ex_teams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team p = (Team) teamAdapter.getItem(position);
                Intent intent = new Intent(WorkerActivity.this,TeamActivity.class);
                intent.putExtra("team_id",p.getId()+"");
                System.out.println("======"+p.getId());
                startActivity(intent);
            }
        });

        workers_connected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Worker w = (Worker)workerConnectedAdapter.getItem(position);
                Intent i = new Intent(WorkerActivity.this,WorkerActivity.class);
                i.putExtra("worker",w.getId()+"");
                System.out.println("======"+w.getId());
                startActivity(i);
            }
        });

    }

    private void getData(){
        final Worker worker = new Worker();
        final ArrayList<Project> projects = new ArrayList<>();
        final ArrayList<Worker> workerConnected = new ArrayList<>();
        final ArrayList<Team> teams = new ArrayList<>();
        String baseUrl = "http://34.226.141.56:8000/user/worker_match/?worker_id="+workerId;
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
                //{"picture": "alsdfkjadslfj", "name": "\u897f\u5b89\u8d5a\u94b1\u6709\u9650\u516c\u53f8", "matched_workers": [], "specialty": "\u6728\u5de5", "ex_projects": [{"picture": "alsdfkjadslfj", "name": "\u4e07\u79d1\u57ce\u4e00\u671f"}], "ex_teams": [{"team_id": 3, "name": "\u897f\u5b89\u8d5a\u94b1\u6709\u9650\u516c\u53f8"}]}
                Log.i("worker",res);
                try{
                    JSONObject thisworker = new JSONObject(res);
                    String picture = thisworker.getString("picture");
                    String name = thisworker.getString("name");
                    String speciality = thisworker.getString("specialty");
                    JSONArray matchedWork = thisworker.getJSONArray("matched_workers");
                    JSONArray ex_projects = thisworker.getJSONArray("ex_projects");
                    JSONArray ex_team = thisworker.getJSONArray("ex_teams");
                    worker.setName(name);
                    for(int i = 0;i<matchedWork.length();i++){
                        JSONObject worker = matchedWork.getJSONObject(i);
                        String note = worker.getString("note");
                        String spe = worker.getString("specialty");
                        String namePat = worker.getString("name");
                        int id = worker.getInt("worker_id");
                        Worker w = new Worker();
                        w.setName(namePat);
                        w.setSpeciality(spe);
                        w.setNote(note);
                        w.setId(id);
                        workerConnected.add(w);
                    }
                    for(int j = 0;j<ex_projects.length();j++){
                        JSONObject project = ex_projects.getJSONObject(j);
                        String pic = project.getString("picture");
                        String namePro = project.getString("name");
                        Project p = new Project(namePro);
                        projects.add(p);
                    }
                    for(int k = 0;k<ex_team.length();k++){
                        JSONObject ex_teams = ex_team.getJSONObject(k);
                        int i = ex_teams.getInt("team_id");
                        String namePro = ex_teams.getString("name");
                        Team t = new Team(i,namePro);
                        teams.add(t);
                    }

                    worker.setMatched_workers(workerConnected);
                    worker.setEx_projects(projects);
                    worker.setEx_teams(teams);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        worker_name.setText(worker.getName());
                        worker_speciality.setText(worker.getSpeciality());

                        projectAdapter = new ProjectAdapter(WorkerActivity.this,R.layout.project_item,worker.getEx_projects());
                        ex_projects.setAdapter(projectAdapter);

                        workerConnectedAdapter = new WorkerConnectedAdapter(WorkerActivity.this,R.layout.worker_connected_item,worker.getMatched_workers());
                        workers_connected.setAdapter(workerConnectedAdapter);


                        teamAdapter = new TeamAdapter(WorkerActivity.this,R.layout.team_item,worker.getEx_teams());
                        ex_teams.setAdapter(teamAdapter);

                    }
                });

            }
        });

    }

}
