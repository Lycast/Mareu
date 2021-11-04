package anthony.brenon.mareu.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static anthony.brenon.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import androidx.test.espresso.Espresso;
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
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;
import anthony.brenon.mareu.ui_user.ListMareuActivity;
import anthony.brenon.mareu.utils.DeleteViewAction;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListMareuActivity activity;
    private MeetingApiService service;
    private Meeting meetingTest;

    @Rule
    public ActivityTestRule<ListMareuActivity> activityRule =
            new ActivityTestRule(ListMareuActivity.class);

    @Before
    public void setUp() {
        meetingTest = new Meeting("Financement", "Londres", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021", "11h20", 0 -255- 0);

        activity = activityRule.getActivity();
        assertThat(activity, notNullValue());

        service = DI.getNewInstanceApiService();
        service.createMeeting(meetingTest);
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_ma_reu))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_ma_reu)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_ma_reu))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_ma_reu)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     *Meeting test created and completed
     */
    @Test
    public void meetingTest_createdWithSuccess_populateIsRight() {
        //test click on add fab and create activity is displayed
        Espresso.onView(ViewMatchers.withId(R.id.list_meeting_fab_add)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.activity_add_meeting_layout)).check(matches(isDisplayed()));
        //test populate create meeting and create right back to list activity
        Espresso.onView(ViewMatchers.withId(R.id.ti_layout_topic)).perform(typeText("Topic test"));
        Espresso.onView(ViewMatchers.withId(R.id.ti_layout_room)).perform(click());
        Espresso.onView(ViewMatchers.withContentDescription("Londres")).perform(click());
        //->date
        //Espresso.onView(ViewMatchers.withId(R.id.btn_picker_date)).perform(click());

        //->time
        //participants
        //Espresso.onView(ViewMatchers.withId(R.id.ti_layout_participants)).perform(typeText("toto@gegemail.com"));
        //press
        Espresso.onView(ViewMatchers.withId(R.id.button_create_meeting)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_ma_reu)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_ma_reu)).check((matches(hasMinimumChildCount(1))));
    }
}