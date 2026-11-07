package vanier.com.programmingpatternslia.Model;

public class Conference {

    private int id;
    private String title;
    private int capacity;
    private String c_date;
    private String start_time;
    private String end_time;
    private String location;

    public Conference(int id, String title, int capacity, String c_date, String start_time, String end_time, String location) {
        this.id = id;
        this.title = title;
        this.capacity = capacity;
        this.c_date = c_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
    }

    public Conference(String title, int capacity, String c_date, String start_time, String end_time, String location) {
        this.title = title;
        this.capacity = capacity;
        this.c_date = c_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", capacity=" + capacity +
                ", c_date='" + c_date + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
