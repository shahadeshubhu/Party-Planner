package com.sjsu.partyplanner.Activities.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.partyplanner.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        passwordEmail = (EditText)findViewById(R.id.passwordEmail);
        resetPassword = findViewById(R.id.SubmitBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(v -> {
            String userEmail = passwordEmail.getText().toString().trim();

            if(userEmail.equals("")){
                Toast.makeText(ForgotPasswordActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
            }else {
                firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,"Password reset email sent", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        }else {
                            Toast.makeText(ForgotPasswordActivity.this,"Error sending reset password email",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
