package Algorithme;

import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;

import java.util.List;
public abstract class Algorithm {


    public abstract void handleEventNotification(List<Notification_Event> id_event);

    public abstract void handleItemUpdate(List<Notification_Item> item);

    public abstract LongArrayList getRecommendations(List<Notification_Requete> id_event);




}
