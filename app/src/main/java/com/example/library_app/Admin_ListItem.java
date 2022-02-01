package com.example.library_app;

public class Admin_ListItem {
    private String title;       // 책의 제목
    private String duedate;     // 반납기한
    private String current_location;
    private String right_location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public String getRight_location() {
        return right_location;
    }

    public void setRight_location(String right_location) {
        this.right_location = right_location;
    }
}
