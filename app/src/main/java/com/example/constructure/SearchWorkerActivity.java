package com.example.constructure;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import data.WorkerSearched;
import views.WorkerSearchedAdapter;

public class SearchWorkerActivity extends Activity {

    private Spinner type;
    private ListView workers;
    private Button search;
    private String typeChoosen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_worker);

        type = (Spinner)findViewById(R.id.type);
        workers = (ListView)findViewById(R.id.workers);
        search = (Button)findViewById(R.id.search);
        SpinnerAdapter adapter = new ArrayAdapter<String>(SearchWorkerActivity.this,R.layout.support_simple_spinner_dropdown_item,getTypes());
        type.setAdapter(adapter);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                typeChoosen = type.getSelectedItem().toString();
                //在这里将数据发回到服务器以获取信息
                System.out.println(typeChoosen);
                WorkerSearchedAdapter workerSearchedAdapter = new WorkerSearchedAdapter(SearchWorkerActivity.this,R.layout.worker_item,getWorkerData());
                workers.setAdapter(workerSearchedAdapter);
            }
        });


    }

    //将来这两个函数需要向服务器请求数据  然后才能得到相应的数据
    private String[] getTypes(){
        String[] types = {"木工","建筑工","油漆工","水泥工"};
        return types;
    }

    private ArrayList<WorkerSearched> getWorkerData(){
        ArrayList<WorkerSearched> workers = new ArrayList<>();
        WorkerSearched toAdd0 = new WorkerSearched(01,"张伟","上海市",87,true);
        WorkerSearched toAdd1 = new WorkerSearched(02,"王思","许昌市",77,false);
        WorkerSearched toAdd2 = new WorkerSearched(03,"刘立","北京市",85,true);
        WorkerSearched toAdd3 = new WorkerSearched(01,"张伟","上海市",87,true);

        workers.add(toAdd0);
        workers.add(toAdd1);
        workers.add(toAdd2);
        workers.add(toAdd3);

        return workers;
    }


}
