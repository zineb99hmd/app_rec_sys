package data;

import java.util.List;
public class EventData {
    public Event click;
    //the session in which this click occurred
    public List<Event> session;
    //all of the users previous clicks
    public List<Event> wholeUserHistory;
}
