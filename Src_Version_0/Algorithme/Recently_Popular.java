package Algorithme;

import data.Notification_Event;

import java.util.LinkedList;
import java.util.List;

public class Recently_Popular extends MostPopular{
    //The time up until which clicks are counted for the "popularity"
    private int filterTime = 24*60*60*1000;//default = 1 day (millisecond)
    //a buffer of recent transactions so that click counts can be decreased when they "leave" the time window
    private List<Notification_Event> recentTransactions = new LinkedList<Notification_Event>();

    @Override
    public void handleEventNotification(List<Notification_Event> Event) {
        //train the basic "most popular" implementation
        super.handleEventNotification(Event);
        //add the training click to the transaction buffer
        recentTransactions.addAll(Event);
        System.out.println("recet  "+recentTransactions);
        //filter all clicks from the transaction buffer that have left the time window (e.g. when they occurred more than 1h ago)
        while((recentTransactions.get(recentTransactions.size()-1).time.getTime()-recentTransactions.get(0).time.getTime())>filterTime){
            //if the transactions get old, remove them from the list and decrease the popularity count
            if(recentTransactions.get(0)!=null){
                Event_Counter.addTo(recentTransactions.get(0).id_item, -1);
            }
            recentTransactions.remove(0);
        }
        //for the actual recommending: let the super implementation do the work
    }




    /**
     * Sets the time up until which clicks are counted for the "popularity"
     * @param filterTime -
     */
    public void setFilterTime(int filterTime) {
        this.filterTime = filterTime;
    }

}
