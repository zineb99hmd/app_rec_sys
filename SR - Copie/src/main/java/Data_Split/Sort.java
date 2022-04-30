package Data_Split;

import data.Event;
import data.Item;
//import db.DAO;
import com.example.sr.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {
    public static List<Object> list_item = new ArrayList<>();
    public static List<Object> list_event = new ArrayList<>();
   public static List<Data_Memory> session_DataMemory = new ArrayList<Data_Memory>();
   public page2 p=new page2();

    public String  file_item=p.getLien_item();
    public String  file_event=p.getLien_event();
    public Item i,item;
    public    String line = "";
    public  String line_event ="";



    public List<Data_Memory> getSession_DataMemory() {
        return session_DataMemory;
    }

    public  List<Data_Memory> read_load_file() {


        System.out.println("item file"+file_item);
       System.out.println("event file"+file_event);


        Event event;
        Data_Memory d;
        page2 p=new page2();




        try {
            BufferedReader br = new BufferedReader(new FileReader(file_item));
            BufferedReader br2 = new BufferedReader(new FileReader(file_event));
            String split_by = ",";

            long event_id = 0;
            Boolean first_item = true;
            Boolean first_event= true;
            Event ev,ev_;
            while ((line = br.readLine()) != null){
                if (first_item) {
                    first_item = false;
                } else {
                    item=new Item(line);
                    //DAO.Insert_Item(item);
                    i = new Item(item.getId_item(),item.getCreated_at(),"Item");

                    p.data_item.add(i);


                    //System.out.println("data item li f sort "+page2.data_item);
                     d = new Data_Memory(i,"Item");
                     session_DataMemory.add(d);
                }}

            //-------------------------------------------------------
            while ((line_event = br2.readLine()) != null){
                if (first_event) {
                    first_event = false;
                } else {
                    event=new Event(line_event,event_id);
                   // DAO.Insert_Event(event);
                    event_id++;
                    ev = new Event(event.getId_event(),event.getTime(),"Event");
                    ev_=new Event(event.getId_event(),event.getId_item(),event.getId_user(),event.getTime());
                    p.data_event.add(ev_);
                    d=new Data_Memory(ev,"Event");
                    session_DataMemory.add(d);
                } }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  session_DataMemory;
    }

    //sort ___class session(id,time,type)
    public   void Sort_By_time(List<Data_Memory> list_){
        //Ordonner les lignes du Dataset par date (Ordre chronologique)
        Collections.sort(list_, new Comparator<Data_Memory>() {
            public int compare(Data_Memory i1, Data_Memory i2) {
                return i1.getTime().compareTo(i2.getTime());
            }
        });
        for (Data_Memory Data : list_) {
            System.out.println(Data);
        }

    }


}