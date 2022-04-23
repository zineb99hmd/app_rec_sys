package Algorithme;

import data.Event;
import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import Util.Util;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FastSessionCoOccurrence extends Algorithm {
    protected Map<String, Object2IntOpenHashMap<String>> coOcurrenceMap = new Object2ObjectOpenHashMap<>();
    //should the whole current sessions be considered or just the current item
    protected boolean wholeSession = false;
    //should we only count co-occurrences in the last N clicks?
    protected boolean buffer = false;
    //how large should the click buffer be?
    protected int bufferSize = 10000;
    //the buffer of co-occurrences from the last N clicks
    protected LinkedList<AbstractMap.Entry<String, String>> ringBuffer = new LinkedList<>();

    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {
        for (Notification_Event e : id_event) {
            updateMap(e.session);
        }

    }

    @Override
    public void handleItemUpdate(List<Notification_Item> item) {

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        //create a list of scores for each item, which is the sum of all co-occurrence counts
        Map<String, Double> combineWeights = new Object2DoubleOpenHashMap<String>();
        //depending on if we are supposed to use the whole session or not,
        //this for loop only does one iteration on the last element or it iterates over
        //all click in the current user sesssion
        for (int i = wholeSession ? 0 : (clickData.session.size() - 1); i < clickData.session.size(); i++) {
            Transaction click = clickData.session.get(i);
            // if the inner map is empty, we cannot recommend anything
            if (!coOcurrenceMap.containsKey(getCoOccurrenceKey(click))) {
                continue;
            }
            // get the inner map of items that this item has co-occurred with
            Map<String, Integer> m = coOcurrenceMap.get(getCoOccurrenceKey(click));
            for (Map.Entry<String, Integer> entry : m.entrySet()) {
                // sum up the co-occurrence weights for each item
                Double currVal = combineWeights.get(entry.getKey());
                if (currVal == null) {
                    currVal = 0d;
                }
                combineWeights.put(entry.getKey(), currVal + entry.getValue());
            }
        }

        // sort the weighted sums
        Map<String, Double> sortedKeys = Util.sortByValue(combineWeights, false);
        return generateResultList(sortedKeys, clickData);
    }
    protected void updateMap(List<Transaction> session) {
        for (int i = 0; i < session.size() - 1; i++) {
            if (getCoOccurrenceKey(session.get(i)) == getCoOccurrenceKey(session.get(session.size()-1))) {
                // ignore co-occurrences of items with themselves
                continue;
            }
            // we add one entry to the map with the "correct" order (time-based)
            addTuple(session.get(i), session.get(session.size()-1));
            // map with opposite order
            addTuple(session.get(session.size()-1), session.get(i));
        }
        //if we are supposed to only look at the last N clicks,
        //this part of the method checks the buffer and cleans out old clicks.
        if(buffer){
            while(ringBuffer.size()>bufferSize){
                //adjust map
                Map.Entry<String, String> first = ringBuffer.poll();
                Object2IntOpenHashMap<String> map = coOcurrenceMap.get(first.getKey());
                map.addTo(first.getValue(), -1);
                map.remove(first.getValue(), 0);//remove if 0
            }
        }
    }
    private void addTuple(Event a, Event b) {
        String keyA = getCoOccurrenceKey(a);
        String keyB = getCoOccurrenceKey(b);
        if(buffer){
            ringBuffer.add(new AbstractMap.SimpleEntry<String, String>(keyA, keyB));
        }
        // check if the inner map exists
        if (!coOcurrenceMap.containsKey(keyA)) {
            coOcurrenceMap.put(keyA, new Object2IntOpenHashMap<String>());
        }
        Object2IntOpenHashMap<String> map = coOcurrenceMap.get(keyA);
        // check if the item in the inner map exists
        map.addTo(keyB, 1);
    }

    protected LongArrayList generateResultList(Map<String, Double> sortedKeys, ClickData clickData) {
        // remap all item ids back to the actual Item objects
        LongArrayList sortedItems = new LongArrayList();
        for (String itemId : sortedKeys.keySet()) {
            sortedItems.add(Long.parseLong(itemId));
        }
        return sortedItems;
    }
    protected String getCoOccurrenceKey(Event e) {
        return "" + e.getId_item();
    }
    public void setWholeSession(boolean wholeSession) {
        this.wholeSession = wholeSession;
    }

    void setBuffer(boolean buffer) {
        this.buffer = buffer;
    }

    void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}


