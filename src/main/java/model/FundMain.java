package model;

public class FundMain {
    private String name;
    private String code;
    private String yesterday;
    private String ydvalue;
    private String today;
    private String tdvalue;
    private String rise_fall;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getYdvalue() {
        return ydvalue;
    }

    public void setYdvalue(String ydvalue) {
        this.ydvalue = ydvalue;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTdvalue() {
        return tdvalue;
    }

    public void setTdvalue(String tdvalue) {
        this.tdvalue = tdvalue;
    }

    public String getRise_fall() {
        return rise_fall;
    }

    public void setRise_fall(String rise_fall) {
        this.rise_fall = rise_fall;
    }
}
