package com.example.muhammadafiffauzi.abma.Model;

public class Question3Model {
    private String idQuest3;
    private String quest3Score;
    private String date;
    private String scoreAlfabet;

    public Question3Model(){

    }

    public Question3Model(String idQuest3, String quest3Score, String date, String scoreAlfabet) {
        this.idQuest3 = idQuest3;
        this.quest3Score = quest3Score;
        this.date = date;
        this.scoreAlfabet = scoreAlfabet;
    }

    public String getIdQuest3() {
        return idQuest3;
    }

    public void setIdQuest3(String idQuest3) {
        this.idQuest3 = idQuest3;
    }

    public String getQuest3Score() {
        return quest3Score;
    }

    public void setQuest3Score(String quest3Score) {
        this.quest3Score = quest3Score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScoreAlfabet() {
        return scoreAlfabet;
    }

    public void setScoreAlfabet(String scoreAlfabet) {
        this.scoreAlfabet = scoreAlfabet;
    }
}
