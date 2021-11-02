package anthony.brenon.mareu.service;

import java.util.ArrayList;
import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 16/09/2021.
 */
public class DummyMeetingApiService implements MeetingApiService{

    private final List<Meeting> meetings = MeetingsListGenerator.generateMeetings();


        @Override
        public List<Meeting> getMeetingList() {
            return meetings;
        }

        @Override
        public void deleteMeeting(Meeting meeting) {
            meetings.remove(meeting);
        }

        @Override
        public void createMeeting(Meeting meeting) { meetings.add(meeting); }

        @Override
        public List<Meeting> filterDateList(String date) {
            List<Meeting> meetingsByDate = new ArrayList<>();
            for(Meeting meeting : meetings) {
                if (meeting.getDate().equals(date)) {
                    meetingsByDate.add(meeting);
                }
            }
            return meetingsByDate;
        }

        @Override
        public List<Meeting> filterRoomList(String room) {
            List<Meeting> meetingsByRoom = new ArrayList<>();
            for(Meeting meeting : meetings) {
                if (meeting.getRoom().equals(room)) {
                    meetingsByRoom.add(meeting);
                }
            }
            return meetingsByRoom;
        }
}
