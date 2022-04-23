package data;

import org.bson.Document;

import java.text.ParseException;
import java.util.Date;

public class Notification_Requete {

    private long id_event;
    private long id_user;
    private long geoUser;
    private long device_type;
    private Date time;
    public Notification_Requete(Document doc) throws ParseException {
        this.id_event = Long.parseLong(((doc.toString().split(",")[1]).split("=")[1]));
        String time = ((doc.toString().split(",")[5]).split("=")[1]);
        this.time= new Date((Long.parseLong(time)));
        this.id_user = Long.valueOf(((doc.toString().split(",")[4]).split("=")[1]));
        this.geoUser= Long.valueOf(((doc.toString().split(",")[6]).split("=")[1]));
        this.device_type= Long.valueOf((((doc.toString().split(",")[7]).split("=")[1])).split("}")[0]);



    }

    public long getId_event() {
        return id_event;
    }

    public long getId_user() {
        return id_user;
    }

    public long getGeo() {
        return geoUser;
    }

    public long getDevice_type() {
        return device_type;
    }

    public Date getTime() {
        return time;
    }

    public void setId_event(Long id_event) {
        this.id_event = id_event;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public void setGeo(long geo) {
        this.geoUser = geo;
    }

    public void setDevice_type(long device_type) {
        this.device_type = device_type;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString(){
        return id_event+","+id_user+","+time+","+geoUser+","+device_type;
    }
}
