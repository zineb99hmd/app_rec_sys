//
//package db;
//
//        import com.mongodb.BasicDBObject;
//        import com.mongodb.MongoClient;
//        import com.mongodb.client.MongoCollection;
//        import com.mongodb.client.MongoCursor;
//        import com.mongodb.client.MongoDatabase;
//        import data.Event;
//        import data.Item;
//        import org.bson.Document;
//
//        import java.util.logging.Level;
//        import java.util.logging.Logger;
//public class DAO {
//    public static MongoDatabase database;
//
//    public static  void connection_mongo(String Base_Name){
//        Logger logger = Logger.getLogger("org.mongodb.driver");
//        logger.setLevel(Level.SEVERE);
//        //  Connexion avec Mongod
//        MongoClient mongo=new MongoClient("Localhost",27017);
//        // System.out.println("Connected to the database successfully");
//        // Accéder a la base de Donnée
//        database = mongo.getDatabase(Base_Name);
//
//
//
//    }
//
//
//    public static void ADD_Document_Item( long domain,
//                                          String Created_at, long Item_Id, String recom,
//                                          String Url, String title, long category, String text){
//        //Preparing a document
//        //ItemType,Domain,CreatedAt,ItemID,Recommendable,URL,Title,category,text,keywords,categories
//        Document document = new Document();
//        if (!Get_Document_Test(database,"ItemID",Item_Id,"Item")){
//            document.append("Domain", domain);
//            document.append("CreatedAt",Created_at );
//            document.append("ItemID",Item_Id);
//            document.append("Recommendable",recom);
//            document.append("URL",Url);
//            document.append("Title",title);
//            document.append("category",category);
//            document.append("text",text);
//            //Inserting the document into the collection
//            database.getCollection("Item").insertOne(document);
//        }
//
//        // System.out.println("Document inserted successfully");
//    }
//    public static void Insert_Item(Item item){
//        ADD_Document_Item(item.getDomain(), item.getCreated_md(),item.getId_item(),item.getRecom(), item.getUrl(), item.getTitle(),item.getCategory(),item.getText());
//    }
//    public static void Insert_Event(Event event){
//        ADD_Document_Event(event.getId_event(),event.getAction(),event.getId_item(),event.getId_user(), event.getTime_md(),event.getGeoUser(),event.getDevice_type());
//    }
//    public static void ADD_Document_Event(long Id_event, String actiontype, long item_id_event, long user_id_event, String time_event, long geoUser, long deviceType){
//        //actionType,ItemID,UserId,TimeStamp,geoUser,deviceType
//        Document document = new Document();
//        if(!Get_Document_Test(database,"Id_event",Id_event,"Event")){
//            document.append("Id_event",Id_event);
//            document.append("actionType", actiontype);
//            document.append("ItemID",item_id_event);
//            document.append("UserId",user_id_event);
//            document.append("TimeStamp",time_event);
//            document.append("geoUser",geoUser);
//            document.append("deviceType",deviceType);
//            database.getCollection("Event").insertOne(document);
//        }
//
//    }
//
//
//    public static Document Get_Document(MongoDatabase database, String Id_Name, long Id_ , String Collection_Name){
//        try {
//            MongoCollection<Document> collection_Item = database.getCollection(Collection_Name);
//
//            BasicDBObject Query = new BasicDBObject();
//            Query.put(Id_Name, Id_);
//            MongoCursor<Document> cursor = collection_Item.find(Query).iterator();
//            while (cursor.hasNext()) {
//                return cursor.next();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static Boolean Get_Document_Test(MongoDatabase database, String Id_Name, long Id_ , String Collection_Name){
//        try {
//            MongoCollection<Document> collection_Item = database.getCollection(Collection_Name);
//
//            BasicDBObject Query = new BasicDBObject();
//            Query.put(Id_Name, Id_);
//            MongoCursor<Document> cursor = collection_Item.find(Query).iterator();
//            while (cursor.hasNext()) {
//                return true;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//}
//
