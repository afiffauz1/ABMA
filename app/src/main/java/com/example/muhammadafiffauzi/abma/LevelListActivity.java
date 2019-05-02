package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelListActivity extends AppCompatActivity {

    Button btnLesson1, btnLesson2, btnLesson3, btnLesson4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);

        btnLesson1 = (Button) findViewById(R.id.btn_lesson1);
        btnLesson2 = (Button) findViewById(R.id.btn_lesson2);
        btnLesson3 = (Button) findViewById(R.id.btn_lesson3);
        btnLesson4 = (Button) findViewById(R.id.btn_lesson4);

        btnLesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelListActivity.this, SelectLesson1Activity.class);
                startActivity(intent);
            }
        });
    }
}
