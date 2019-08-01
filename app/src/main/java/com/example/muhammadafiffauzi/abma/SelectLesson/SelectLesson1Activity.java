package com.example.muhammadafiffauzi.abma.SelectLesson;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammadafiffauzi.abma.HighscoreActivity;
import com.example.muhammadafiffauzi.abma.LevelListActivity;
import com.example.muhammadafiffauzi.abma.Model.Lesson1;
import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.Model.QuestionTotalScore;
import com.example.muhammadafiffauzi.abma.R;
import com.example.muhammadafiffauzi.abma.ScoreActivity;
import com.example.muhammadafiffauzi.abma.questions.Quest1Lesson1Activity;
import com.example.muhammadafiffauzi.abma.questions.Quest2Lesson1Activity;
import com.example.muhammadafiffauzi.abma.questions.Quest3Lesson1Activity;
import com.example.muhammadafiffauzi.abma.questions.Quest4Lesson1Activity;
import com.example.muhammadafiffauzi.abma.questions.Quest5Lesson1Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectLesson1Activity extends AppCompatActivity {

    private Button btnQuest1, btnQuest2, btnQuest3, btnQuest4, btnQuest5;
    //private TextView lesson1TotalScore;

    private String currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference lesson1Database, lesson2Database, mDatabase;

    ArrayList<Question1Model> arrayList =  new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lesson1);

        btnQuest1 = (Button) findViewById(R.id.question1);
        btnQuest2 = (Button) findViewById(R.id.question2);
        btnQuest3 = (Button) findViewById(R.id.question3);
        btnQuest4 = (Button) findViewById(R.id.question4);
        btnQuest5 = (Button) findViewById(R.id.question5);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        lesson1Database = FirebaseDatabase.getInstance().getReference("Question1").child(currentUserId);
        lesson2Database = FirebaseDatabase.getInstance().getReference("Question2").child(currentUserId);
        mDatabase = FirebaseDatabase.getInstance().getReference("Lesson1HS").child(currentUserId);


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
        btnQuest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToQuest3();
            }
        });
        btnQuest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToQuest4();
            }
        });
        btnQuest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToQuest5();
            }
        });

        lesson1Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int total = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String score = ds.child("quest1Score").getValue(String.class);
                    int value = Integer.valueOf(score);
                    total += value;
                }

                String totalQ1 = String.valueOf(total);

                QuestionTotalScore questionTotalScore = new QuestionTotalScore(totalQ1);

                mDatabase.child("quest1TS").setValue(questionTotalScore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lesson2Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int total = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String score = ds.child("quest2Score").getValue(String.class);
                    int value = Integer.valueOf(score);
                    total += value;
                }
                String totalQ2 = String.valueOf(total);

                QuestionTotalScore questionTotalScore = new QuestionTotalScore(totalQ2);

                mDatabase.child("quest2TS").setValue(questionTotalScore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    private void sendUserToQuest3() {
        Intent intent = new Intent(SelectLesson1Activity.this, Quest3Lesson1Activity.class);
        startActivity(intent);
    }
    private void sendUserToQuest4() {
        Intent intent = new Intent(SelectLesson1Activity.this, Quest4Lesson1Activity.class);
        startActivity(intent);
    }
    private void sendUserToQuest5() {
        Intent intent = new Intent(SelectLesson1Activity.this, Quest5Lesson1Activity.class);
        startActivity(intent);
    }


}
