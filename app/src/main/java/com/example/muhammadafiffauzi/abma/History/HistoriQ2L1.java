package com.example.muhammadafiffauzi.abma.History;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.muhammadafiffauzi.abma.Holder.Question2ViewHolder;
import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.Model.Question2Model;
import com.example.muhammadafiffauzi.abma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoriQ2L1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;
    private ArrayList<Question2Model> arrayList;

    private FirebaseRecyclerOptions<Question2Model> options;
    private FirebaseRecyclerAdapter<Question2Model, Question2ViewHolder> adapter;

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
        setContentView(R.layout.activity_histori_q2_l1);

        recyclerView = (RecyclerView) findViewById(R.id.myRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Question2").child(currentUser);
        mDatabase.keepSynced(true);

        arrayList = new ArrayList<Question2Model>();

        options = new FirebaseRecyclerOptions.Builder<Question2Model>().setQuery(mDatabase, Question2Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Question2Model, Question2ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Question2ViewHolder holder, int position, @NonNull Question2Model model) {
                holder.scoreAlfabet.setText("Score : "+model.getScoreAlfabet());
                holder.scoreView.setText("Overall Score : "+model.getQuest2Score());
                holder.dateView.setText("Date Play : "+model.getDate());
            }

            @NonNull
            @Override
            public Question2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Question2ViewHolder(LayoutInflater.from(HistoriQ2L1.this).inflate(R.layout.histori_list_layout, viewGroup, false));

            }
        };

        recyclerView.setAdapter(adapter);
    }
}
