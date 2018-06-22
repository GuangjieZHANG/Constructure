package data;

public class WorkerSearched {

    private int id;
    private String name;
    private String hometown;
    private int cci;//匹配程度  查询需要
    private boolean certified;

    public WorkerSearched() {
    }

    public WorkerSearched(int id, String name, String hometown, int cci, boolean certified) {
        this.id = id;
        this.name = name;
        this.hometown = hometown;
        this.cci = cci;
        this.certified = certified;
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

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public int getCci() {
        return cci;
    }

    public void setCci(int cci) {
        this.cci = cci;
    }

    public boolean isCertified() {
        return certified;
    }

    public void setCertified(boolean certified) {
        this.certified = certified;
    }
}
