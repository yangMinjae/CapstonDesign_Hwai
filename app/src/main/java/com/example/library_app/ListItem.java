package com.example.library_app;

public class ListItem {
    private String title;       // 책의 제목
    private String duedate;     // 반납기한
    private Boolean overdue;    //  연체 여부(연체시 True리턴, Title부분을 빨갛게 표시)

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

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }
}
