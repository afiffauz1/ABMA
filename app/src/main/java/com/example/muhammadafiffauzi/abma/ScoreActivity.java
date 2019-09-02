package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.SelectLesson.SelectLesson1Activity;
import com.example.muhammadafiffauzi.abma.questions.Quest2Lesson1Activity;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {

    private Button btnQuestion;
    private TextView scoreView;
    private String score;
    private String scoreAlfa;
    private TextView scoreAlfaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btnQuestion = (Button) findViewById(R.id.buttonQuestion);
        scoreView = (TextView) findViewById(R.id.scoreNum);
        scoreAlfaView = (TextView) findViewById(R.id.scoreAlfa);

        Bundle extras = getIntent().getExtras();
        scoreAlfaView.setText(extras.getString("scoreAlfa"));
        scoreView.setText("Your Score : "+extras.getString("score"));

        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLesson1Activity();
            }
        });
    }

    private void sendUserToLesson1Activity(){
        Intent intent = new Intent(ScoreActivity.this, SelectLesson1Activity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendUserToLesson1Activity();
    }
}
