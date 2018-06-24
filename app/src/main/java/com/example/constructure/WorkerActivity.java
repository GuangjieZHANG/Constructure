package com.example.constructure;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import data.Project;
import data.Team;
import data.Worker;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker);

        worker_image = (ImageView)findViewById(R.id.worker_image);
        worker_name = (TextView)findViewById(R.id.worker_name);
        worker_speciality = (TextView)findViewById(R.id.worker_speciality);
        worker_cci = (TextView)findViewById(R.id.worker_cci);

        workers_connected = (HorizontalListView)findViewById(R.id.worker_connected);
        ex_projects = (HorizontalListView)findViewById(R.id.ex_projects_worker);
        ex_teams = (HorizontalListView)findViewById(R.id.ex_teams_worker);

        Worker worker = getData();
        worker_name.setText(worker.getName());
        worker_speciality.setText(worker.getSpeciality());
        worker_cci.setText("合拍度："+worker.getCci());

        ProjectAdapter projectAdapter = new ProjectAdapter(WorkerActivity.this,R.layout.project_item,worker.getEx_projects());
        ex_projects.setAdapter(projectAdapter);

        WorkerConnectedAdapter workerConnectedAdapter = new WorkerConnectedAdapter(WorkerActivity.this,R.layout.worker_connected_item,worker.getMatched_workers());
        workers_connected.setAdapter(workerConnectedAdapter);


        TeamAdapter teamAdapter = new TeamAdapter(WorkerActivity.this,R.layout.team_item,worker.getEx_teams());
        ex_teams.setAdapter(teamAdapter);


    }

    private Worker getData(){
        Worker worker = new Worker();
        worker.setName("吴伟杰");
        worker.setSpeciality("木工");
        worker.setCci(87);
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Worker> workerConnected = new ArrayList<>();
        ArrayList<Team> teams = new ArrayList<>();

        Worker w1 = new Worker(01,"王全有","木工",78,"搭档");
        Worker w2 = new Worker(02,"张三","建筑工",58,"丛属");
        Worker w3 = new Worker(03,"李四","油漆工",43,"搭档");
        Worker w4 = new Worker(04,"王五","瓦工",70,"上级");
        workerConnected.add(w1);
        workerConnected.add(w2);
        workerConnected.add(w3);
        workerConnected.add(w4);
        worker.setMatched_workers(workerConnected);

        Team t1 = new Team(01,"红队");
        Team t2 = new Team(02,"橙队");
        Team t3 = new Team(03,"黄队");
        Team t4 = new Team(04,"绿队");
        Team t5 = new Team(05,"青队");
        Team t6 = new Team(06,"全队");
        teams.add(t1);
        teams.add(t2);
        teams.add(t3);
        teams.add(t4);
        teams.add(t5);
        teams.add(t6);
        worker.setEx_teams(teams);


        Project p1 = new Project("明珠");
        Project p2 = new Project("和谐城");
        Project p3 = new Project("世纪嘉园");
        Project p4 = new Project("博物馆");
        Project p5 = new Project("世博园");
        projects.add(p1);
        projects.add(p2);
        projects.add(p3);
        projects.add(p4);
        projects.add(p5);
        worker.setEx_projects(projects);

        return worker;
    }

}
