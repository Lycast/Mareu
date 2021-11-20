package anthony.brenon.mareu.test;


import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


import static anthony.brenon.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedTestMareu {

    // This is fixed
    private static int ITEMS_COUNT = 2;

    private ListMareuActivity activity;
    private MeetingApiService service;

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

    @Test
    public void filterDate_withSuccess() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021,12,12));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.list_meetings_recycler_view)).check((withItemCount(1)));
    }

    @Test
    public void filterRoom_withSuccess() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Room")).perform(click());
        onView(withText("Tokyo")).perform(click());
        onView(withId(R.id.list_meetings_recycler_view)).check((withItemCount(1)));
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
        onView(withId(R.id.ti_ed_topic)).perform(replaceText("Topic test"));

        //chek return meeting list
        onView(withId(R.id.btn_picker_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,10,12));
        onView(withId(android.R.id.button1)).perform(click());

        //input room
        onView(withId(R.id.ti_ed_room)).perform(click());
        onData(equalTo("Paris")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.ti_ed_participants)).perform(typeText("emailtest1@gmail.com"), pressImeActionButton());//press enter

        closeSoftKeyboard();

        //put button create
        onView(withId(R.id.button_create_meeting)).perform(click());
        //chek meeting is displayed
        onView(withId(R.id.list_meetings_recycler_view)).check((matches(hasChildCount(2))));
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
        // Given : We check list with ITEM COUNT
        onView(ViewMatchers.withId(R.id.list_meetings_recycler_view)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meetings_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 1
        onView(ViewMatchers.withId(R.id.list_meetings_recycler_view)).check(withItemCount(ITEMS_COUNT -1));
    }
}