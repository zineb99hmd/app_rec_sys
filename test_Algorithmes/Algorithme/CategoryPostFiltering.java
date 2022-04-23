package Algorithme;

import data.Event;
import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;

import java.util.List;
import java.util.Set;

public class CategoryPostFiltering extends Algorithm{
    //the algorithm whose results should be post-filtered (set via JSON configuration)
    protected Algorithm mainStrategy;
    //should we fill append the items that were filtered from the result list again at the end of the list?
    private boolean fallback = false;
    //should we consider the categories of all items from the current session or just the last clicked item?
    private boolean considerSession = false;
    //a map of categories for each item
    private Long2IntOpenHashMap categoryMap = new Long2IntOpenHashMap();

    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {
        for (Notification_Event e : id_event) {
            categoryMap.put(e.getId_item(), e.g);
        }
        //let the underlying algorithm train
        mainStrategy.train(items, clickData);
    }

    @Override
    public void handleItemUpdate(List<Notification_Item> item) {

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        //filter the items that match with the current click's category
        //first, retrieve the recommendation results from the underlying algorithm
        LongArrayList allRec = mainStrategy.getRecommendations(clickData);

        //if we want to consider all categories from the items of the current session,
        //create a hashset with all these item categories
        Set<Integer> setOfCategoryInSession = null;
        if (considerSession) {
            setOfCategoryInSession = new IntOpenHashSet();
            for (Event t : clickData.session) {
                setOfCategoryInSession.add(t.item.category);
            }
        }

        //create lists of filtered items and retained items
        LongArrayList allRecInSameCategory = new LongArrayList();
        LongArrayList allRecNotSameCategory = new LongArrayList();
        //iterate over the recommendations
        for (int j = 0; j < allRec.size(); j++) {
            long id = allRec.getLong(j);
            int category = categoryMap.get(allRec.getLong(j));
            //check the category of the i-th item in the recommendation list
            if ((!considerSession && category == clickData.click.item.category)
                    || (considerSession && setOfCategoryInSession.contains(category))) {
                allRecInSameCategory.add(id);
            } else if (fallback) {
                //if we have a fallback, add the filtered item to the fallback list
                allRecNotSameCategory.add(id);
            }
        }
        //merge the filtered list with the fallback list (empty in case fallback==false)
        allRecInSameCategory.addAll(allRecNotSameCategory);
        //return the filtered list
        return allRecInSameCategory;
    }


    public void setMainStrategy(Algorithm algorithm){
        mainStrategy = algorithm;
    }

    public void setFallback(boolean fallback) {
        this.fallback = fallback;
    }

    public void setConsiderSession(boolean considerSession) {
        this.considerSession = considerSession;
    }
}
