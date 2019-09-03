package com.example.muhammadafiffauzi.abma.History;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.muhammadafiffauzi.abma.Holder.Question3ViewHolder;
import com.example.muhammadafiffauzi.abma.Model.Question3Model;
import com.example.muhammadafiffauzi.abma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoriQ3L1 extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String currentUser;

    private FirebaseRecyclerOptions<Question3Model> options;
    private FirebaseRecyclerAdapter<Question3Model, Question3ViewHolder> adapter;

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
        setContentView(R.layout.activity_histori_q3_l1);

        recyclerView = (RecyclerView) findViewById(R.id.myRV3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Question3").child(currentUser);
        mDatabase.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Question3Model>().setQuery(mDatabase, Question3Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Question3Model, Question3ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Question3ViewHolder holder, int position, @NonNull Question3Model model) {
                holder.scoreAlfabetView.setText("Score : "+model.getScoreAlfabet());
                holder.scoreView.setText("Overall Score : "+model.getQuest3Score());
                holder.dateView.setText("Date Play : "+model.getDate());
            }

            @NonNull
            @Override
            public Question3ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Question3ViewHolder(LayoutInflater.from(HistoriQ3L1.this).inflate(R.layout.histori_list_layout, viewGroup, false));
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
