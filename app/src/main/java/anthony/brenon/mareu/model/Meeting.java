package anthony.brenon.mareu.model;

import java.io.Serializable;

/**
 * Created by Lycast on 16/09/2021.
 */
public class Meeting implements Serializable {

    private String topic;
    private String room;
    private String participants;
    private String date;
    private String time;
    private int color;

    //Construct
    public Meeting(String topic, String room, String participants,String date, String time, int color) {
        this.topic = topic;
        this.room = room;
        this.participants = participants;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    //Getter and Setter
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getParticipants() { return participants; }
    public void setParticipants(String participants) { this.participants = participants; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }
}
