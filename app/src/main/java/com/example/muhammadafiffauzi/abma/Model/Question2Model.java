package com.example.muhammadafiffauzi.abma.Model;

public class Question2Model {
    private String idQuest2;
    private String quest2Score;

    public Question2Model(){

    }

    public Question2Model(String idQuest2, String quest2Score) {
        this.idQuest2 = idQuest2;
        this.quest2Score = quest2Score;
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
}
