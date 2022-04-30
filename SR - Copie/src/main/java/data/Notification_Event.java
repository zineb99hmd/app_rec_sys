package data;

import java.util.Date;

public class Notification_Event {
    private long id_event;
    private long id_item;
    private long id_user;
    private long geo;
    private  long device_type;
    private Date time;
    public Notification_Event(Event event){
        this.id_event=event.getId_event();
        this.id_item=event.getId_item();
        this.id_user=event.getId_user();
        this.time=event.getTime();
        this.geo=event.getGeoUser();
        this.device_type=event.getDevice_type();
    }

    public long getId_event() {
        return id_event;
    }

    public long getId_item() {
        return id_item;
    }

    public long getId_user() {
        return id_user;
    }

    public long getGeo() {
        return geo;
    }

    public long getDevice_type() {
        return device_type;
    }

    public Date getTime() {
        return time;
    }

    public void setId_event(long id_event) {
        this.id_event = id_event;
    }

    public void setId_item(long id_item) {
        this.id_item = id_item;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public void setGeo(long geo) {
        this.geo = geo;
    }

    public void setDevice_type(long device_type) {
        this.device_type = device_type;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString(){
        return id_event+","+id_item+","+id_user+","+time+","+geo+","+device_type;
    }
}
