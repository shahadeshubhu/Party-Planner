package com.sjsu.partyplanner.Models;


public class Payment {

    private String name;
    private float amount;
    private int status;

    public Payment(String name, float amount) {
        this.name = name;
        this.amount = amount;
        this.status = STATUS.UNPAID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void changeStatus() {
        if (status == STATUS.PAID) { status = STATUS.UNPAID; }
        else { status = STATUS.PAID; }
    }
}
