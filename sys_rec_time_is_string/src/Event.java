import java.util.Date;
public class Event {
    long id_event;
    long id_item;
    long id_user;
    Date time;
    String action;
    long geo;
    long device_type;
    /*public Event(long id_event,Date time){
        this.id_event=id_event;
        this.time=time;
    }*/
    public Event(long id_event,long id_item ,long id_user,Date time){
        this.id_event=id_event;
        this.id_item=id_item;
        this.id_user=id_user;
        this.time=time;
    }
   /* public Event(long id_event,long id_item, long id_user,long geo, long device_type){
        this.id_event=id_event;
        this.id_item=id_item;
        this.id_user=id_user;
        this.geo=geo;
        this.device_type=device_type;


    }*/


    public String toString(){
        return id_event+","+id_item+","+id_user+","+time;
    }
}
