package com.example.sbeta;

import android.location.Location;

import java.util.Date;

/**
 * This class represents a binomial trial
 */
public class binomialTrial extends Trial{
    /**
     * Constructor for binomialTrial
     * @param result double
     * @param participant (String) participant name
     * @param location (Location)
     * @param trialName (Strig) trialName
     * @param trialNum (Integer) trialNumber
     */
    public binomialTrial(double result, String participant, Location location, String trialName, int trialNum) {
        super(result, participant, location, trialName, trialNum);
    }
}
