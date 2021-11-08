package anthony.brenon.mareu.test;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


import static anthony.brenon.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.service.MeetingApiService;
import anthony.brenon.mareu.ui_user.ListMareuActivity;
import anthony.brenon.mareu.utils.DeleteViewAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestMareu {

    // This is fixed
    private static int ITEMS_COUNT = 1;

    private ListMareuActivity activity;
    private MeetingApiService service;
    int inputId;
    String text;

    public void insertTextIntoInput(Integer inputId, String text) {
        onView(withId(inputId)).perform(typeText(text));
    }

    @Rule
    public ActivityTestRule<ListMareuActivity> activityRule =
            new ActivityTestRule(ListMareuActivity.class);

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
        assertThat(activity, notNullValue());

        service = DI.getNewInstanceApiService();

    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.activity_list_ma_reu))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position
        onView(ViewMatchers.withId(R.id.list_meetings_recycler_view)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meetings_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_meetings_recycler_view)).check(withItemCount(ITEMS_COUNT -1));
    }

    /**
     *Meeting test created
     */
    @Test
    public void meetingTest_createdWithSuccess() {
        //test click on add fab and create activity is displayed
        onView(withId(R.id.list_meeting_fab_add)).perform(click());
        onView(withId(R.id.activity_add_meeting_layout)).check(matches(isDisplayed()));
        //test populate create meeting and create right back to list activity

        onView(withId(R.id.ti_ed_topic)).perform(typeText("Test topic"));
        //this.insertTextIntoInput(R.id.ti_ed_topic, "topic test");
        onView(withId(R.id.activity_add_meeting_layout)).perform(click());

        onView(withId(R.id.button_create_meeting)).perform(click());
        //chek return meeting list
        onView(withId(R.id.activity_list_ma_reu)).check(matches(isDisplayed()));
        //chek populate is right
//
//        /**onView(ViewMatchers.withId(R.id.)).;*/
        //chek meeting is displayed
        onView(withId(R.id.activity_list_ma_reu)).check((matches(hasChildCount(2))));
    }
}