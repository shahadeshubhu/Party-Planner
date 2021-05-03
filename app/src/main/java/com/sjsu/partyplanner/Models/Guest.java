package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;

@com.google.firebase.firestore.IgnoreExtraProperties

public class Guest implements Parcelable {
    private String uid;
    private String firstName;
    private String lastName;
    private String email;

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getName() { return firstName + " " + lastName; }

    public Guest() {
    }

    protected Guest(Parcel in) {
        uid = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
    }

    public static final Creator<Guest> CREATOR = new Creator<Guest>() {
        @Override
        public Guest createFromParcel(Parcel in) {
            return new Guest(in);
        }

        @Override
        public Guest[] newArray(int size) {
            return new Guest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
    }

    @Override
    public String toString() {
        return
            String.format( "%s %s\n%s", firstName, lastName, email);
    }
}
