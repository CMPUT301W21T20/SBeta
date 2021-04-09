package com.example.sbeta;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchUserTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);
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
    @Test
    public void searchExperTest(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Menu");
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.user_profile)); //Click user profile
        solo.assertCurrentActivity("Wrong Activity", UserProfileActivity.class);

        solo.clickOnButton("search");
        solo.enterText((EditText) solo.getView(R.id.searchkey), "user");
        solo.clickOnText("OK");
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
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
