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

        @Before
        public void setup() { service = DI.getNewInstanceApiService(); }

        //Meeting list is not empty
        @Test
        public void getMeetingsWithSuccess() {
            List<Meeting> meetings = service.getMeetingList();
            List<Meeting> expectedMeetings = MeetingsListGenerator.MEETINGS_LIST;
            assertEquals(meetings, expectedMeetings);
        }

        //Meeting is deleted right
        @Test
        public void deleteMeetingWithSuccess() {
            Meeting meetingToDelete = service.getMeetingList().get(0);
            service.deleteMeeting(meetingToDelete);
            assertFalse(service.getMeetingList().contains(meetingToDelete));
        }

        //Meeting is created right
        @Test
        public void meetingIsCreatedWithSuccess() {
            Meeting meetingTest = new Meeting("RH", "Londres", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021", "11h20", 0 -255- 0);
            service.createMeeting(meetingTest);
            assertTrue(service.getMeetingList().contains(meetingTest));
        }


        //Meeting filter by room test
        @Test
        public void getMeetingByRoom_withSuccess() {
            List<Meeting> meetingsByRoom = service.filterRoomList("Tokyo");
            assertFalse(meetingsByRoom.isEmpty());
            List<Meeting> meetingsByRoomB = service.filterRoomList("");
            assertTrue(meetingsByRoomB.isEmpty());
        }

        //Meeting filter by date test
        @Test
        public void getMeetingByDate_withSuccess() {
            List<Meeting> meetingsByDate = service.filterDateList("12/12/2021");
            assertFalse((meetingsByDate.isEmpty()));
            List<Meeting> meetingsByDateB = service.filterDateList("");
            assertTrue((meetingsByDateB.isEmpty()));
        }
}