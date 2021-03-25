package com.sjsu.partyplanner.Models;

public class Subtask {

    private String subtaskID;
    private String name;
    private STATUS subtaskStatus;
    private String taskID;

    private enum STATUS {
        PENDING,
        COMPLETE
    }

    // Constructor
    public Subtask(String subtaskID,  String name, String taskID) {
        this.subtaskID = subtaskID;
        this.name = name;
        this.subtaskStatus = STATUS.PENDING;
    }

    /**
     * Gets subtask name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets subtask ID
     * @return subtaskID
     */
    public String getSubtaskID() {
        return subtaskID;
    }

    /**
     * Gets task that owns the subtask
     * @return taskID
     */
    public String getTaskID() {
        return taskID;
    }

    /**
     * Gets subtask status (pending - false; complete - true)
     * @return subtaskStatus
     */
    public boolean getSubtaskStatus() {
        if (subtaskStatus == STATUS.COMPLETE) {
            return true;
        }
        return false;
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
        else {
            subtaskStatus = STATUS.COMPLETE;
        }
    }



}
