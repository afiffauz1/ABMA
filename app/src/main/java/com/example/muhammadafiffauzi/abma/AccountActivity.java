package com.example.muhammadafiffauzi.abma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muhammadafiffauzi.abma.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private Button btn_update;
    private EditText userName, userStatus;
    private CircleImageView userProfpic;

    private String currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btn_update = (Button) findViewById(R.id.btn_update);
        userName = (EditText) findViewById(R.id.set_username);
        userStatus = (EditText) findViewById(R.id.set_status);
        userProfpic = (CircleImageView) findViewById(R.id.user_profpic);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });

        mDatabase.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name_view = dataSnapshot.child("name").getValue().toString();
                    String status_view = dataSnapshot.child("status").getValue().toString();

                    userName.setText(name_view);
                    userStatus.setText(status_view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendUserToMainActivity();
    }

    private void updateAccount() {

        String setUserName = userName.getText().toString();
        String setStatus = userStatus.getText().toString();

        if (TextUtils.isEmpty(setUserName)){

            Toast.makeText(AccountActivity.this, "please input your user name first...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(setStatus)) {

            Toast.makeText(AccountActivity.this, "please input your status...", Toast.LENGTH_SHORT).show();
        } else  {

            Users users = new Users(currentUserId, setUserName, setStatus, 0);
            mDatabase.child("Users").child(currentUserId).setValue(users);
            sendUserToMainActivity();
            Toast.makeText(AccountActivity.this, "Profile has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(AccountActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
