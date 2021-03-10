package com.sjsu.partyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * onClick method for the log in button
     * Logs the user in and goes to the home page
     */
    public void loginClick(View view) {
        toastMsg("Login Button");
    }

    /**
     * onClick method for 'sign up'
     * Goes to the 'sign up' page
     */
    public void signupPageClick(View view) {
        toastMsg("Go to sign up page");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }


    /**
     * onClick method for 'forgot password'
     * Goes to the 'reset password' page
     */
    public void forgotPWClick(View view) {
        toastMsg("Forgot Password");
    }

    /**
     * onClick method for Google Icon
     */
    public void googleClick(View view) {
        toastMsg("Google Button");
    }

    /**
     * onClick method for Twitter Icon
     */
    public void twitterClick(View view) {
        toastMsg("Twitter Button");
    }

    /**
     * onClick method for Facebook Icon
     */
    public void facebookClick(View view) {
        toastMsg("Facebook Button");
    }

    /**
     * Testing onClick methods
     * @param msg to toast
     */
    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}