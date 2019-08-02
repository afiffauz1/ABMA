package com.example.muhammadafiffauzi.abma;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highScoreTV = (TextView) findViewById(R.id.highScoreTV);
        btnToLeaderBoard = (Button) findViewById(R.id.btnToLeaderboard);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("Lesson1HS").child(currentUserId);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String scoreQ1 = dataSnapshot.child("quest1TS").child("questScore").getValue(String.class);
                String scoreQ2 = dataSnapshot.child("quest2TS").child("questScore").getValue(String.class);

//              Toast.makeText(HighscoreActivity.this, "quest2 : "+scoreQ2 , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
