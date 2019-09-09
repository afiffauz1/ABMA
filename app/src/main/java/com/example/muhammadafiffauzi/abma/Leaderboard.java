package com.example.muhammadafiffauzi.abma;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.muhammadafiffauzi.abma.Holder.LeaderboardHolder;
import com.example.muhammadafiffauzi.abma.Holder.Question1ViewHolder;
import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.Model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView mLeaderBoardList;
    private DatabaseReference mDatabase;
    private String currentUser;
    private FirebaseAuth mAuth;

    private ArrayList<Users> arrayList;

    private FirebaseRecyclerOptions<Users> options;
    private FirebaseRecyclerAdapter<Users, LeaderboardHolder> adapter;

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
        setContentView(R.layout.activity_leaderboard);

        mLeaderBoardList = (RecyclerView) findViewById(R.id.leaderboardRV);
        mLeaderBoardList.setHasFixedSize(true);
        mLeaderBoardList.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);

        arrayList = new ArrayList<Users>();

        options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(mDatabase, Users.class).build();

        adapter = new FirebaseRecyclerAdapter<Users, LeaderboardHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull LeaderboardHolder holder, int position, @NonNull Users model) {
                holder.userNameView.setText(model.getName());
                String hs = String.valueOf(model.getHighscore());
                holder.userScoreView.setText(hs);
            }

            @NonNull
            @Override
            public LeaderboardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new LeaderboardHolder(LayoutInflater.from(Leaderboard.this).inflate(R.layout.leaderboard_layout, viewGroup, false));
            }
        };

        mLeaderBoardList.setAdapter(adapter);
    }
}
