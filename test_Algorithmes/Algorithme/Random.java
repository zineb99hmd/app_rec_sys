package Algorithme;

import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;

import java.util.Collections;
import java.util.List;

public class Random extends Algorithm{
    private LongOpenHashSet items = new LongOpenHashSet();
    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {

    }

    @Override
    public void handleItemUpdate(List<Notification_Item> item) {
        //if new items arrive, add their ids to the set of item ids
        for (Notification_Item it : item) {
            this.items.add(it.getId_item());
        }

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        //create a result list and copy the known item ids there
        LongArrayList recs = new LongArrayList(items);
        //shuffle the result list and return it
        Collections.shuffle(recs);
        return recs;
    }
}
