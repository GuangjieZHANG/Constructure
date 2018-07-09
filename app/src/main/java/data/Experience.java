package data;

public class Experience {

    private int workerId;
    private String team;
    private String project;
    private String start;
    private String end;

    public Experience() {
    }

    public Experience(String team, String project, String start, String end) {
        this.team = team;
        this.project = project;
        this.start = start;
        this.end = end;
    }

    public Experience(int workerId, String team, String project, String start, String end) {
        this.workerId = workerId;
        this.team = team;
        this.project = project;
        this.start = start;
        this.end = end;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "workerId=" + workerId +
                ", team='" + team + '\'' +
                ", project='" + project + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
