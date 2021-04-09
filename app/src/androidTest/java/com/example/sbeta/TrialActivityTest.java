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
import static org.junit.Assert.assertTrue;

public class TrialActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void testTrialActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Menu");

        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.add_experiment_button));
        //
        solo.enterText((EditText) solo.getView(R.id.description_input), "AAAACount");
        solo.enterText((EditText) solo.getView(R.id.region_text), "Edmonton");
        solo.clearEditText((EditText) solo.getView(R.id.minTrials)); //Clear the EditText
        solo.enterText((EditText) solo.getView(R.id.minTrials), "1");
        solo.clickOnView(solo.getView(R.id.type_spinner));
        solo.clickOnText("Count-based");
        solo.clickOnText("Ok");
        assertTrue(solo.waitForText("AAAACount", 1, 2000));

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", TrialActivity.class);

        Button add = (Button) solo.getView(R.id.add_trial_button);
        solo.clickOnView(add);
        solo.clickOnMenuItem("Manually Add");
        solo.enterText((EditText) solo.getView(R.id.count_data), "3");
        solo.clickOnText("CONFIRM");

        solo.clickOnView(add);
        solo.clickOnMenuItem("Manually Add");
        solo.enterText((EditText) solo.getView(R.id.count_data), "2");
        solo.clickOnText("CONFIRM");

        //check end
        Button operation = (Button) solo.getView(R.id.operation_button);
        solo.clickOnView(operation);
        solo.clickOnMenuItem("End Experiment");
        solo.assertCurrentActivity("Wrong Activity", TrialActivity.class);
        solo.clickOnView(add);
        solo.clickOnMenuItem("Manually Add");
        solo.assertCurrentActivity("Wrong Activity", TrialActivity.class);
        solo.clickOnView(add);
        solo.clickOnMenuItem("Scan QR");
        solo.assertCurrentActivity("Wrong Activity", TrialActivity.class);

        //check switch activity
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", TrialInfo.class);
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
