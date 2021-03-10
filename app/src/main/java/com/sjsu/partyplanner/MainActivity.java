package com.sjsu.partyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }


  public void registerUser() {
    String fName = ((EditText) findViewById(R.id.fNameTB)).getText().toString();
    String lName = ((EditText) findViewById(R.id.lNameTB)).getText().toString();
    String email = ((EditText) findViewById(R.id.emailTB)).getText().toString();
    String pwd = ((EditText) findViewById(R.id.passTB)).getText().toString();
    String confirmPwd = ((EditText) findViewById(R.id.confirmPassTB)).getText().toString();

    if (!validateInput(fName, lName, email, pwd, confirmPwd)) {
      Toast.makeText(this,"Wrong Input", Toast.LENGTH_LONG);
    }else{

    }
  }

  private boolean validateInput(String firstName, String lastName, String email, String pwd, String confirmPwd){
    String emailAcceptedPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean isValid = true;
    if(firstName.length() <= 0){
      //TODO: trigger error for first name field
      isValid = false;
    }
    if(lastName.length() <= 0){
      //TODO: trigger error for last name field
      isValid = false;
    }
    if(email.length() <= 8  || !email.matches(emailAcceptedPattern)){
      //TODO: trigger error for email invalid field
      isValid = false;
    }
    if(pwd.length() <= 0){
      //TODO: trigger error for confirmPwd field
      isValid = false;
    }
    if(confirmPwd.length() <= 0){
      //TODO: trigger error for first name field
      isValid = false;

    }
    if(!pwd.equals(confirmPwd)){
      //TODO: trigger pwd does not match
      isValid = false;
    }
    return isValid;
  }


  // ERROR METHOD?
  public void testingUserSign()
  {
    //TextView textView = findViewById(R.id.textView);
    boolean result;
    //UserController userController = new UserController();

  }


  // BUTTON CLICKS ---------------------------------------

  /**
   * onClick method for 'login'
   * Goes to the login screen
   */
  public void loginPageClick(View view) {
    toastMsg("Go to login page");
    startActivity(new Intent(MainActivity.this, LoginActivity.class));
  }

  /**
   * onClick method for the signup button
   * Signs up the user and goes to the home page
   */
  public void signupClick(View view) {
    toastMsg("Sign Up Button");
    registerUser();
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