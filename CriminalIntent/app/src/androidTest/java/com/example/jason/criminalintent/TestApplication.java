package com.example.jason.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.R.attr.orientation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(AndroidJUnit4.class)
public class TestApplication {
    @Rule
    public ActivityTestRule<CrimeListActivity> mActivityRule = new ActivityTestRule<>(CrimeListActivity.class);



    @Before
    public void initValidString() {
        // Specify a valid string.
    }


    @Test
    public void testChooseCrime() {
        Activity activity = mActivityRule.getActivity();

        //View view = mActivityRule.getActivity().findViewById(R.id.crime_linear_layout);
        //final Activity activity = (Activity) view.getContext();

        // Test ability to choose crime fragment from the recycler list
        // Click on the RecyclerView item at position crimeToSelect
        for (int crimeID = 1; crimeID < 2; crimeID++) {

            onView(withId(R.id.crime_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(crimeID, click()));
            //Log.i("CrimeID",crimeID);
            Log.i("CrimeID",String.valueOf(crimeID) );

            //Select the i'th crime, and check that the crime_title matches the 'Crime #'+crimeId
            onView( allOf(withId(R.id.crime_title) , isDisplayed() ) ).check(matches(withText("Crime #" + crimeID)));

            //Select the crime_date button to trigger the datepicker
            onView( allOf(withId(R.id.crime_date) , isDisplayed() )).perform(click());

            //Choose a date for datepicker, and set it equal to 1/1/1999
            onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1999, 1, 1));
            onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(9,0));

            //Press okay and go back to crime view
            onView(withId(android.R.id.button1)).perform(click());
            //Go back to the list view
            Espresso.pressBack();

            //Rotate the screen
            activity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );

            //Select the CrimeID again
            onView(withId(R.id.crime_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(crimeID, click()));

            //Ensure screen is rotated
            activity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );

            //Select the i'th crime, and check that the crime_title matches the 'Crime #'+crimeId
            onView( allOf(withId(R.id.crime_title) , isDisplayed() ) ).check(matches(withText("Crime #" + crimeID)));

            //Check to make sure date still matches in landscape view
            onView( allOf(withId(R.id.crime_date) , isDisplayed() )).check(matches(withText(  "Fri Jan 01 09:00:00 EST 1999" )));

        }
    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation= context.getResources().getConfiguration().orientation;

        Activity activity = mActivityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    /*
    @Test
    public void testSetSolved() {
        // Test the solved checkbox is communicated to list and retained in item view
        // set all to false        for (Crime crime : CrimeLab.get(InstrumentationRegistry.getContext()).getCrimes()) {
            crime.setSolved(false);
        }

        // Test ability to set crime to solved and xfer back to CrimeListFragment
        // set some to true
        for (int crimeID = 1; crimeID < 3; crimeID++) {

            onView(withId(R.id.crime_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(crimeID, click()));
            onView(withId(R.id.crime_solved)).check(matches(isNotChecked()));
            onView(withId(R.id.crime_solved)).perform(click());
            onView(withId(R.id.crime_solved)).check(matches(isChecked()));
            // return to List, then check that solved is still selected in crimeFrag view
            Espresso.pressBack();

            onView(withId(R.id.crime_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(crimeID, click()));
            onView(withId(R.id.crime_solved)).check(matches(isChecked()));
            Espresso.pressBack();
        }
    }*/
}