package data;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Worker {

    private int id;
    private String name;
    private String speciality;
    private Bitmap picture;
    private int cci;//匹配程度  查询需要
    private String note;

    private ArrayList<Worker> matched_workers;
    private ArrayList<Project> ex_projects;
    private ArrayList<Team> ex_teams;

    //每个工人的详细页面
    public Worker(String name, String speciality, Bitmap picture, ArrayList<Worker> matched_workers, ArrayList<Project> ex_projects, ArrayList<Team> ex_teams) {
        this.name = name;
        this.speciality = speciality;
        this.picture = picture;
        this.matched_workers = matched_workers;
        this.ex_projects = ex_projects;
        this.ex_teams = ex_teams;
    }

    //队友的构造


    public Worker(int id, String name, String speciality, Bitmap picture, int cci, String note) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.picture = picture;
        this.cci = cci;
        this.note = note;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public int getCci() {
        return cci;
    }

    public void setCci(int cci) {
        this.cci = cci;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<Worker> getMatched_workers() {
        return matched_workers;
    }

    public void setMatched_workers(ArrayList<Worker> matched_workers) {
        this.matched_workers = matched_workers;
    }

    public ArrayList<Project> getEx_projects() {
        return ex_projects;
    }

    public void setEx_projects(ArrayList<Project> ex_projects) {
        this.ex_projects = ex_projects;
    }

    public ArrayList<Team> getEx_teams() {
        return ex_teams;
    }

    public void setEx_teams(ArrayList<Team> ex_teams) {
        this.ex_teams = ex_teams;
    }
}
