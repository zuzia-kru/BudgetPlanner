package com.example.zuzanna.budgetplanner;

import java.sql.Timestamp;

/**
 * Created by Zuzanna on 02/04/2017.
 */

public class BudgetRecord {
    private Timestamp timestamp;
    private float amount;
    private String category;
    private String comment;

    public BudgetRecord(Timestamp timestamp, float amount, String category, String comment) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.category = category;
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
