package com.example.muhammadafiffauzi.abma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText userEmail, userPassword, userName;
    private ProgressDialog loading;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnSubmit = (Button) findViewById(R.id.btn_register_submit);
        userEmail = (EditText) findViewById(R.id.input_email);
        userPassword = (EditText) findViewById(R.id.input_password);
        loading = new ProgressDialog(this);

        //ketika button register di tekan
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                loading.setTitle("Creating new account");
                loading.setMessage("Please wait...");
                loading.setCanceledOnTouchOutside(true);
                loading.show();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "enter your email...", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "enter your password...", Toast.LENGTH_SHORT).show();

                } else {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                sendUserToMainActivity();
                                Toast.makeText(RegisterActivity.this, "Account has been created", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            } else {
                                String errorMsg = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Failed : "+ errorMsg, Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    private void createNewAccount() {

    }

    private void sendUserToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


}
