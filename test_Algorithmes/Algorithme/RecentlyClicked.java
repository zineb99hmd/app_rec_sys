package Algorithme;

import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;

import java.util.List;

public class RecentlyClicked extends Algorithm{
    public LongLinkedOpenHashSet clickedItems = new LongLinkedOpenHashSet();

    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {
        for (Notification_Event c : id_event) {
            clickedItems.addAndMoveToFirst(c.getId_item());
        }
    }

    @Override
    public void handleItemUpdate(List<Notification_Item> item) {

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        return new LongArrayList(clickedItems);
    }
}
