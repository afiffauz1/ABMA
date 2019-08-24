package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.muhammadafiffauzi.abma.History.HistoriQ1L1;

public class HistoriActivity extends AppCompatActivity {

    private TextView quest1Histori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori);

        quest1Histori = (TextView) findViewById(R.id.ls1q1);

        quest1Histori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoriActivity.this, HistoriQ1L1.class);
                startActivity(intent);
            }
        });
    }
}
