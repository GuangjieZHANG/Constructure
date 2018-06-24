package data;

public class CurrentWorker {

    private String speciality;
    private int number;
    private String note;

    public CurrentWorker(String speciality, int number, String note) {
        this.speciality = speciality;
        this.number = number;
        this.note = note;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
