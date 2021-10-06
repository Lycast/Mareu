package anthony.brenon.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 17/09/2021.
 */
public abstract class MeetingsList {

    public static List<Meeting> MEETINGS_LIST = Arrays.asList(
            new Meeting("Financement", "tokyo", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021"),
            new Meeting("Webinar", "madrid", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021"),
            new Meeting("Raclette", "paris", "toto@gegemail.com, titi@gegemail.com, hugue@gegemail.com", "12/12/2021"));


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGS_LIST);
    }
}
