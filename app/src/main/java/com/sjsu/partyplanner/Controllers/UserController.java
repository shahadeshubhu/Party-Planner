package com.sjsu.partyplanner.Controllers;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.sjsu.partyplanner.Activities.Users.LoginActivity;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.Activities.Users.RegistrationActivity;
import com.sjsu.partyplanner.databinding.ActivityDashboardBinding;

public class UserController {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public UserController()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    public boolean isSignedIn(){
        if(currentUser != null) {
            Log.d("#UC currentUser", "" + currentUser.getEmail());
            return true;
        }
        return false;
    }

    // ** need to add a mechanism to see if it was successful or not.
    public void createAccount(RegistrationActivity activity, String email, String password)
    {
        Log.d("#UC createAccount", email + password);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                Log.d("#UC createAccount", currentUser.getUid());
                                //      TODO: get UID and save the first name, lastname,
                            }
                            activity.handleSuccess();
                        } else {

                            Exception e = task.getException();

                            if (e != null) {
                                activity.handleError(e.getMessage());
                            }
                            Log.d("#UC createAccount", "" + task.getException());
                        }
                    }
        });

    }



    public void signInUser(LoginActivity activity, String email, String password)
    {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    currentUser = mAuth.getCurrentUser();
                    activity.handleSuccess();

                }
                else
                {
                    Exception e = task.getException();

                    if (e != null) {
                        activity.handleError(e.getMessage());
                    }
                    Log.d("#UC signIn", "" + task.getException());
                }
            }
        });

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

    public User getUser(String email, String password){
      return null;
    }
}
