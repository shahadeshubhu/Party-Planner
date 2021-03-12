package com.sjsu.partyplanner.Activities.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserController controller;
    public static final int REQUIRED_PWD_LEN = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up Error Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot());
        setUpListeners();
        controller = new UserController();

    }


    /**
     * Sets up listeners for the textboxes
     */
    public void setUpListeners() {

        // Textbox Drawables
        Drawable normalTB = getResources().getDrawable(R.drawable.textbox_background);
        Drawable errorTB = getResources().getDrawable(R.drawable.text_box_error);

        // Email TB TextListener
        binding.emailLoginTB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String currentInput = binding.emailLoginTB.getText().toString();
                String emailAcceptedPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (currentInput.length() <= 0 || !currentInput.matches(emailAcceptedPattern)) {
                    binding.emailLoginTB.setBackground(errorTB);
                    binding.loginErrorMsg.setText("Invalid email");
                }
                else {
                    binding.emailLoginTB.setBackground(normalTB);
                    binding.loginErrorMsg.setText("");
                }
            }
        });

        // Password TB Listener
        binding.pwLoginTB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String currentInput = binding.pwLoginTB.getText().toString();
                if(currentInput.length() < 8){
                    binding.pwLoginTB.setBackground(errorTB);
                    binding.loginErrorMsg.setText(String.format("Password must be at least %d characters", REQUIRED_PWD_LEN));
                }
                else{
                    binding.pwLoginTB.setBackground(normalTB);
                    binding.loginErrorMsg.setText("");
                }
            }
        });
    }



    //-------------------------------------------------

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
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
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