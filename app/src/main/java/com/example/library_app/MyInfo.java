package com.example.library_app;

public class MyInfo { //메인로그인 화면에서 로그인 성공시 정보를 담는 클래스
    private int id;
    private String name;
    private String birth;
    private String tel;
    private String email;
    private Boolean admin;
    private String pw;
    private int pinNum;

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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getPinNum() {
        return pinNum;
    }

    public void setPinNum(int pinNum) {
        this.pinNum = pinNum;
    }
}
