package com.sjsu.partyplanner.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.sjsu.partyplanner.Activities.Dashboard.InvitationListActivity;
import com.sjsu.partyplanner.Activities.Parties.CreatePartyActivity;
import com.sjsu.partyplanner.Activities.Parties.PartyActivity;
import com.sjsu.partyplanner.Models.Guest;
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
    private static final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static PartyController PartyController_instance = null;

    private PartyController()
    {

    }

    // static method to create instance of Singleton class
    public static PartyController getInstance()
    {
        if (PartyController_instance == null)
            PartyController_instance = new PartyController();

        return PartyController_instance;
    }
    public void createParty(CreatePartyActivity activity, Party p) {
        Log.d("databaseDebug", "createParty: saving database");
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
                        Log.d("databaseDebug", "createParty: saved successfuly with id: " + partyDocumentReference.getId());
                        ArrayList<Guest> guests = p.getGuests();
                        if (guests == null || guests.size() <= 0){
                            activity.handleSuccess();
                            return;
                        }
                        inviteGuestByEmail(activity, guests, partyDocumentReference.getId(), p.getName());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("#EC createEvent fail", "Error adding document", e);
                        Log.d("databaseDebug", "createParty: failed to save");
                        activity.handleFailure();
                    }
                });
    }

    public void getParties(PartyActivity activity) {
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<String> userParties = UserController.getCurrentUser().getParties();
        if(userParties != null && userParties.size() > 0) {
            db.collection(EVENT_DB_NAME).whereIn(FieldPath.documentId(), userParties)
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

    public void inviteGuestByEmail(CreatePartyActivity activity, ArrayList<Guest> guests, String partyId, String partyTitle) {

        ArrayList<Invitation> invitations = new ArrayList<>();

        for( Guest g : guests){
            invitations.add(new Invitation(partyId, partyTitle, g.getUid()));
        }

        Log.d("inviteGuestByEmail", ""+invitations.size());
        if (invitations.size()>0) {
            WriteBatch batch = db.batch();
            for(Invitation i : invitations){
                batch.set(db.collection(INVITE_DB_NAME).document(), i);
            }
            // Commit the batch
            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    activity.handleSuccess();
                }
            });
        } else {
            Log.d("inviteGuestByEmail", " No guest ");
            activity.handleSuccess();
        }

    }

    public void getUserInvitations(InvitationListActivity activity){
//        String uId = UserController.currentUser.getUid();
        //TODO:TESTING INVITATION NOW
        String uId ="LGd3AlG18uS9uG238XRml58CSFI3";
        db.collection(INVITE_DB_NAME).whereEqualTo("guestId", uId)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Invitation> invitations = new ArrayList<>();
                if(task.isComplete()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        invitations.add(document.toObject(Invitation.class));
                    }
                    activity.handleGetInvitationSuccess(invitations);
                }
            }
        });
    }

}
