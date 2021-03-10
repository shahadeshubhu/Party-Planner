package com.sjsu.partyplanner;

import androidx.appcompat.app.AppCompatActivity;

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
    String fName = "";
    String lName = "";
    String email = "";
    String pwd = "";
    String confirmPwd = "";
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
   * Sign Up Screen
   * onClick method for 'login'
   * Goes to the login screen
   */
  public void loginPageClick(View view) {

  }

  /**
   * Sign Up Screen
   * onClick method for the signup button
   * Signs up the user and goes to the home page
   */
  public void signupClick(View view) {

  }


  /**
   * Login Screen
   * onClick method for the log in button
   * Logs the user in and goes to the home page
   */
  public void loginClick(View view) {

  }

  /**
   * Login Screen
   * onClick method for 'sign up'
   * Goes to the 'sign up' page
   */
  public void signupPageClick(View view) {

  }


  /**
   * Login Screen
   * onClick method for 'forgot password'
   * Goes to the 'reset password' page
   */
  public void forgotPWClick(View view) {

  }

  /**
   * Login Screen
   * onClick method for Google Icon
   */
  public void googleClick(View view) {

  }

  /**
   * Login Screen
   * onClick method for Twitter Icon
   */
  public void twitterClick(View view) {

  }

  /**
   * Login Screen
   * onClick method for Facebook Icon
   */
  public void facebookClick(View view) {

  }

}