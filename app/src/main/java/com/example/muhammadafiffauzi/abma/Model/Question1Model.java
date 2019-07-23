package com.example.muhammadafiffauzi.abma.Model;

import com.example.muhammadafiffauzi.abma.SelectLesson.SelectLesson1Activity;

import java.util.ArrayList;

public class Question1Model {
    private String idQuest1;
    private String quest1Score;

    public Question1Model(SelectLesson1Activity selectLesson1Activity, ArrayList<Question1Model> arrayList){

    }

    public Question1Model(String idQuest1, String quest1Score) {
        this.idQuest1 = idQuest1;
        this.quest1Score = quest1Score;
    }

    public String getIdQuest1() {
        return idQuest1;
    }

    public void setIdQuest1(String idQuest1) {
        this.idQuest1 = idQuest1;
    }

    public String getQuest1Score() {
        return quest1Score;
    }

    public void setQuest1Score(String quest1Score) {
        this.quest1Score = quest1Score;
    }
}
