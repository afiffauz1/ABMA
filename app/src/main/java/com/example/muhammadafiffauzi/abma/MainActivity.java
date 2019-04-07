package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser == null){
        sendUserToLoginActivity();
    }
}

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
