package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.History.HistoriQ1L1;
import com.example.muhammadafiffauzi.abma.History.HistoriQ2L1;
import com.example.muhammadafiffauzi.abma.History.HistoriQ3L1;

public class HistoriActivity extends AppCompatActivity {

    private TextView quest1Histori, quest2Histori, quest3Histori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori);

        quest1Histori = (TextView) findViewById(R.id.ls1q1);
        quest2Histori = (TextView) findViewById(R.id.ls1q2);
        quest3Histori = (TextView) findViewById(R.id.ls1q3);

        quest1Histori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoriActivity.this, HistoriQ1L1.class);
                startActivity(intent);
            }
        });

        quest2Histori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoriActivity.this, HistoriQ2L1.class);
                startActivity(intent);
            }
        });

        quest3Histori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoriActivity.this, HistoriQ3L1.class);
                startActivity(intent);
            }
        });


    }
}
