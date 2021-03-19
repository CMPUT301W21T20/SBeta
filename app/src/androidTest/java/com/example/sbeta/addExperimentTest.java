package com.example.sbeta;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;
import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class addExperimentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Gets the Activity
     *
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * test the add experiment fragment
     */

    @Test
    public void addExprTest(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Menu");

        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.add_experiment_button));
        //
        solo.enterText((EditText) solo.getView(R.id.description_input), "addExprTest");
        solo.enterText((EditText) solo.getView(R.id.region_text), "Edmonton");
        solo.clearEditText((EditText) solo.getView(R.id.minTrials)); //Clear the EditText
        solo.enterText((EditText) solo.getView(R.id.minTrials), "1");
        solo.clickOnView(solo.getView(R.id.type_spinner));
        solo.clickOnText("Binomial trials");
        solo.clickOnView(solo.getView(R.id.Location_required_switch));
        solo.clickOnText("Ok");

        assertTrue(solo.waitForText("addExprTest", 1, 2000));
        solo.clickLongOnText("addExprTest"); //delete the test
    }
}
