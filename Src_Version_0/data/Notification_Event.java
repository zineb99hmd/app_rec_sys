package data;

import java.util.Date;

public class Notification_Event {
    String id_event;
    public long id_item;
    long id_user;
    long geo;
    long device_type;
    public Date time;
    public Notification_Event(String id_event, long id_item , long id_user, Date time, long geo, long device_type){
        this.id_event=id_event;
        this.id_item=id_item;
        this.id_user=id_user;
        this.time=time;
        this.geo=geo;
        this.device_type=device_type;
    }
    public String toString(){
        return id_event+","+id_item+","+id_user+","+time+","+geo+","+device_type;
    }
}
