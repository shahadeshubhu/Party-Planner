package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.sjsu.partyplanner.Activities.Parties.CreatePartyActivity;
import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.Models.Invitation;
import com.sjsu.partyplanner.Models.Party;
import com.sjsu.partyplanner.Models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartyController {
    public static final String EVENT_DB_NAME = "Parties";
    public static final String INVITE_DB_NAME= "Invitations";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void createParty(CreatePartyActivity activity, Party p) {
        String ownerId = UserController.currentUser.getUid();
        p.setOwnerID(ownerId);
        db.collection(EVENT_DB_NAME).add(p)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference partyDocumentReference) {
                        db.collection(UserController.ASSOCIATE_DB_NAME)
                                .document(ownerId)
                                .update("parties", FieldValue.arrayUnion(partyDocumentReference.getId()));

                        Log.d("#EC createEvent success", "DocumentSnapshot written with ID: " + partyDocumentReference.getId());
                        activity.handleSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("#EC createEvent fail", "Error adding document", e);
                        activity.handleFailure();
                    }
                });
    }

    public void getParties(PartyActivity activity) {
        String ownerId = UserController.currentUser.getUid();
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<String> userParties = UserController.currentUserInfo.getParties();
//        ArrayList<String> userParties = UserController.currentUserInfo.getParties();
        if(userParties != null && userParties.size() > 0) {
            db.collection(EVENT_DB_NAME).whereIn(FieldPath.documentId(), UserController.currentUserInfo.getParties())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Party p = document.toObject(Party.class);
                            Log.d("#document data", "" + document.getId());

                            parties.add(p);
                        }
                        activity.handleFetchParties(true, parties);
                        Log.d("#getParties", "" + parties.size());

                    } else {
                        Log.d("#getParties error", "Error getting documents: ", task.getException());
                        activity.handleFetchParties(false, parties);

                    }
                }
            });
        }
    }


    public void inviteGuestByEmail(PartyActivity activity, ArrayList<String> emails, String partyId, String partyTitle) {
        if (emails == null || emails.size() <= 0){
           return;
        }
        Set emailSet = new HashSet(emails);
        db.collection(UserController.ASSOCIATE_DB_NAME).whereIn("email", emails)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<User> appRegisteredGuests = new ArrayList<>();
                    ArrayList<Invitation> invitations = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User u = document.toObject(User.class);
                        u.setUid(document.getId());
                        appRegisteredGuests.add(u);
                        emailSet.remove(u.getEmail());
                        invitations.add(new Invitation(partyId, partyTitle, u.getUid()));

                    }

                    if (invitations.size()>0) {
                        WriteBatch batch = db.batch();
                        for(Invitation i : invitations){
                            batch.set(db.collection(INVITE_DB_NAME).document(), i);
                        }
                        // Commit the batch
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });
                    }

                    ArrayList<String> unregisteredGuests = new ArrayList<>(emailSet);
                    //TODO: email the invitation to all unregisterGuests email
//                    activity.handleFetchParties(true, appRegisteredGuests);
                    Log.d("#invittByEmail", "" + appRegisteredGuests.size());

                } else {
                    Log.d("#invite error", "Error getting documents: ", task.getException());
//                    activity.handleFetchParties(false, appRegisteredGuests);

                }
            }
        });
    }

    public void getUserInvitations(){
        db.collection(INVITE_DB_NAME).whereEqualTo("guestId", UserController.currentUser.getUid())
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Invitation> invitations = new ArrayList<>();
                if(task.isComplete()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        invitations.add(document.toObject(Invitation.class));
                        //Todo: pass this back to invitation
                    }
                }
            }
        });
    }

}
