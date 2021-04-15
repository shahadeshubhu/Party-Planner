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
import com.google.firebase.firestore.FieldPath;
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
    public final static String ASSOCIATE_DB_NAME = "AssociateUsers";
    public static User currentUserInfo;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserController() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Log.d("currentUser", ""+currentUser);
        if (currentUser != null) {
            getUserInfo();
        }
    }

    public boolean isSignedIn() {
        return currentUser != null;
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
                        User user = new User(firstName, lastName, email);
                        db.collection(ASSOCIATE_DB_NAME).document(currentUser.getUid())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    currentUserInfo = user;
                                    activity.handleSuccess();
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
                    getUserInfo();
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
        currentUserInfo = null;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    private boolean isEmailExist() {
        return false;
    }

    public void getUserInfo() {
        Log.d("#getUserInfo", "************************"+currentUser.getUid());

        db.collection(ASSOCIATE_DB_NAME).whereEqualTo(FieldPath.documentId(), currentUser.getUid())
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        currentUserInfo = document.toObject(User.class);
                        currentUserInfo.setUid(document.getId());
                        Log.d("#getUserInfo", document.getId() + " => " + currentUserInfo.toString());
                    }
                } else {
                    Log.d("#getParties error", "Error getting documents: ", task.getException());

                }
            }
        });
    }
}
