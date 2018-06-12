package technovision.itsp.studentapp;


public class attendancefordate {
    private String date;
    private String string;

    public attendancefordate(String date, String string) {
        this.date = date;
        this.string = string;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
