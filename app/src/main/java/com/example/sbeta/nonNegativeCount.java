package com.example.sbeta;
import android.location.Location;

import java.util.Date;

/**
 * This class represents a non-negative count trial
 */
public class nonNegativeCount extends Trial{
    /**
     * Constructor for nonNegativeCount
     * @param result (Double) trial result
     * @param participant (String) participant name
     * @param location (Location) trial location
     * @param trialName (String) trial name
     * @param trialNum (Integer) trial nunmber
     */
    public nonNegativeCount(double result, String participant, Location location, String trialName, int trialNum) {
        super(result, participant, location, trialName, trialNum);
    }
}
