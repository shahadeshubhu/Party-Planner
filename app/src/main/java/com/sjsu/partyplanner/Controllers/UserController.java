package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sjsu.partyplanner.Activities.Users.LoginActivity;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.Models.User;
import com.sjsu.partyplanner.Activities.Users.RegistrationActivity;

public class UserController {

    private final FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    private final DatabaseReference mDatabase;
    public final static String ASSOCIATE_DB_NAME = "AssociateUsers";
    public static User currentUserInfo;
    public static String associateUserId;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserController() {

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public boolean isSignedIn() {
        if (currentUser != null) {
            getUserInfo();
            Log.d("#UC currentUser", "" + currentUser.getEmail() + currentUserInfo);
            return true;
        }
        return false;
    }

    // ** need to add a mechanism to see if it was successful or not.
    public void createAccount(RegistrationActivity activity, String email, String password, String firstName, String lastName) {
        Log.d("#UC createAccount", email + password);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        Log.d("#UC createAccount", currentUser.getUid());
                        User user = new User(currentUser.getUid(), firstName, lastName);
                        db.collection(ASSOCIATE_DB_NAME).add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        activity.handleSuccess();
                                        Log.d("#EC createEvent success", "DocumentSnapshot written with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("#EC createEvent fail", "Error adding document", e);
                                    }
                                });
                    }
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


    public void signInUser(LoginActivity activity, String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    currentUser = mAuth.getCurrentUser();
                    activity.handleSuccess();

                } else {
                    Exception e = task.getException();

                    if (e != null) {
                        activity.handleError(e.getMessage());
                    }
                    Log.d("#UC signIn", "" + task.getException());
                }
            }
        });

    }


    public void signOutUser() {
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    private boolean isEmailExist() {
        return false;
    }

    public void getUserInfo() {
        db.collection(ASSOCIATE_DB_NAME)
                .whereEqualTo("uid", currentUser.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        currentUserInfo = document.toObject(User.class);
                        associateUserId = document.getId();
                        Log.d("#getParties", document.getId() + " => " + currentUserInfo.toString());
                    }
                } else {
                    Log.d("#getParties error", "Error getting documents: ", task.getException());

                }
            }
        });
    }
}
