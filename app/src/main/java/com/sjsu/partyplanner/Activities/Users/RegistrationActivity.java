package com.sjsu.partyplanner.Activities.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sjsu.partyplanner.Activities.Dashboard.DashboardActivity;
import com.sjsu.partyplanner.Controllers.UserController;
import com.sjsu.partyplanner.R;
import com.sjsu.partyplanner.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {
  private ActivityRegistrationBinding binding;
  private UserController controller;
  public static final int REQUIRED_PWD_LEN = 8;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    controller =  UserController.getInstance();
    setContentView(R.layout.activity_registration);
    binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
    setContentView( binding.getRoot());
    setUpListeners();

    if (controller.isSignedIn()){
      Intent intent = new Intent(this, DashboardActivity.class);
      startActivity(intent);
      finish();
    }
  }

  /**
   * Handle next action when task from controller success
   */
  public void handleSuccess(){
    Intent intent = new Intent(this, DashboardActivity.class);
    startActivity(intent);
    finish();
  }

  /**
   * Handle error when task from controller failed
   */
  @SuppressLint("SetTextI18n")
  public void handleError(String message){
    if (message == null || message.length()<=0){
      binding.signupErrorMsg.setText("Fail to register a new user");
    }else
      binding.signupErrorMsg.setText(message);
  }

  public void registerUser(View view) {
    String fName = ((EditText) findViewById(R.id.fNameTB)).getText().toString();
    String lName = ((EditText) findViewById(R.id.lNameTB)).getText().toString();
    String email = ((EditText) findViewById(R.id.emailTB)).getText().toString();
    String pwd = ((EditText) findViewById(R.id.passTB)).getText().toString();
    String confirmPwd = ((EditText) findViewById(R.id.confirmPassTB)).getText().toString();

    if (!validateInput(fName, lName, email, pwd, confirmPwd)) {
      Toast.makeText(this,"Wrong Input", Toast.LENGTH_LONG);
    }else{
      controller.createAccount(this,email,pwd, fName,lName);
    }
  }

  public void loginPageClick(View view){
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }

  /**
   * Sets up listeners for the textboxes
   */
  public void setUpListeners(){
    // Textbox Drawables
    Drawable normalTB = getResources().getDrawable(R.drawable.textbox_background);
    Drawable errorTB = getResources().getDrawable(R.drawable.text_box_error);

    // First Name TB listener
    binding.fNameTB.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {
        String currentInput = binding.fNameTB.getText().toString();
        if(currentInput.length() <= 0){
          binding.fNameTB.setBackground(errorTB);
          binding.signupErrorMsg.setText("First name is required");
        }
        else{
          binding.fNameTB.setBackground(normalTB);
          binding.signupErrorMsg.setText("");
        }
      }
    });



    // Last Name TB Listener
    binding.lNameTB.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {
        String currentInput = binding.lNameTB.getText().toString();
        if(currentInput.length() <= 0){
          binding.lNameTB.setBackground(errorTB);
          binding.signupErrorMsg.setText("Last name is required");
        }
        else{
          binding.lNameTB.setBackground(normalTB);
          binding.signupErrorMsg.setText("");
        }
      }
    });

    // Email TB listener
    binding.emailTB.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {
        String currentInput = binding.emailTB.getText().toString();
        String emailAcceptedPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(currentInput.length() <= 0 || !currentInput.matches(emailAcceptedPattern)){
          binding.emailTB.setBackground(errorTB);
          binding.signupErrorMsg.setText("Invalid email");
        }
        else{
          binding.emailTB.setBackground(normalTB);
          binding.signupErrorMsg.setText("");
        }
      }
    });

    // Password TB Listener
    binding.passTB.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {
        String currentInput = binding.passTB.getText().toString();
        if(currentInput.length() < 8){
          binding.passTB.setBackground(errorTB);
          binding.signupErrorMsg.setText(String.format("Password must be at least %d characters", REQUIRED_PWD_LEN));

        }
        else{
          binding.passTB.setBackground(normalTB);
          binding.signupErrorMsg.setText("");
        }
      }
    });

    // Confirm Password TB Listner
    binding.confirmPassTB.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}

      @Override
      public void afterTextChanged(Editable s) {
        String currentInput = binding.confirmPassTB.getText().toString();
        if(currentInput.length() < REQUIRED_PWD_LEN || !currentInput.equals(binding.passTB.getText().toString()) ){
          binding.confirmPassTB.setBackground(errorTB);
          binding.signupErrorMsg.setText("Password does not match");
        }
        else{
          binding.confirmPassTB.setBackground(normalTB);
          binding.signupErrorMsg.setText("");
        }
      }

    });
  }


  private boolean validateInput(String firstName, String lastName, String email, String pwd, String confirmPwd){
    String emailAcceptedPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

    boolean fname = firstName.length() > 0;
    boolean lname = lastName.length() > 0;
    boolean emailB = email.length() > 0 && email.matches(emailAcceptedPattern);
    boolean passB = pwd.length() > REQUIRED_PWD_LEN;
    boolean conpassB = confirmPwd.length() > REQUIRED_PWD_LEN || pwd.equals(confirmPwd);

    return fname && lname && emailB && passB && conpassB;
  }

}