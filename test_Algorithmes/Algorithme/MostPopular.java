package Algorithme;

import Util.Util;
import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;

import java.util.List;

public class MostPopular extends Algorithm{
    protected Long2IntOpenHashMap Event_Counter = new Long2IntOpenHashMap();
    protected Long2IntOpenHashMap item_Counter = new Long2IntOpenHashMap();
    @Override
    public  void handleEventNotification(List<Notification_Event> id_event) {

        for (Notification_Event e: id_event) {
            Event_Counter.addTo(e.getId_item(), 1);
        }
               System.out.println( "event counter "+Event_Counter);
    }


    @Override
    public void handleItemUpdate(List<Notification_Item> item) {
        for(Notification_Item i:item) {


            item_Counter.addTo(i.getId_item(), 1);
        }
//        System.out.println( "item counter "+item_Counter);


    }



    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event){
        //return the items sorted by their click count
        System.out.println( "event counter Most populaire "+Event_Counter);
        return (LongArrayList) Util.sortByValueAndGetKeys(Event_Counter, false, new LongArrayList());

    }


}
