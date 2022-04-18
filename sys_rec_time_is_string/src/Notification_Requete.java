public class Notification_Requete {

    String id_event;

    long id_user;
    long geo;
    long device_type;
    String time;
    public Notification_Requete(String id_event , long id_user, String time, long geo, long device_type){
        this.id_event=id_event;
        this.id_user=id_user;
        this.time=time;
        this.geo=geo;
        this.device_type=device_type;


    }
    public String toString(){
        return id_event+","+id_user+","+time+","+geo+","+device_type;
    }
}
