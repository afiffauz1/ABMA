package com.example.muhammadafiffauzi.abma.History;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.muhammadafiffauzi.abma.Holder.Question3ViewHolder;
import com.example.muhammadafiffauzi.abma.Holder.Question5ViewHolder;
import com.example.muhammadafiffauzi.abma.Model.Question3Model;
import com.example.muhammadafiffauzi.abma.Model.Question5Model;
import com.example.muhammadafiffauzi.abma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoriQ5L1 extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String currentUser;

    private FirebaseRecyclerOptions<Question5Model> options;
    private FirebaseRecyclerAdapter<Question5Model, Question5ViewHolder> adapter;

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
        setContentView(R.layout.activity_histori_q5_l1);

        recyclerView = (RecyclerView) findViewById(R.id.myRV5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Question5").child(currentUser);
        mDatabase.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Question5Model>().setQuery(mDatabase, Question5Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Question5Model, Question5ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Question5ViewHolder holder, int position, @NonNull Question5Model model) {
                holder.scoreAlfabetView.setText("Score : "+model.getScoreAlfa());
                holder.scoreView.setText("Overall Score : "+model.getQuest5Score());
                holder.dateView.setText("Date Play : "+model.getDate());
            }

            @NonNull
            @Override
            public Question5ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Question5ViewHolder(LayoutInflater.from(HistoriQ5L1.this).inflate(R.layout.histori_list_layout, viewGroup, false));
            }
        };

        recyclerView.setAdapter(adapter);

    }
}
