package data;

import org.bson.Document;

import java.text.ParseException;
import java.util.Date;

public class Event {
	private  long geoUser;
	private long id_event;
	private long id_item;
	private  long id_user;
	private Date time;
	private  String action;
	private	long device_type;
	private String time_md;
	private	String type;
	   public Event(long id_event,Date time,String type){
	        this.id_event=id_event;
	        this.time=time;
			this.type=type;
	    }

	public Event(long id_event,long id_item ,long id_user,Date time){
		this.id_event=id_event;
		this.id_item=id_item;
		this.id_user=id_user;
		this.time=time;
	}



	public 	Event(String line,long event_id){
	String split_file_event[] = line.split(",");
	this.action= split_file_event[0];
	this.id_item = Long.parseLong(split_file_event[1]);
	this.id_user = Long.parseLong(split_file_event[2]);
	this.id_event=event_id;
	//SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	this.time = new Date((Long.parseLong(split_file_event[3])));
	this.time_md=split_file_event[3];
	this.geoUser= Long.parseLong(split_file_event[4]);
	this.device_type= Long.parseLong(split_file_event[5]);

}
    public Event(Document doc) throws ParseException {
		this.id_event = Long.parseLong(((doc.toString().split(",")[1]).split("=")[1]));
		this.id_item = Long.valueOf(((doc.toString().split(",")[3]).split("=")[1]));
		String time = ((doc.toString().split(",")[5]).split("=")[1]);
		this.time= new Date((Long.parseLong(time)));
		this.id_user = Long.valueOf(((doc.toString().split(",")[4]).split("=")[1]));
		this.geoUser= Long.valueOf(((doc.toString().split(",")[6]).split("=")[1]));
		this.device_type= Long.valueOf((((doc.toString().split(",")[7]).split("=")[1])).split("}")[0]);


	}
/*
	//actionType,ItemID,UserId,TimeStamp,geoUser,deviceType
		public Event(String action,long id_item,long id_user,Date time,long geoUser,long device_type){
			this.action=action;
			this.id_item=id_item;
			this.id_user=id_user;
			this.time=time;
			this.geoUser=geoUser;
			this.device_type=device_type;
		}
		//sauf id_item;
	public Event(String action,long id_user,Date time,long geoUser,long device_type){
		this.action=action;
		this.id_user=id_user;
		this.time=time;
		this.geoUser=geoUser;
		this.device_type=device_type;
	}
*/

	public long getId_event() {
		return id_event;
	}

	public void setGeoUser(long geoUser) {
		this.geoUser = geoUser;
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

	public void setTime(Date time) {
		this.time = time;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setDevice_type(long device_type) {
		this.device_type = device_type;
	}

	public long getId_item() {
		return id_item;
	}

	public long getId_user() {
		return id_user;
	}

	public Date getTime() {
		return time;
	}

	public String getAction() {
		return action;
	}

	public long getDevice_type() {
		return device_type;
	}

	public long getGeoUser() {
		return geoUser;
	}
	public String getTime_md() {
		return time_md;
	}
	public String toString(){
	        return id_event+","+id_item+","+id_user+","+time;
	    }
}
