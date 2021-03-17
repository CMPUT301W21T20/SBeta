package com.example.sbeta;

import android.location.Location;

public abstract class Trial {
    double result;
    String participant;
    Location location;

    public Trial(double result, String participant, Location location) {
        this.result = result;
        this.participant = participant;
        this.location = location;
    }
}
