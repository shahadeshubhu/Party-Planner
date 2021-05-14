package com.sjsu.partyplanner.Activities.Users;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.partyplanner.Activities.Dashboard.DashboardActivity;
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
        controller = UserController.getInstance();

        // If user is already logged in, go to Dashboard
        if (FirebaseAuth.getInstance().getCurrentUser() !=null) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }



        // Set up Error Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot());
        setUpListeners();

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

    public void handleSuccess(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void handleError(String message){
        binding.loginErrorMsg.setText("Fail to log in");
    }

    //-------------------------------------------------

    /**
     * onClick method for the log in button
     * Logs the user in and goes to the home page
     */
    public void loginClick(View view) {

        boolean emailGood = false;
        boolean passwordGood = false;



        // Checks Email
        String email = binding.emailLoginTB.getText().toString();
        String emailAcceptedPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.length() <= 0 || !email.matches(emailAcceptedPattern)) {
            binding.emailLoginTB.setBackground(getResources().getDrawable(R.drawable.text_box_error));
        }
        else {
            binding.emailLoginTB.setBackground(getResources().getDrawable(R.drawable.textbox_background));
            emailGood = true;
        }

        // Checks password
        String password = binding.pwLoginTB.getText().toString();
        if(password.length() < 8){
            binding.pwLoginTB.setBackground(getResources().getDrawable(R.drawable.text_box_error));
        }
        else{
            binding.pwLoginTB.setBackground(getResources().getDrawable(R.drawable.textbox_background));
            passwordGood = true;
        }

        // Check Login
        if(passwordGood && emailGood) {
            controller.signInUser(this, binding.emailLoginTB.getText().toString(), binding.pwLoginTB.getText().toString());
        }
        else {
            binding.loginErrorMsg.setText(String.format("Invalid Login"));
        }
    }

    /**
     * onClick method for 'sign up'
     * Goes to the 'sign up' page
     */
    public void signupPageClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }


    /**
     * onClick method for 'forgot password'
     * Goes to the 'reset password' page
     */
    public void forgotPWClick(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
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

    // Toast message
    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}