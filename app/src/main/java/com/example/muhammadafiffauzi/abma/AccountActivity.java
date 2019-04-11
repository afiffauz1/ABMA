package com.example.muhammadafiffauzi.abma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AccountActivity extends AppCompatActivity {

    private Button btn_update;
    private EditText userName, userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btn_update = (Button) findViewById(R.id.btn_update);
        userName = (EditText) findViewById(R.id.set_username);
        userStatus = (EditText) findViewById(R.id.set_status);
    }
}
