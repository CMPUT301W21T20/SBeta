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
    String userId;

//    public Experiment(User owner, String description, String status, String name) {
//        this.owner = owner;
//        this.description = description;
//        this.status = status;
//        this.name = name;
//    }

    public Experiment(String description, Boolean isEnded, Boolean isPublished, Integer minTrials, Boolean locationRequired, String experimentType, String name, String userId) {
        this.description = description;
        this.isEnded = isEnded;
        this.isPublished = isPublished;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;
        this.experimentType = experimentType;
        this.name = name;
        this.userId = userId;
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
        if (isEnded == true) {
            return "Ended";
        } else {
            if (isPublished == false) {
                return "Unpublished";
            } else {
                return "Published";
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}
