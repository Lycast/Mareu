package anthony.brenon.mareu.di;

import anthony.brenon.mareu.service.DummyMeetingApiService;
import anthony.brenon.mareu.service.MeetingApiService;

/**
 * Created by Lycast on 16/09/2021.
 * * Dependency injector to get instance of services
 */
public class DI {

    private static final MeetingApiService service = new DummyMeetingApiService();


    /**
     * Get an instance on @{@link MeetingApiService}
     * @return
     */
    public  static MeetingApiService getMeetingApiService(){return service;}
}
