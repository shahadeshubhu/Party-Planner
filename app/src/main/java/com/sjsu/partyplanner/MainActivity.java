package com.sjsu.partyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sjsu.partyplanner.Controllers.UserController;

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


  public void testingUserSign()
  {
    TextView textView = findViewById(R.id.textView);
    boolean result;
    UserController userController = new UserController();

  }
}