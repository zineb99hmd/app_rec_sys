package Algorithme;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import data.Event;
import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import Util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeywordJaccard extends Algorithm{
    //a map between an item id and the bitmap representation of keywords of this item
    private Map<Long, EWAHCompressedBitmap> itemKeywordMap = new Long2ObjectOpenHashMap<>();
    //a map for keywords (EWAHCompressedBitmap can only hold INT, but keywords are STRING)
    private Map<String, Integer> keywordMap = new Object2IntOpenHashMap<String>();
    //indicates which keyword id to map to next
    private int currentKeywordCounter = 0;
    //a map between keywords and items. For easy lookup so that not all items have to be compared with each other.
    //in case they have no overlap at all.
    private Map<Integer, Set<Long>> keywordItemMap = new Int2ObjectOpenHashMap<Set<Long>>();
    private List<Event> session;

    //for the final recommendation list scores, should we take only the similarity with the currently
    //clicked item or the whole current user session into account.
    private boolean wholeSession;



    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {

    }

    @Override
    public void handleItemUpdate(List<Notification_Item> items) {
        //iterate over all newly published items
        for (Notification_Item item : items) {
            //if the keywords of the item are not null, create a keyword map an save it
            if(item.getKeywords()!=null){
                //create an empty bitmap of keyword IDs
                EWAHCompressedBitmap itemKeywords = new EWAHCompressedBitmap();
                itemKeywordMap.put(item.getId_item(), itemKeywords);
                //iterate over the item's keywords
                for (String keyword : item.getKeywords().keySet()) {
                    //if the keyword is unknown, generate a new INT id for it
                    if(!keywordMap.containsKey(keyword)){
                        keywordMap.put(keyword, ++currentKeywordCounter);
                        keywordItemMap.put(currentKeywordCounter, new LongOpenHashSet());
                    }
                    //extract the id of the keyword
                    int keywordInt = keywordMap.get(keyword);
                    //set the bit for this keyword in the item's bitmap
                    itemKeywords.set(keywordInt);
                    //also set the item in the keyword's reverse lookup map
                    keywordItemMap.get(keywordInt).add(item.getId_item());
                }
            }
        }

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {

        //generate a list of item ids, from the current session, with which we want to compare
        LongOpenHashSet itemIDsToCompare = new LongOpenHashSet();
        if(wholeSession){
            //either all items from the current session
            for (Event sessionEvent : session) {
                itemIDsToCompare.add(sessionEvent.getId_item());
            }
        }else{
            //or only the last item
            itemIDsToCompare.add(clickData.click.item.id);
        }

        //make a list of all candidate items
        LongOpenHashSet items = new LongOpenHashSet();
        //from the reverse lookup, extract all items that have at least
        //one keyword in common with the item(s) from the current sessions
        for(long itemToCompare : itemIDsToCompare){
            EWAHCompressedBitmap currentItemKeywords = itemKeywordMap.get(itemToCompare);
            if(currentItemKeywords!=null){
                for (Integer keyword : currentItemKeywords) {
                    items.addAll(keywordItemMap.get(keyword));
                }
            }
        }

        //remove the current item from the candidates
        items.remove(clickData.click.item.id);

        //create a map of scores for each recommendation candidate item
        Long2DoubleOpenHashMap scores = new Long2DoubleOpenHashMap();
        //iterate over the candidate items
        for (LongIterator iterator = items.iterator(); iterator.hasNext();) {
            long item = iterator.nextLong();
            EWAHCompressedBitmap entry = itemKeywordMap.get(item);
            double score = 0;
            //compare the actual keyword maps and sum up the scores
            for (long itemId : itemIDsToCompare) {
                EWAHCompressedBitmap bitmap = itemKeywordMap.get(itemId);
                if(bitmap!=null){
                    score += similarity(bitmap, entry);
                }
            }
            //if the score is not 0, add it to the score map
            if(score>0){
                scores.put(item, score);
            }
        }
        //return candidates ordered by score
        return (LongArrayList) Util.sortByValueAndGetKeys(scores, false, new LongArrayList());
    }
    protected double similarity(EWAHCompressedBitmap itemKeywords, EWAHCompressedBitmap anotherItemsKeywords) {
        int intersection = itemKeywords.andCardinality(anotherItemsKeywords);
        if (intersection == 0) {
            // if the intersection is 0 -> return 0
            return 0;
        }
        // otherwise calculate the jaccard
        int union = itemKeywords.orCardinality(anotherItemsKeywords);
        return intersection * 1d / union * 1d;
    }
    public void setWholeSession(boolean wholeSession) {
        this.wholeSession = wholeSession;
    }
}
