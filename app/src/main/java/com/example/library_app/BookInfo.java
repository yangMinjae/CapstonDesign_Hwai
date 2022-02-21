package com.example.library_app;

public class BookInfo {
    private int id;
    private String title;
    private String current;
    private String due_date;
    private String shelfid;

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

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getShelfid() {
        return shelfid;
    }

    public void setShelfid(String shelfid) {
        this.shelfid = shelfid;
    }
}
