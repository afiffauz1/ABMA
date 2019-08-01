package com.example.muhammadafiffauzi.abma.Model;

public class Lesson1 {
    private String idLesson1, quest1Score;

    public Lesson1() {
    }

    public Lesson1(String idLesson1, String quest1Score) {
        this.idLesson1 = idLesson1;
        this.quest1Score = quest1Score;
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
}
