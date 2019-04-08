package com.example.muhammadafiffauzi.abma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;

    private EditText userEmail;
    private EditText userPassword;
    private Button btnLoginSubmit;
    private Button createAccount;

    private ProgressDialog loading;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.input_email);
        userPassword = (EditText) findViewById(R.id.input_password);
        btnLoginSubmit = (Button) findViewById(R.id.btn_login_submit);
        createAccount = (Button) findViewById(R.id.btn_create_account);

        loading = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        
        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowUserToLogin();
            }
        });

    }

    private void allowUserToLogin() {

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        loading.setTitle("Log in");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(true);
        loading.show();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "enter your email...", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "enter your password", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendUserToMainActivity();
                        Toast.makeText(LoginActivity.this, "Welcome...", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    } else {
                        String errorMsg = task.getException().getMessage();
                        Toast.makeText(LoginActivity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser != null){
            sendUserToMainActivity();
        }
    }

    private void sendUserToMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }
}
