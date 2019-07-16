package com.example.muhammadafiffauzi.abma.SelectLesson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.LevelListActivity;
import com.example.muhammadafiffauzi.abma.R;
import com.example.muhammadafiffauzi.abma.questions.Quest1Lesson1Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectLesson1Activity extends AppCompatActivity {

    private Button btnQuest1, btnQuest2, btnQuest3, btnQuest4, btnQuest5;
    private TextView lesson1TotalScore;

    private String currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lesson1);

        btnQuest1 = (Button) findViewById(R.id.question1);
        btnQuest2 = (Button) findViewById(R.id.question2);
        btnQuest3 = (Button) findViewById(R.id.question3);
        btnQuest4 = (Button) findViewById(R.id.question4);
        btnQuest5 = (Button) findViewById(R.id.question5);

        lesson1TotalScore = (TextView) findViewById(R.id.total_score);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        btnQuest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToQuest1();
            }
        });
        btnQuest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToQuest2();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SelectLesson1Activity.this, LevelListActivity.class);
        startActivity(intent);
    }

    private void sendUserToQuest1() {
        Intent intent = new Intent(SelectLesson1Activity.this, Quest1Lesson1Activity.class);
        startActivity(intent);
    }

    private void sendUserToQuest2() {
        Intent intent = new Intent(SelectLesson1Activity.this, Quest2Lesson1Activity.class);
        startActivity(intent);
    }
}