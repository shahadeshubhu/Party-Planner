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
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        passwordEmail = findViewById(R.id.passwordEmail);
        Button resetPassword = findViewById(R.id.SubmitBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(v -> firebaseAuth.sendPasswordResetEmail(passwordEmail.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                toastMsg("Password reset email sent");
            } else {
                toastMsg(task.getException().getMessage());
            }
        }));
    }

    // Toast message
    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}


