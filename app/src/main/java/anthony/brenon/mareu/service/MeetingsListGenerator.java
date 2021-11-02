package anthony.brenon.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 17/09/2021.
 */
public abstract class MeetingsListGenerator {

    public static List<Meeting> MEETINGS_LIST = Arrays.asList();

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGS_LIST);
    }
}
