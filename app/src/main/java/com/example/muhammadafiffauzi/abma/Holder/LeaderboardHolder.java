package com.example.muhammadafiffauzi.abma.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.R;

public class LeaderboardHolder extends RecyclerView.ViewHolder {

    public TextView userNameView, userScoreView, nomorUrut;

    public LeaderboardHolder(@NonNull View itemView){
        super(itemView);

        userNameView = itemView.findViewById(R.id.userName);
        userScoreView = itemView.findViewById(R.id.userScore);
    }
}
