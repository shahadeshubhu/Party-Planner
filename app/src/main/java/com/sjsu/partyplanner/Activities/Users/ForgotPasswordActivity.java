package com.sjsu.partyplanner.Activities.Users;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        passwordEmail = findViewById(R.id.passwordEmail);
        resetPassword = findViewById(R.id.SubmitBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(v -> firebaseAuth.sendPasswordResetEmail(passwordEmail.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPasswordActivity.this,
                        "Password reset email sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ForgotPasswordActivity.this,
                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
}


