package com.example.muhammadafiffauzi.abma.History;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.muhammadafiffauzi.abma.Holder.Question1ViewHolder;
import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoriQ1L1 extends AppCompatActivity {


    private RecyclerView mScoreList;
    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;

    private ArrayList<Question1Model> arrayList;

    private FirebaseRecyclerOptions<Question1Model> options;
    private FirebaseRecyclerAdapter<Question1Model, Question1ViewHolder> adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_q1_l1);

        mScoreList = (RecyclerView) findViewById(R.id.myRV);
        mScoreList.setHasFixedSize(true);
        mScoreList.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Question1").child(currentUser);
        mDatabase.keepSynced(true);

        arrayList = new ArrayList<Question1Model>();

        options = new FirebaseRecyclerOptions.Builder<Question1Model>().setQuery(mDatabase, Question1Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Question1Model, Question1ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Question1ViewHolder holder, int position, @NonNull Question1Model model) {
                holder.scoreView.setText("Overall Score : "+ model.getQuest1Score());
                holder.dateView.setText("Date Play : "+ model.getDate());
                holder.scoreAlfaView.setText("Score : "+model.getScoreAlfabet());
            }

            @NonNull
            @Override
            public Question1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Question1ViewHolder(LayoutInflater.from(HistoriQ1L1.this).inflate(R.layout.histori_list_layout, viewGroup, false));
            }
        };

        mScoreList.setAdapter(adapter);

    }

}
