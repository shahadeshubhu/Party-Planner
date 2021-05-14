package com.sjsu.partyplanner.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@IgnoreExtraProperties
public class AssociateUser {

    private ArrayList<Party> parties;

    public AssociateUser(){};

        public AssociateUser(ArrayList<Party> parties){

            this.parties = new ArrayList<>();

        }



        public void addEvent(Party e){
            parties.add(e);
        }
        public ArrayList<Party> getParties(){
            return parties;
        }

        @NotNull
        @Override
        public String toString() {
            return "AssociateUser{" +
                    "parties='" + parties.size() +
                    '}';
    }

}
