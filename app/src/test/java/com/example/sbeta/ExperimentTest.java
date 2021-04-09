package com.example.sbeta;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit Test for class Experiment
 */

public class ExperimentTest {

    private Experiment mockExperiment() {
        return new Experiment("mockDescription", false, true, 10, false, "Binomial", "mockName", "mockUserName");
    }

    @Test
    public void testSetTrials() {
        Experiment experiment = mockExperiment();
        ArrayList<Trial> trials = new ArrayList<>();
        binomialTrial trial1 = new binomialTrial(1, "userName", null, "name", 12);
        binomialTrial trial2 = new binomialTrial(0, "userName", null, "name", 12);
        trials.add(trial1);
        trials.add(trial2);
        experiment.setTrials(trials);
        assertEquals(trials, experiment.trials);
    }

    @Test
    public void testSetDescription() {
        Experiment experiment = mockExperiment();
        experiment.setDescription("qqqqqqq");
        assertEquals("qqqqqqq", experiment.description);
    }

    @Test
    public void testSetEnded() {
        Experiment experiment = mockExperiment();
        experiment.setEnded(true);
        assertEquals(true, experiment.isEnded);
        experiment.setEnded(false);
        assertEquals(false, experiment.isEnded);
    }

    @Test
    public void testSetPublished() {
        Experiment experiment = mockExperiment();
        experiment.setPublished(true);
        assertEquals(true, experiment.isPublished);
        experiment.setPublished(false);
        assertEquals(false, experiment.isPublished);
    }

    @Test
    public void testSetMinTrials() {
        Experiment experiment = mockExperiment();
        experiment.setMinTrials(10);
        assertEquals(10, experiment.minTrials);
        experiment.setMinTrials(20);
        assertEquals(20, experiment.minTrials);
    }

    @Test
    public void testSetLocationRequired() {
        Experiment experiment = mockExperiment();
        experiment.setLocationRequired(true);
        assertEquals(true, experiment.locationRequired);
        experiment.setLocationRequired(false);
        assertEquals(false, experiment.locationRequired);
    }

    @Test
    public void testSetExperimentType() {
        Experiment experiment = mockExperiment();
        experiment.setExperimentType("Count");
        assertEquals("Count", experiment.experimentType);
        experiment.setExperimentType("Binomial");
        assertEquals("Binomial", experiment.experimentType);
    }

    @Test
    public void testSetStatus() {
        Experiment experiment = mockExperiment();
        experiment.setStatus("Ended");
        assertEquals("Ended", experiment.status);
        experiment.setStatus("Unpublished");
        assertEquals("Unpublished", experiment.status);
    }

    @Test
    public void testGetTrials() {
        Experiment experiment = mockExperiment();
        ArrayList<Trial> trials = new ArrayList<>();
        binomialTrial trial1 = new binomialTrial(1, "userName", null, "name", 12);
        binomialTrial trial2 = new binomialTrial(0, "userName", null, "name", 12);
        trials.add(trial1);
        trials.add(trial2);
        experiment.setTrials(trials);
        assertEquals(trials, experiment.trials);
        ArrayList<Trial> get = experiment.getTrials();
        assertEquals(trials, get);
    }

    @Test
    public void testGetDescription() {
        Experiment experiment = mockExperiment();
        assertEquals("mockDescription", experiment.getDescription());
    }

    @Test
    public void testGetEnded() {
        Experiment experiment = mockExperiment();
        experiment.setEnded(true);
        assertEquals(true, experiment.getEnded());
        experiment.setEnded(false);
        assertEquals(false, experiment.getEnded());
    }

    @Test
    public void testGetPublished() {
        Experiment experiment = mockExperiment();
        experiment.setPublished(true);
        assertEquals(true, experiment.getPublished());
        experiment.setPublished(false);
        assertEquals(false, experiment.getPublished());
    }

    @Test
    public void testGetMinTrials() {
        Experiment experiment = mockExperiment();
        experiment.setMinTrials(10);
        assertEquals(10, experiment.getMinTrials());
        experiment.setMinTrials(20);
        assertEquals(20, experiment.getMinTrials());
    }

    @Test
    public void testGetLocationRequired() {
        Experiment experiment = mockExperiment();
        experiment.setLocationRequired(true);
        assertEquals(true, experiment.getLocationRequired());
        experiment.setLocationRequired(false);
        assertEquals(false, experiment.getLocationRequired());
    }

    @Test
    public void testGetExperimentType() {
        Experiment experiment = mockExperiment();
        experiment.setExperimentType("Count");
        assertEquals("Count", experiment.getExperimentType());
        experiment.setExperimentType("Binomial");
        assertEquals("Binomial", experiment.getExperimentType());
    }

    @Test
    public void testGetStatus() {
        Experiment experiment = mockExperiment();
        experiment.isEnded = true;
        experiment.isPublished = true;
        assertEquals("Ended", experiment.getStatus());
        experiment.isEnded = false;
        experiment.isPublished = true;
        assertEquals("Published", experiment.getStatus());
        experiment.isEnded = false;
        experiment.isPublished = false;
        assertEquals("Unpublished", experiment.getStatus());
        experiment.isEnded = true;
        experiment.isPublished = false;
        assertEquals("Unpublished", experiment.getStatus());
    }

    @Test
    public void testGetName() {
        Experiment experiment = mockExperiment();
        assertEquals("mockName", experiment.getName());
        experiment.name = "asdf";
        assertEquals("asdf", experiment.getName());
    }

    @Test
    public void testGetUserID() {
        Experiment experiment = mockExperiment();
        assertEquals("mockUserName", experiment.getUserId());
        experiment.userId = "change";
        assertEquals("change", experiment.getUserId());
    }


}









