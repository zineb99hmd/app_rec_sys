import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class sort {
    public static List<Object> list_item = new ArrayList<>();
    public static List<Object> list_event = new ArrayList<>();
    public static MongoClient mongo = new MongoClient("localhost", 27017);
    //System.out.println("Connected to the database successfully");
    public static MongoDatabase database = mongo.getDatabase("sys_rec");
    public static MongoCollection<Document> collection_Item = database.getCollection("Item");
    public static MongoCollection<Document> collection_Event = database.getCollection("Event");
    public static List<Object> read_load_file() {

        String file_item = "C:\\Users\\zineb\\OneDrive\\Bureau\\data_set1\\Items_plista418_1m_fixed.csv";
        String file_event = "C:\\Users\\zineb\\OneDrive\\Bureau\\data_set\\Events_plista418_1m_dedup.csv";
        Item i;

        List<Object> session = new ArrayList<>();



        try {
            BufferedReader br = new BufferedReader(new FileReader(file_item));
            BufferedReader br2 = new BufferedReader(new FileReader(file_event));
            String split_by = ",";
            String line = "";
            String line_event ="";

            long event_id = 0;
            Boolean first_item = true;
            Boolean first_event= true;
            Event ev;
            while ((line = br.readLine()) != null){
                if (first_item) {
                    first_item = false;
                } else {
                    String split_file[] = line.split(split_by);


                    char[] chars = line.toCharArray();

                    long id_item = Long.parseLong(split_file[3].trim());
                    Date created_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(split_file[2]);
                    String created_at_mongodb=split_file[2];
                    String domain = split_file[1];
                    String recom = split_file[4];
                    String Url = split_file[5];
                    long category = Long.parseLong(split_file[7]);
                    String text = split_file[8];
                    String title = split_file[6];
                    i = new Item(id_item, created_at);
                    //enregistrer mongodb -------------
                    Document document = new Document();
                    document.append("Domain", domain);
                    document.append("CeatedAt",created_at_mongodb);
                    document.append("ItemID",id_item);
                    document.append("Recommendable",recom);
                    document.append("URL",Url);
                    document.append("Title",title);
                    document.append("category",category);
                    document.append("Text",text);
                    database.getCollection("Item").insertOne(document);
                    session.add(i);
                    list_item.add(i);


                }}

            //-------------------------------------------------------
            while ((line_event = br2.readLine()) != null){
                if (first_event) {
                    first_event = false;
                } else {


                    String split_file_event[] = line_event.split(split_by);
                    String actiontype= split_file_event[0];
                    long item_id_event = Long.parseLong(split_file_event[1]);
                    long user_id_event = Long.parseLong(split_file_event[2]);
                    //SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    Date time_event = new Date((Long.parseLong(split_file_event[3])));
                    String time_mongodb=split_file_event[3];
                    long geoUser= Long.parseLong(split_file_event[4]);
                    long deviceType= Long.parseLong(split_file_event[5]);
                    //String timestamp = sf.format(time);
                    event_id++;
                    // Map<Long, Event> event = new HashMap<>();
                    ev = new Event(event_id,item_id_event,user_id_event ,time_event);
                    // event.put(event_id, e);
                    session.add(ev);
                    list_event.add(ev);

                    Document document = new Document();
                    document.append("actionType", actiontype);
                    document.append("ItemID",item_id_event);
                    document.append("UserId",user_id_event);
                    document.append("TimeStamp",time_mongodb);
                    document.append("geoUser",geoUser);
                    document.append("deviceType",deviceType);

                    database.getCollection("Event").insertOne(document);






                } }  br.close();




        } catch (Exception e) {
            e.printStackTrace();
        }
        return  session;
    }
    public static List<Object> Sort_with_time(List<Object> a) {
        a.sort(((o1, o2) -> {
            if (o1 instanceof Item && o2 instanceof Item){
                Item g1 = (Item) o1;
                Item g2 = (Item) o2;
                return g1.created_at.compareTo(g2.created_at);
            }else if((o1 instanceof Event && o2 instanceof Event)) {
                Event g1 = (Event) o1;
                Event g2 = (Event) o2;
                return g1.time.compareTo(g2.time);
            }else if(o1 instanceof Event&& o2 instanceof Item){
                Event g1 = (Event) o1;
                Item g2 = (Item) o2;
                return g1.time.compareTo(g2.created_at);
            }else{
                Item g1 = (Item) o1;
                Event g2 = (Event) o2;
                return g1.created_at.compareTo(g2.time);
            }
        }));
        for(Object Obj:a) {

        }
        return a;

    }
}
