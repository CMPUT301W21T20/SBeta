package com.example.sbeta;

import android.location.Location;

import java.util.Date;

/**
 * This class represents a countBasedTrial
 */
public class countBasedTrial extends Trial {
    /**
     * Constructor for countBasedTrial
     * @param result (Double) result of trial
     * @param participant (String) participant name
     * @param location (Location) trial location
     * @param trialName (String) trial name
     * @param trialNum (Integer) trial number
     */
    public countBasedTrial(double result, String participant, Location location, String trialName, int trialNum) {
        super(result, participant, location, trialName, trialNum);
    }
}
