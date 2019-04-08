package com.example.muhammadafiffauzi.abma;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText userEmail, userPassword;
    private ProgressDialog loading;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnSubmit = (Button) findViewById(R.id.btn_register_submit);
        userEmail = (EditText) findViewById(R.id.input_email);
        userPassword = (EditText) findViewById(R.id.input_password);
        loading = new ProgressDialog(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        loading.setTitle("Creating new account");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(true);
        loading.show();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this, "enter your email...", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "enter your password...", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendUserToLoginActivity();
                        Toast.makeText(RegisterActivity.this, "Account has been created", Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().getMessage();
                        Toast.makeText(RegisterActivity.this, "Failed : "+ errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}
