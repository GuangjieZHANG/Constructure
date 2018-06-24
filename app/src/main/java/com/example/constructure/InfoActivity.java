package com.example.constructure;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.Experience;
import views.ExperienceAdapter;

public class InfoActivity extends Activity {

    private TextView name;
    private ImageView image;
    private TextView updateCer;
    private ListView experience;
    private Button updateExp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        name = (TextView)findViewById(R.id.info_name);
        image = (ImageView)findViewById(R.id.info_image);
        updateCer = (TextView)findViewById(R.id.info_update_certi);
        updateExp = (Button)findViewById(R.id.info_update_exp);
        experience = (ListView)findViewById(R.id.info_experience);

        ArrayList<Experience> experiences = getData();
        ExperienceAdapter adapter = new ExperienceAdapter(InfoActivity.this,R.layout.experience_item,experiences);
        experience.setAdapter(adapter);

    }

    private ArrayList<Experience> getData(){
        ArrayList<Experience> experiences = new ArrayList<>();
        Experience e1 = new Experience(1,"team1","project1","2017/01","2017/02");
        Experience e2 = new Experience(2,"team1","project1","2017/01","2017/02");
        Experience e3 = new Experience(3,"team1","project1","2017/01","2017/02");
        Experience e4 = new Experience(4,"team1","project1","2017/01","2017/02");
        Experience e5 = new Experience(5,"team1","project1","2017/01","2017/02");
        experiences.add(e1);
        experiences.add(e2);
        experiences.add(e3);
        experiences.add(e4);
        experiences.add(e5);
        return experiences;
    }


}
