package anthony.brenon.mareu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 17/09/2021.
 */
public abstract class MeetingsListGenerator {

    public static List<Meeting> MEETINGS_LIST = Collections.singletonList(
            new Meeting("Financement", "Tokyo", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021", "11h20", -255));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGS_LIST);
    }
}
