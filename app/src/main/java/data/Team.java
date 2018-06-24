package data;

import java.util.ArrayList;

public class Team {

    private int id;
    private String name;
    private String labor_companys;
    private ArrayList<Project> ex_projects;
    private ArrayList<CurrentWorker> current_workers;

    public Team() {
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(String name, String labor_companys, ArrayList<Project> ex_projects, ArrayList<CurrentWorker> current_workers) {
        this.name = name;
        this.labor_companys = labor_companys;
        this.ex_projects = ex_projects;
        this.current_workers = current_workers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabor_companys() {
        return labor_companys;
    }

    public void setLabor_companys(String labor_companys) {
        this.labor_companys = labor_companys;
    }

    public ArrayList<Project> getEx_projects() {
        return ex_projects;
    }

    public void setEx_projects(ArrayList<Project> ex_projects) {
        this.ex_projects = ex_projects;
    }

    public ArrayList<CurrentWorker> getCurrent_workers() {
        return current_workers;
    }

    public void setCurrent_workers(ArrayList<CurrentWorker> current_workers) {
        this.current_workers = current_workers;
    }

}
