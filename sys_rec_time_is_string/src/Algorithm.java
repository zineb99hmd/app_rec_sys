import it.unimi.dsi.fastutil.longs.LongArrayList;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
public abstract class Algorithm {
    //connect_mongo_db
    /*public static MongoClient mongo = new MongoClient("localhost", 27017);
    //System.out.println("Connected to the database successfully");
    public static MongoDatabase database = mongo.getDatabase("sys_rec");
    public static MongoCollection<Document> collection_Item = database.getCollection("Item");
    public static MongoCollection<Document> collection_Event = database.getCollection("Event");*/
    public static List<Object> session;
    public static List<Object> new_sort_list;
    public static List<Event> id_list_event = new ArrayList<>();
    public static List<Item> id_list_item = new ArrayList<>();
    public static List<Notification_Requete> not_eq_list = new ArrayList();
    public static List<Notification_Event> not_ev_list = new ArrayList();
    public static Event ev;
    public static Item It;
    public static Notification_Requete n_req;
    public static Notification_Item n_it;
    public static  Notification_Event n_ev;


    public abstract void handleEventNotification(List<Event> id_event);

    public abstract void handleItemUpdate(List<Item> item);

    public abstract LongArrayList getRecommendations(List<Notification_Requete> id_event);
    
    //public abstract List<Long> getRecommendations(RecommendationRequest currentRequest);
    public static void main(String[] args) {
        sort s = new sort();
        session = s.read_load_file();
        new_sort_list = s.Sort_with_time(session);
        //System.out.println(new_sort_list);
        for (int i = 0; i < new_sort_list.size(); i++) {
            if (new_sort_list.get(i) instanceof Item) {
                It = new Item(((Item) new_sort_list.get(i)).id_item, ((Item) new_sort_list.get(i)).created_at, ((Item) new_sort_list.get(i)).Url, ((Item) new_sort_list.get(i)).category, ((Item) new_sort_list.get(i)).text, ((Item) new_sort_list.get(i)).title);
                id_list_item.add(It);
               Document doc_it = sort.collection_Item.find(eq("ItemID", ((Item) new_sort_list.get(i)).id_item)).first();
               //System.out.println("doc it"+doc_it);
               if (doc_it == null) {

                    System.out.println("No results found.");
                } else {
                    //System.out.println("doc item"+doc_it);
                    String CreatAt= ((doc_it.toString().split(",")[2]).split("=")[1]);
                    Long Item_id= Long.valueOf(((doc_it.toString().split(",")[3]).split("=")[1]));
                    String Url = ((doc_it.toString().split(",")[5]).split("=")[1]);
                    String title = ((doc_it.toString().split(",")[6]).split("=")[1]);
                    long category = Long.valueOf(((doc_it.toString().split(",")[7]).split("=")[1]));
                    String text = (((doc_it.toString().split(",")[8]).split("=")[1]));

                    n_it=new Notification_Item(Item_id,CreatAt,Url,category,text,title);
                    //System.out.println("text"+text);
                }
            }else
            if (new_sort_list.get(i) instanceof Event) {
                ev = new Event(((Event) new_sort_list.get(i)).id_event, ((Event) new_sort_list.get(i)).id_item, ((Event) new_sort_list.get(i)).id_user, ((Event) new_sort_list.get(i)).time);
                System.out.println("ev" + ev);
                id_list_event.add(ev);
                Document doc = sort.collection_Event.find(eq("ItemID", ((Event) new_sort_list.get(i)).id_item)).first();
                if (doc == null) {
                    System.out.println("No results found.");
                } else {
                    //System.out.println("yes !!!");
                    String id_event = ((doc.toString().split(",")[0]).split("=")[1]);
                    Long Item_id = Long.valueOf(((doc.toString().split(",")[2]).split("=")[1]));
                    Long User_id = Long.valueOf(((doc.toString().split(",")[3]).split("=")[1]));
                    String time = ((doc.toString().split(",")[4]).split("=")[1]);
                    Long geo = Long.valueOf(((doc.toString().split(",")[5]).split("=")[1]));
                    Long device_type = Long.valueOf((((doc.toString().split(",")[6]).split("=")[1])).split("}")[0]);


                    //System.out.println((doc.toString().split(",")[2]).split("=")[1]);
                    //System.out.println("device type" + device_type);
                    n_ev = new Notification_Event(id_event, Item_id, User_id, time, geo, device_type);
                    n_req = new Notification_Requete(id_event, User_id, time, geo, device_type);
                    not_eq_list.add(n_req);

                }
            }
        }

            //System.out.println(n_ev);
            most_popular m = new most_popular();
            recently_popular r = new recently_popular() ;
            r.handleEventNotification(id_list_event);
            System.out.println(r.getRecommendations(not_eq_list));

    }


    }




