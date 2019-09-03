package com.example.muhammadafiffauzi.abma.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.R;

public class Question1ViewHolder extends RecyclerView.ViewHolder {

    public TextView scoreView, dateView, scoreAlfaView;

    public Question1ViewHolder(@NonNull View itemView){
        super(itemView);

        scoreView = itemView.findViewById(R.id.hisScoreView);
        dateView = itemView.findViewById(R.id.hisDateView);
        scoreAlfaView = itemView.findViewById(R.id.hisScoreAlfaView);
    }
}
