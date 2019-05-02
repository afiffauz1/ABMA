package com.example.muhammadafiffauzi.abma.SelectLesson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.R;
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


    }
}
