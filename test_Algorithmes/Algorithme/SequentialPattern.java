package Algorithme;

import data.Event;
import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import Util.Util;
import java.util.List;
import java.util.Map;

public class SequentialPattern extends Algorithm{
    protected SequenceTreeNode patternTree = new SequenceTreeNode();
    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {
        for (Notification_Event c : id_event) {
            updateMap(c.session);
        }
    }
    @Override
    public void handleItemUpdate(List<Notification_Item> item) {

    }

    @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        //step down the pattern tree to match the right pattern
        SequenceTreeNode currentNode = patternTree;
        for (Notification_Requete e: id_event) {
            if (!currentNode.children.containsKey(getTreeNodeKey(click))) {
                return new LongArrayList();
            }
            currentNode = currentNode.children.get(getTreeNodeKey(click));
        }

        //if we found the right pattern, sort the possible completions of this pattern
        //by their support values and create a recommendation list
        Map<String, SequenceTreeNode> sortMap = Util.sortByValue(currentNode.children, false);
        LongArrayList returnList = new LongArrayList();
        for (String i : sortMap.keySet()){
            returnList.add(Long.parseLong(i));
        }
        return returnList;
    }
    protected void updateMap(List<Event> session) {
        SequenceTreeNode currentNode = patternTree;
        //step down the pattern tree based on the pattern of the session
        for (int i = 0; i < session.size(); i++) {
            Event event = session.get(i);
            if (currentNode.children.containsKey(getTreeNodeKey(event))) {
                currentNode = currentNode.children.get(getTreeNodeKey(event));
            } else {
                SequenceTreeNode node = new SequenceTreeNode();
                currentNode.children.put(getTreeNodeKey(event), node);
                currentNode = node;
            }
            //increase the support value of the last node
            if (i == session.size() - 1) {
                currentNode.support++;
            } else if (i == session.size() - 2) {
                //decrease the support of the second-to-last node
                //(because of incremental learning, we assume each time that the session is over
                //and add the complete pattern. If the pattern was incomplete, we decrease the support
                //and increase the support for the full pattern here)
                currentNode.support--;
            }
        }
    }
    protected String getTreeNodeKey(Event e){
        return ""+ e.getId_item();
    }

    public class SequenceTreeNode implements Comparable<SequenceTreeNode>{
        Map<String,SequenceTreeNode> children = new Object2ObjectOpenHashMap<String,SequenceTreeNode>();
        int support=0;
        public int compareTo(SequenceTreeNode o) {
            return this.support-o.support;
        }

    }
}
