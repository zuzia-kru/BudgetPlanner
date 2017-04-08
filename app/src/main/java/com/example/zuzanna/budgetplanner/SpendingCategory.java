/**
 * Created by Zuzanna on 02/04/2017.
 */
package com.example.zuzanna.budgetplanner;

public enum SpendingCategory {
    MONTHLY_FIXED ("Monthly fixed"),
    HOBBY ("Hobby"),
    FOOD ("Food"),
    TRANSPORT ("Transport"),
    OTHER ("Other");

    private final String radioName;

    public String getRadioName() {
        return radioName;
    }

    SpendingCategory(String radioName) {

        this.radioName = radioName;
    }
}
