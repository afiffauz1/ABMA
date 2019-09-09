package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HighscoreActivity extends AppCompatActivity {

    private TextView highScoreTV;
    private Button btnToLeaderBoard;
    private String currentUserId;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highScoreTV = (TextView) findViewById(R.id.highScoreTV);
        btnToLeaderBoard = (Button) findViewById(R.id.btnToLeaderboard);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("Lesson1HS").child(currentUserId);
        userDatabase = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);

        btnToLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighscoreActivity.this, Leaderboard.class);
                startActivity(intent);
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String scoreQ1 = dataSnapshot.child("quest1TS").child("questScore").getValue(String.class);
                int q1 = Integer.valueOf(scoreQ1);
                String scoreQ2 = dataSnapshot.child("quest2TS").child("questScore").getValue(String.class);
                int q2 = Integer.valueOf(scoreQ2);
                String scoreQ3 = dataSnapshot.child("quest3TS").child("questScore").getValue(String.class);
                int q3 = Integer.valueOf(scoreQ3);
                String scoreQ4 = dataSnapshot.child("quest4TS").child("questScore").getValue(String.class);
                int q4 = Integer.valueOf(scoreQ4);
                String scoreQ5 = dataSnapshot.child("quest5TS").child("questScore").getValue(String.class);
                int q5 = Integer.valueOf(scoreQ5);

                int lesson1HS = q1 + q2 + q3 + q4 + q5;
                String s = String.valueOf(lesson1HS);

                highScoreTV.setText(s);
                userDatabase.child("highscore").setValue(lesson1HS);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
