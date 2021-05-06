package com.sjsu.partyplanner.Models;

import java.io.Serializable;

public class Subtask implements  Serializable {

    private String name;
    private int subtaskStatus;

    // Constructor
    public Subtask(String name) {
        this.name = name;
        this.subtaskStatus = STATUS.PENDING;
    }
    // no argument constructor. Firestore requires it
    public Subtask() { }

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
    public boolean getSubtaskStatusBool() {
        return subtaskStatus == STATUS.COMPLETE;
    }

    public int getSubtaskStatus() {
        return subtaskStatus;
    }

    /**
     * Set subtask name
     * @param name new subtask name
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setSubtaskStatus(int subtaskStatus){
        this.subtaskStatus = subtaskStatus;
    }

    /**
     * Changes subtask status to the opposite status
     */
    public void changeStatus() {
        if (subtaskStatus == STATUS.COMPLETE) { subtaskStatus = STATUS.PENDING; }
        else { subtaskStatus = STATUS.COMPLETE; }
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", subtaskStatus=" + subtaskStatus +
                '}';
    }

}
