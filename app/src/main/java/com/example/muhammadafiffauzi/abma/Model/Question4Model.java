package com.example.muhammadafiffauzi.abma.Model;

public class Question4Model {
    private String idQuest4;
    private String quest4Score;
    private String date;
    private String scoreAlfa;

    public Question4Model(){

    }

    public Question4Model(String idQuest4, String quest4Score, String date, String scoreAlfa) {
        this.idQuest4 = idQuest4;
        this.quest4Score = quest4Score;
        this.date = date;
        this.scoreAlfa = scoreAlfa;
    }

    public String getIdQuest4() {
        return idQuest4;
    }

    public void setIdQuest4(String idQuest4) {
        this.idQuest4 = idQuest4;
    }

    public String getQuest4Score() {
        return quest4Score;
    }

    public void setQuest4Score(String quest4Score) {
        this.quest4Score = quest4Score;
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
