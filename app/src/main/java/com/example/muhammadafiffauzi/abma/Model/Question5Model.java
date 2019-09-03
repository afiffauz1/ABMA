package com.example.muhammadafiffauzi.abma.Model;

public class Question5Model {
    private String idQuest5;
    private String quest5Score;
    private String date;
    private String scoreAlfa;

    public Question5Model(){}

    public Question5Model(String idQuest5, String quest5Score, String date, String scoreAlfa) {
        this.idQuest5 = idQuest5;
        this.quest5Score = quest5Score;
        this.date = date;
        this.scoreAlfa = scoreAlfa;
    }

    public String getIdQuest5() {
        return idQuest5;
    }

    public void setIdQuest5(String idQuest5) {
        this.idQuest5 = idQuest5;
    }

    public String getQuest5Score() {
        return quest5Score;
    }

    public void setQuest5Score(String quest5Score) {
        this.quest5Score = quest5Score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScoreAlfa() {
        return scoreAlfa;
    }

    public void setScoreAlfa(String scoreAlfa) {
        this.scoreAlfa = scoreAlfa;
    }
}
