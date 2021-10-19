package anthony.brenon.mareu.service;

import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 16/09/2021.
 */
public interface MeetingApiService {

    List<Meeting> getMeetingList();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);

    List<Meeting> filterTimeList();

    List<Meeting> filterRoomList();
}
