package com.example.muhammadafiffauzi.abma;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {



    private EditText userEmail;
    private EditText userPassword;
    private Button btnLoginSubmit;
    private Button createAccount;

    private ProgressDialog loading;
    private AlertDialog.Builder dialog;

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
        dialog = new AlertDialog.Builder(this);

        mAuth = FirebaseAuth.getInstance();

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

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            dialog.setTitle("Peringatan!!!");
            dialog.setMessage("Masukkan e-mail atau password anda dengan benar");
            dialog.setNegativeButton("OK", null);
            dialog.show();
            loading.dismiss();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendUserToMainActivity();
                        loading.dismiss();
                    } else {
                        String errorMsg = task.getException().getMessage();
                        dialog.setTitle("Peringatan!!!");
                        dialog.setMessage(errorMsg);
                        dialog.setNegativeButton("OK", null);
                        dialog.show();
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



    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
