package com.example.sbeta;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrialTest {
    private Trial mockTrial() {
        return new binomialTrial(1, "mockParticipant", null, "Trial 0", 0);
    }

    @Test
    public void testSetIgnore() {
        Trial trial = mockTrial();
        assertFalse(trial.getIsIgnored());
        trial.setIgnored(true);
        assertTrue(trial.getIsIgnored());
    }

    @Test
    public void testSetCreatedTime() {
        Trial trial = mockTrial();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        trial.setCreatedTime(time);
        assertEquals(time, trial.getCreatedTime());
    }

    @Test
    public void testGetTrialNum() {
        Trial trial = mockTrial();
        assertEquals(trial.getTrialNum(), 0);
    }

    @Test
    public void testGetTrialName() {
        Trial trial = mockTrial();
        assertEquals(trial.getTrialName(), "Trial 0");
    }

    @Test
    public void testGetResult() {
        Trial trial = mockTrial();
        assertEquals(trial.getResult(), 1, 0);
    }

    @Test
    public void testGetIsIgnored() {
        Trial trial = mockTrial();
        assertFalse(trial.getIsIgnored());
        trial.setIgnored(true);
        assertTrue(trial.getIsIgnored());
    }

    @Test
    public void testGetCreatedTime() {
        Trial trial = mockTrial();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        trial.setCreatedTime(time);
        assertEquals(time, trial.getCreatedTime());
    }
}
