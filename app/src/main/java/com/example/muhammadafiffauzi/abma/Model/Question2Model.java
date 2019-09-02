package com.example.muhammadafiffauzi.abma.Model;

public class Question2Model {
    private String idQuest2;
    private String quest2Score;
    private String date;
    private String scoreAlfabet;

    public Question2Model(){

    }

    public Question2Model(String idQuest2, String quest2Score, String date, String scoreAlfabet) {
        this.idQuest2 = idQuest2;
        this.quest2Score = quest2Score;
        this.date = date;
        this.scoreAlfabet = scoreAlfabet;
    }

    public String getIdQuest2() {
        return idQuest2;
    }

    public void setIdQuest2(String idQuest2) {
        this.idQuest2 = idQuest2;
    }

    public String getQuest2Score() {
        return quest2Score;
    }

    public void setQuest2Score(String quest2Score) {
        this.quest2Score = quest2Score;
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
