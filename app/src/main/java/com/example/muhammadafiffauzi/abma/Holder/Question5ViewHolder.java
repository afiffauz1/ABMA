package com.example.muhammadafiffauzi.abma.Holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.R;

public class Question5ViewHolder extends RecyclerView.ViewHolder {

    public TextView scoreAlfabetView, scoreView, dateView;

    public Question5ViewHolder(@NonNull View view){
        super(view);

        scoreAlfabetView = view.findViewById(R.id.hisScoreAlfaView);
        scoreView = view.findViewById(R.id.hisScoreView);
        dateView = view.findViewById(R.id.hisDateView);
    }
}
