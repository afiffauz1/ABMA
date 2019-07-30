package com.example.muhammadafiffauzi.abma.Model;

public class Lesson1 {
    private String idLesson1, quest1Score, quest2Score, quest3Score, quest4Score, quest5Score;

    public Lesson1(){

    }

    public Lesson1(String idLesson1, String quest1Score, String quest2Score, String quest3Score, String quest4Score, String quest5Score) {
        this.idLesson1 = idLesson1;
        this.quest1Score = quest1Score;
        this.quest2Score = quest2Score;
        this.quest3Score = quest3Score;
        this.quest4Score = quest4Score;
        this.quest5Score = quest5Score;
    }

    public String getIdLesson1() {
        return idLesson1;
    }

    public void setIdLesson1(String idLesson1) {
        this.idLesson1 = idLesson1;
    }

    public String getQuest1Score() {
        return quest1Score;
    }

    public void setQuest1Score(String quest1Score) {
        this.quest1Score = quest1Score;
    }

    public String getQuest2Score() {
        return quest2Score;
    }

    public void setQuest2Score(String quest2Score) {
        this.quest2Score = quest2Score;
    }

    public String getQuest3Score() {
        return quest3Score;
    }

    public void setQuest3Score(String quest3Score) {
        this.quest3Score = quest3Score;
    }

    public String getQuest4Score() {
        return quest4Score;
    }

    public void setQuest4Score(String quest4Score) {
        this.quest4Score = quest4Score;
    }

    public String getQuest5Score() {
        return quest5Score;
    }

    public void setQuest5Score(String quest5Score) {
        this.quest5Score = quest5Score;
    }
}
