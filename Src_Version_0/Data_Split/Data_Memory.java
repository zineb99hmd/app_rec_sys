package Data_Split;

import data.Event;
import data.Item;

import java.util.Date;

public class Data_Memory {
   private long id;
    private Date time;
    private String type;
    public Data_Memory(long id, Date time){
        this.id=id;
        this.time=time;

    }
    public Data_Memory(Item item, String type){
        this.id=item.getId_item();
        this.time=item.getCreated_at();
        this.type=type;
    }
    public Data_Memory(Event event,String type){
        this.id=event.getId_event();
        this.time=event.getTime();
        this.type=type;

    }

    public String toString() {
        return id+","+time+" "+type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
