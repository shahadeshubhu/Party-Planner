package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Subtask implements Parcelable {

    private String name;
    private STATUS subtaskStatus;

    // Constructor
    public Subtask(String name) {
        this.name = name;
        this.subtaskStatus = STATUS.PENDING;
    }

    protected Subtask(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Subtask> CREATOR = new Creator<Subtask>() {
        @Override
        public Subtask createFromParcel(Parcel in) {
            return new Subtask(in);
        }

        @Override
        public Subtask[] newArray(int size) {
            return new Subtask[size];
        }
    };

    //TODO: Implement this
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
    /**
     * Gets subtask name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets subtask status (pending - false; complete - true)
     * @return subtaskStatus
     */
    public boolean getSubtaskStatus() {
        return subtaskStatus == STATUS.COMPLETE;
    }

    /**
     * Set subtask name
     * @param name new subtask name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes subtask status to the opposite status
     */
    public void changeStatus() {
        if (subtaskStatus == STATUS.COMPLETE) { subtaskStatus = STATUS.PENDING; }
        else { subtaskStatus = STATUS.COMPLETE; }
    }



}
