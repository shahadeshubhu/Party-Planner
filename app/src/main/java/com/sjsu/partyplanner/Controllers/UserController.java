package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.partyplanner.Activities.Users.LoginActivity;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.Activities.Users.RegistrationActivity;

public class UserController {

    private final FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    private final DatabaseReference mDatabase;

    public UserController()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isSignedIn(){
        if(currentUser != null) {
            Log.d("#UC currentUser", "" + currentUser.getEmail());
            currentUser.getDisplayName();
            return true;
        }
        return false;
    }

    // ** need to add a mechanism to see if it was successful or not.
    public void createAccount(RegistrationActivity activity, String email, String password, String firstName, String lastName)
    {
        Log.d("#UC createAccount", email + password);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                Log.d("#UC createAccount", currentUser.getUid());
                                String userCollectionName = "users";
                                User user = new User(firstName, lastName);
                                mDatabase.child(userCollectionName).child(currentUser.getUid()).setValue(user);
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

    public void getUserInfo(){
        mDatabase.child("users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue(User.class)));
                }
            }
        });
    }
}
