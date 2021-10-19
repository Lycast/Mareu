package anthony.brenon.mareu.service;

import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 16/09/2021.
 */
public class DummyMeetingApiService implements MeetingApiService{

    private final List<Meeting> meetings = MeetingsList.generateMeetings();

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
    public List<Meeting> filterTimeList() {
        return null;
    }

    @Override
    public List<Meeting> filterRoomList() {
        return null;
    }
}
