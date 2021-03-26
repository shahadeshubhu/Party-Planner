package com.sjsu.partyplanner.Models;

import java.util.ArrayList;

public class Task {

    private String taskID;
    private String name;
    private String taskCategory;
    private String note;
    private ArrayList<Subtask> subtasks;
    private String partyID;
    private STATUS taskStatus;
    private int completedSubtasks;

    public enum STATUS {
        NOT_STARTED,
        PENDING,
        COMPLETE
    }

    // Constructor
    public Task(String taskID, String name, String taskCategory, String note, ArrayList<Subtask> subtasks, String partyID) {
        this.taskID = taskID;
        this.name = name;
        this.note = note;
        this.taskCategory = taskCategory;
        this.subtasks = subtasks;
        this.partyID = partyID;
        this.taskStatus = getTaskStatus();
    }

    public String getNote() { return note; };

    public String getTaskID() { return taskID; };

    /**
     * Gets the task name (task adapter)
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * For TaskAdapter, gets subtask completion
     * @return s
     */
    public String getCompleted() {

        String s = "";
        setTaskStatus();

        if (taskStatus == STATUS.NOT_STARTED) {
            s = "Subtasks: 0/";
        }
        else if (taskStatus != STATUS.NOT_STARTED){
            s = "Subtasks: " + String.valueOf(completedSubtasks) + "/";
        }

        s = s + String.valueOf(getTotalSubtasks());

        return s;
    }

    /**
     * For TaskAdapter, gets task state (string)
     * @return task status
     */
    public String getStatus() {
        if (taskStatus == STATUS.COMPLETE) {
            return "Complete";
        }
        else if (taskStatus == STATUS.NOT_STARTED) {
            return "Not Started";
        }
        else {
            return "Pending";
        }
    }


    /**
     * Gets the task category (task adapter)
     * @return taskCategory
     */
    public String getTaskCategory() {
        return taskCategory;
    }


    /**
     * Gets the arraylist of subtasks for this task
     * @return subtasks
     */
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    /**
     * Get partyID who owns the task
     * @return partyID
     */
    public String getPartyID() {
        return partyID;
    }

    /**
     * Gets the number of completed subtasks
     * @return completedSubtasks
     */
    public int getCompletedSubtasks() {
        return completedSubtasks;
    }

    /**
     * Gets the amount of subtasks
     * @return subtasks.size()
     */
    public int getTotalSubtasks() {
        return subtasks.size();
    }



    //-------------Private Methods----------------



    /**
     * Calculates the number of completed subtasks;
     */
    private void setCompletedSubtasks() {
        int count = 0;

        for (int i = 0; i < getTotalSubtasks()-1; i++) {
            // Completed subtask returns true
            if (subtasks.get(i).getSubtaskStatus()) { count++; }
        }
        completedSubtasks = count;
    }

    /**
     * Gets the task status (based on subtasks)
     * #/# completed subtask = completed task
     * otherwise, task is pending
     * @return STATUS
     */
    private STATUS getTaskStatus() {
        setCompletedSubtasks();
        if (completedSubtasks == getTotalSubtasks()) {
            return STATUS.COMPLETE;
        }
        else if (completedSubtasks == 0) {
            return STATUS.NOT_STARTED;
        }
        return STATUS.PENDING;
    }

    /**
     * Sets the status of the task
     */
    private void setTaskStatus() {
        taskStatus = getTaskStatus();
    }

}