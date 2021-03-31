package com.example.sbeta;


import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TrialActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<TrialActivity> rule =
            new ActivityTestRule<>(TrialActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        //Activity activity = rule.getActivity();
    }

    @Test
    public  void addTrial() {
        /*
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickInList(0);

        Button op = (Button) solo.getView(R.id.add_trial_button);
        solo.clickOnView(op);
        solo.clickOnMenuItem("Manually Add");

         */



    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }




}
