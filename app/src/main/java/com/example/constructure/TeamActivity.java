package com.example.constructure;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CurrentWorker;
import data.Project;
import data.Team;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);

        hListViewCurrentWorker = (HorizontalListView)findViewById(R.id.current_worker_team);
        hListViewProjects = (HorizontalListView)findViewById(R.id.ex_projects_team);
        teamName = (TextView)findViewById(R.id.team_name);
        teamImage = (ImageView)findViewById(R.id.team_image);
        laborCom = (TextView)findViewById(R.id.team_labor);

        Team test = getData();

        teamName.setText(test.getName());
        laborCom.setText(test.getLabor_companys());

        CurrentWorkerAdapter currentWorkerAdapter = new CurrentWorkerAdapter(TeamActivity.this,R.layout.current_worker_item,test.getCurrent_workers());
        hListViewCurrentWorker.setAdapter(currentWorkerAdapter);

        ProjectAdapter projectAdapter = new ProjectAdapter(TeamActivity.this,R.layout.project_item,test.getEx_projects());
        hListViewProjects.setAdapter(projectAdapter);

    }

    private Team getData(){

        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<CurrentWorker> currentWorkers = new ArrayList<>();
        Team team = new Team();

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
        projects.add(p5);

        team.setCurrent_workers(currentWorkers);
        team.setEx_projects(projects);
        return team;

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
