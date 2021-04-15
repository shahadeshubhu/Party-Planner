package com.sjsu.partyplanner.Models;

import java.util.ArrayList;

public class Budget {

    private String name;
    private String party;
    private float amount;
    private ArrayList<Payment> payments = new ArrayList<Payment>();

    // Constructor
    public Budget(String name, String party, float amount) {
        this.name = name;
        this.party = party;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public void addPayments(Payment payment) {
        payments.add(payment);
    }


    /**
     * Gets the total payments in the budget
     * @return total payments
     */
    private float getPaymentTotal() {
        float total = 0;

        for (int i = 0; i < payments.size() - 1; i++) {
            total += payments.get(i).getAmount();
        }

        return total;
    }
}
