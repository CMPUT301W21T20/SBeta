package com.example.sbeta;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Test class for search experiment. All the UI tests are written here. Robotium test framework is used
 */
@RunWith(AndroidJUnit4.class)
public class TrialListTest{

    private Solo solo;

    @Rule
    public ActivityTestRule<MainMenuActivity> rule =
            new ActivityTestRule<>(MainMenuActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    /**
     * search test
     */
    public void showQuesTest(){
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", TrialActivity.class);

        Button op = (Button) solo.getView(R.id.add_trial_button);
        solo.clickOnView(op);
        //solo.clickOnButton("OPERATIONS");
        //Button operationButton = (Button) findViewById(R.id.operation_button);








    }

    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}