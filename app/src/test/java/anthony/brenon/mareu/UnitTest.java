package anthony.brenon.mareu;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.util.List;

import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;
import anthony.brenon.mareu.service.MeetingsListGenerator;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class UnitTest {

    private MeetingApiService service;
    Meeting meetingTest = new Meeting("Financement", "Londres", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021", "11h20", 0 -255- 0);

        @Before
        public void setup() { service = DI.getNewInstanceApiService(); }

        //Meeting list is not empty
        @Test
        public void getMeetingsWithSuccess() {
            service.createMeeting(meetingTest);
            List<Meeting> meetings = service.getMeetingList();
            List<Meeting> expectedMeetings = MeetingsListGenerator.MEETINGS_LIST;
            assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
        }

        //Meeting is deleted right
        @Test
        public void deleteMeetingWithSuccess() {
            service.createMeeting(meetingTest);
            Meeting meetingToDelete = service.getMeetingList().get(0);
            service.deleteMeeting(meetingToDelete);
            assertFalse(service.getMeetingList().contains(meetingToDelete));
        }

        //Meeting is created right
        @Test
        public void meetingIsCreatedWithSuccess() {
            service.createMeeting(meetingTest);
            assertTrue(service.getMeetingList().contains(meetingTest));
        }


        //Meeting is populate right
        @Test
        public void meetingIsPopulateRight() {
            service.createMeeting(meetingTest);
            assertEquals("Financement", service.getMeetingList().get(0).getTopic());
        }
}