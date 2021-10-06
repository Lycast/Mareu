package anthony.brenon.mareu.model;

import java.io.Serializable;

/**
 * Created by Lycast on 16/09/2021.
 */
public class Meeting implements Serializable {

    private String topic;
    private String room;
    private String participants;
    private String time;

    //Construct
    public Meeting(String topic, String room, String participants, String time) {
        this.topic = topic;
        this.room = room;
        this.participants = participants;
        this.time = time;
    }

    //Getter and Setter
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getParticipants() { return participants; }
    public void setParticipants(String participants) { this.participants = participants; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
