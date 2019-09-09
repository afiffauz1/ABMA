package com.example.muhammadafiffauzi.abma.Model;

public class Users {
    private String uid;
    private String name;
    private String status;
    private int highscore;

    public Users(){}

    public Users(String uid, String name, String status, int highscore) {
        this.uid = uid;
        this.name = name;
        this.status = status;
        this.highscore = highscore;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
