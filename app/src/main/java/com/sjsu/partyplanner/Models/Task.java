package com.sjsu.partyplanner.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

@com.google.firebase.firestore.IgnoreExtraProperties
public class Task implements Parcelable {

    private String taskID;
    private String name;
    private String taskCategory;
    private String note;
    private ArrayList<Subtask> subtasks;
    private int taskStatus;
    private int completedSubtasks;

    public Task(){};

    // Constructor
    public Task(String name, String taskCategory, String note, ArrayList<Subtask> subtasks) {
        this.name = name;
        this.note = note;
        this.taskCategory = taskCategory;
        this.subtasks = subtasks;
        this.taskStatus = getTaskStatus();
    }


    public String getNote() { return note; };

    @com.google.firebase.firestore.Exclude
    public String getTaskID() { return taskID; };

    /**
     * Gets the task name (task adapter)
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the task category (task adapter)
     * @return taskCategory
     */
    public String getTaskCategory() {
        return taskCategory;
    }

    /**
     * Sets the new subtasks
     * @param subtasks
     */
    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
        setTaskStatus();
    }


    /**
     * Gets the arraylist of subtasks for this task
     * @return subtasks
     */
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
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

    /**
     * For TaskAdapter, gets subtask completion
     * @return s
     */
    public String getCompleted() {
        String s = "";
        setTaskStatus();

        if (taskStatus == STATUS.NOT_STARTED) { s = "Subtasks: 0/"; }
        else if (taskStatus != STATUS.NOT_STARTED){ s = "Subtasks: " + String.valueOf(completedSubtasks) + "/"; }
        s = s + String.valueOf(getTotalSubtasks());

        return s;
    }

    /**
     * For TaskAdapter, gets task state (string)
     * @return task status
     */
    public String getStatus() {
        if (taskStatus == STATUS.COMPLETE) { return "Complete"; }
        else if (taskStatus == STATUS.NOT_STARTED) { return "Not Started"; }
        else { return "Pending"; }
    }


    //-------------Private Methods----------------


    /**
     * Calculates the number of completed subtasks;
     */
    private void setCompletedSubtasks() {
        int count = 0;

        for (int i = 0; i < getTotalSubtasks(); i++) {
            // Completed subtask returns true
            if (subtasks.get(i).getSubtaskStatusBool()) { count++; }
        }
        completedSubtasks = count;
    }

    /**
     * Gets the task status (based on subtasks)
     * #/# completed subtask = completed task
     * otherwise, task is pending
     * @return STATUS
     */
    private int getTaskStatus() {
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




/*
    // Pareceable

    // Methods needed to parcel this class
    @Override
    public int describeContents() {
        return 0;
    }







    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.taskID);
        dest.writeString(this.name);
        dest.writeString(this.taskCategory);
        dest.writeString(this.note);
        dest.writeList(this.subtasks);
        dest.writeInt(this.taskStatus == null ? -1 : this.taskStatus.ordinal());
        dest.writeInt(this.completedSubtasks);
    }

    public void readFromParcel(Parcel source) {
        this.taskID = source.readString();
        this.name = source.readString();
        this.taskCategory = source.readString();
        this.note = source.readString();
        this.subtasks = new ArrayList<Subtask>();
        source.readList(this.subtasks, Subtask.class.getClassLoader());
        int tmpTaskStatus = source.readInt();
        this.taskStatus = tmpTaskStatus == -1 ? null : STATUS.values()[tmpTaskStatus];
        this.completedSubtasks = source.readInt();
    }

    protected Task(Parcel in) {
        this.taskID = in.readString();
        this.name = in.readString();
        this.taskCategory = in.readString();
        this.note = in.readString();
        this.subtasks = new ArrayList<Subtask>();
        in.readList(this.subtasks, Subtask.class.getClassLoader());
        int tmpTaskStatus = in.readInt();
        this.taskStatus = tmpTaskStatus == -1 ? null : STATUS.values()[tmpTaskStatus];
        this.completedSubtasks = in.readInt();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

*/
    @Override
    public String toString() {
        return "Task{" +
                "taskID='" + taskID + '\'' +
                ", name='" + name + '\'' +
                ", taskCategory='" + taskCategory + '\'' +
                ", note='" + note + '\'' +
                ", subtasks=" + subtasks +
                ", taskStatus=" + taskStatus +
                ", completedSubtasks=" + completedSubtasks +
                '}';
    }


    // Parceable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.taskID);
        dest.writeString(this.name);
        dest.writeString(this.taskCategory);
        dest.writeString(this.note);
        dest.writeList(this.subtasks);
        dest.writeInt(this.taskStatus);
        dest.writeInt(this.completedSubtasks);
    }

    public void readFromParcel(Parcel source) {
        this.taskID = source.readString();
        this.name = source.readString();
        this.taskCategory = source.readString();
        this.note = source.readString();
        this.subtasks = new ArrayList<Subtask>();
        source.readList(this.subtasks, Subtask.class.getClassLoader());
        this.taskStatus = source.readInt();
        this.completedSubtasks = source.readInt();
    }

    protected Task(Parcel in) {
        this.taskID = in.readString();
        this.name = in.readString();
        this.taskCategory = in.readString();
        this.note = in.readString();
        this.subtasks = new ArrayList<Subtask>();
        in.readList(this.subtasks, Subtask.class.getClassLoader());
        this.taskStatus = in.readInt();
        this.completedSubtasks = in.readInt();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
