package com.example.muhammadafiffauzi.abma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {

    private TextView highScoreTV;
    private Button btnToLeaderBoard;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highScoreTV = (TextView) findViewById(R.id.highScoreTV);
        btnToLeaderBoard = (Button) findViewById(R.id.btnToLeaderboard);

    }
}
