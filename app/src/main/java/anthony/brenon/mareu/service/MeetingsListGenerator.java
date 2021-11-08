package anthony.brenon.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 17/09/2021.
 */
public abstract class MeetingsListGenerator {

    public static List<Meeting> MEETINGS_LIST = Arrays.asList(
            new Meeting("Financement", "Tokyo", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021", "11h20", 0 -255- 0));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGS_LIST);
    }
}
