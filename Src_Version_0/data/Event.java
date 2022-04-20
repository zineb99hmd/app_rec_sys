package data;

import java.util.Date;

public class Event {
	private  long geoUser;
	private long id_event;
	private long id_item;
	private  long id_user;
	private Date time;
	private  String action;
	private	long device_type;
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

public 	Event(String line){
	String split_file_event[] = line.split(",");
	this.action= split_file_event[0];
	this.id_item = Long.parseLong(split_file_event[1]);
	this.id_user = Long.parseLong(split_file_event[2]);
	//SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	this.time = new Date((Long.parseLong(split_file_event[3])));
	this.geoUser= Long.parseLong(split_file_event[4]);
	this.device_type= Long.parseLong(split_file_event[5]);

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

	public String toString(){
	        return id_event+","+id_item+","+id_user+","+time;
	    }
}
