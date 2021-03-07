package com.example.sbeta;

import java.util.ArrayList;

public class Experiment {
    ArrayList<Trial> trials;
    User owner;
    ArrayList<User> participants;
    String description;
    Boolean isEnded;
    Boolean isPublished;
    Integer minTrials;
    Boolean locationRequired;
    String experimentType;
    String status;
    String name;

    public Experiment(User owner, String description, String status, String name) {
        this.owner = owner;
        this.description = description;
        this.status = status;
        this.name = name;
    }

    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnded(Boolean ended) {
        isEnded = ended;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public void setMinTrials(Integer minTrials) {
        this.minTrials = minTrials;
    }

    public void setLocationRequired(Boolean locationRequired) {
        this.locationRequired = locationRequired;
    }

    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Trial> getTrials() {
        return trials;
    }

    public User getOwner() {
        return owner;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnded() {
        return isEnded;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public Integer getMinTrials() {
        return minTrials;
    }

    public Boolean getLocationRequired() {
        return locationRequired;
    }

    public String getExperimentType() {
        return experimentType;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
