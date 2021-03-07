package com.sjsu.partyplanner.Controllers;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import com.sjsu.partyplanner.Models.User;

public class UserController {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    public UserController()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }


    // ** need to add a mechanism to see if it was successful or not.
    public boolean createAccount(Activity activity, String email, String password)
    {
        final boolean[] success = new boolean[1];
        success[0] = true;
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            currentUser = mAuth.getCurrentUser();
                        }
                        else
                        {
                            success[0] = false;
                        }
                    }
                    });

        return success[0];
    }


    public boolean signInUser(Activity activity, String email, String password)
    {
        final boolean[] success = new boolean[1];
        success[0] = true;

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    currentUser = mAuth.getCurrentUser();
                }
                else
                {
                    success[0]= false;
                }
            }
        });

        return success[0];
    }

    public void signOutUser()
    {
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser()
    {
        return currentUser;
    }



    private boolean isEmailExist(){
      return false;
    }

    public User registerNewUser(){
      
      //check if email exist
      return null;
    }

    public User getUser(String email, String password){
      return null;
    }
}
