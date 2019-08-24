package com.example.muhammadafiffauzi.abma.History;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.muhammadafiffauzi.abma.Adapter.Q1L1adapter;
import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoriQ1L1 extends AppCompatActivity {

    private ListView listViewQ1L1;
    private String currentUser;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    List<Question1Model> question1Models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_q1_l1);


        listViewQ1L1 = (ListView) findViewById(R.id.listviewQ1L1);

        question1Models = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Question1").child(currentUser);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                question1Models.clear();

                for(DataSnapshot q1l1snapshot : dataSnapshot.getChildren()){
                    Question1Model question1Model = q1l1snapshot.getValue(Question1Model.class);

                    question1Models.add(question1Model);
                }

                Q1L1adapter adapter = new Q1L1adapter(HistoriQ1L1.this, question1Models);
                listViewQ1L1.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
