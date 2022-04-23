package streamrec;

import Algorithme.Algorithm;
import Algorithme.KeywordJaccard;
import Algorithme.MostPopular;
import Algorithme.Recently_Popular;
import Data_Split.Data_Memory;
import Data_Split.Sort;
import data.*;
import db.DAO;
import org.bson.Document;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static db.DAO.connection_mongo;


public class Main_ {
    public static List<Event> id_list_event = new ArrayList<>();
    public static List<Item> id_list_item = new ArrayList<>();
    public static List<Notification_Item> not_IT_list = new ArrayList();
    public static List<Notification_Requete> not_eq_list = new ArrayList();
    public static List<Notification_Event> not_ev_list = new ArrayList();
    public static List<Object> session;
    public static List<Object> new_sort_list;
    public static Event Ev;
    public static Document It_doc,Ev_doc;
    public static Item it;
    public static Notification_Requete n_req;
    public static Notification_Item n_it;
    public static  Notification_Event n_ev;
    public  static List<Algorithm> alg=new ArrayList<>();
    public static List<Data_Memory> Event_Test=new ArrayList<Data_Memory>();

    public static Date addMinutesToDate(int minutes, Date beforeTime) {
        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs
                + (minutes * 60000));
        return afterAddingMins;
    }
    public static void progressPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent, bare.length());
        System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }

    public static void main(String[] args) throws ParseException {
        connection_mongo("sys_rec");
        Algorithm m=new MostPopular();
        Algorithm r=new Recently_Popular();
        Algorithm jacard=new KeywordJaccard();
        //alg.add(m);
        //alg.add(r);
        alg.add(jacard);
        Sort S=new Sort();

        S.Sort_By_time(S.read_load_file());

        for (int i=0;i< S.getSession_DataMemory().size();i++) {
            progressPercentage(i+1,S.getSession_DataMemory().size());
            for (Algorithm algo : alg) {
                if (S.getSession_DataMemory().get(i).getType() == "Item") {
                    It_doc = DAO.Get_Document(DAO.database, "ItemID", S.getSession_DataMemory().get(i).getId(), "Item");
                    it = new Item(It_doc);
                    n_it = new Notification_Item(it.getId_item(), it.getCreated_at(), it.getUrl(), it.getCategory(), it.getText(), it.getTitle(),it.getKeywords());
                    not_IT_list.add(n_it);
                    algo.handleItemUpdate(not_IT_list);
                } else {
                    if (S.getSession_DataMemory().get(i).getType() == "Event") {
                        Ev_doc = DAO.Get_Document(DAO.database, "Id_event", S.getSession_DataMemory().get(i).getId(), "Event");

                        Ev = new Event(Ev_doc);
                        n_ev = new Notification_Event(Ev);
                        not_ev_list.add(n_ev);
                        n_req = new Notification_Requete(Ev_doc);
                        not_eq_list.add(n_req);

                        algo.handleEventNotification(not_ev_list);
                        System.out.println("list de recommandation : "+algo.getRecommendations(not_eq_list));
                        //Evaluate------------
                        for(int j=0;j<not_ev_list.size();j++){
                            System.out.println("noti+++++++++++"+ not_ev_list.get(j));
                            System.out.println("Data_Memory+++++++++++++++"+S.getSession_DataMemory().get(i));
                            System.out.println(addMinutesToDate(1,not_ev_list.get(j).getTime()));
                            if (S.getSession_DataMemory().get(i).getTime().before(addMinutesToDate(1,not_ev_list.get(j).getTime()))) {
                                Event_Test.add(S.getSession_DataMemory().get(i));
                                //System.out.println("Event_Test----------"+Event_Test);
                                break;
                            }
                        }
                        System.out.println("----------------------"+Event_Test);


                        }


                    }



                }


            }
        }



/*
            //List Algorithm
        Algorithm al=new MostPopular();

            Sort s = new Sort();
            session = s.read_load_file();
            new_sort_list = s.Sort_with_time(session);
            //System.out.println(new_sort_list);
        for (int i=0;i<new_sort_list.size();i++){

                if (new_sort_list.get(i) instanceof Item) {
                    It = new Item(((Item) new_sort_list.get(i)).id_item, ((Item) new_sort_list.get(i)).created_at, ((Item) new_sort_list.get(i)).Url, ((Item) new_sort_list.get(i)).category, ((Item) new_sort_list.get(i)).text, ((Item) new_sort_list.get(i)).title);
                    id_list_item.add(It);
                    Document doc_it = Sort.collection_Item.find(eq("ItemID", ((Item) new_sort_list.get(i)).id_item)).first();
                    //System.out.println("doc it"+doc_it);
                    if (doc_it == null) {

                        System.out.println("No results found.");
                } else {
                    //------------------------
                    String CreatAt= ((doc_it.toString().split(",")[2]).split("=")[1]);
                    //Date Creat_At_=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(CreatAt);
                      //System.out.println(CreatAt);
                    //-----------------------------------------------------------------
                    Long Item_id= Long.valueOf(((doc_it.toString().split(",")[3]).split("=")[1]));
                    String Url = ((doc_it.toString().split(",")[5]).split("=")[1]);
                    String title = ((doc_it.toString().split(",")[6]).split("=")[1]);
                    long category = Long.valueOf(((doc_it.toString().split(",")[7]).split("=")[1]));
                    String text = (((doc_it.toString().split(",")[8]).split("=")[1]));
//                    n_it=new Notification_Item(Item_id,Creat_At_,Url,category,text,title);
//                    System.out.println("not item"+n_it);

                }

            }else{
                if (new_sort_list.get(i) instanceof Event) {
                    ev = new Event(((Event) new_sort_list.get(i)).id_event, ((Event) new_sort_list.get(i)).id_item, ((Event) new_sort_list.get(i)).id_user, ((Event) new_sort_list.get(i)).time);
                    System.out.println("ev" + ev);
                    id_list_event.add(ev);
                    Document doc = Sort.collection_Event.find(eq("ItemID", ((Event) new_sort_list.get(i)).id_item)).first();
                    if (doc == null) {
                        System.out.println("No results found.");
                    } else {
                        String id_event = ((doc.toString().split(",")[1]).split("=")[1]);
                        Long Item_id = Long.valueOf(((doc.toString().split(",")[3]).split("=")[1]));

                        String time = ((doc.toString().split(",")[5]).split("=")[1]);
                        System.out.println((time));
                        Long User_id = Long.valueOf(((doc.toString().split(",")[4]).split("=")[1]));
                        Date time_event = new Date((Long.parseLong(time)));
                        Long geo = Long.valueOf(((doc.toString().split(",")[6]).split("=")[1]));
                        Long device_type = Long.valueOf((((doc.toString().split(",")[7]).split("=")[1])).split("}")[0]);

                        n_ev = new Notification_Event(id_event, Item_id, User_id, time_event, geo, device_type);
                        not_ev_list.add(n_ev);
                        System.out.println("notif event"+not_ev_list);
                        n_req = new Notification_Requete(id_event, User_id, time_event, geo, device_type);
                        not_eq_list.add(n_req);

                    }

                    //get documeny by id from mongodb

                    //event=Notification_event
                    //demande_Requete=Notification_Req
                    //demande_req.envoyer() [List item recommandé=SysRec.get_Notification_Requete(demande_Requete)]
                    //demande_req.evaluer()
                    //event.envoyer() [SysRec.get_Notification_Event(eventNotification)]
                }
            }
        }
//        MostPopular m = new MostPopular();
//        Recently_Popular r = new Recently_Popular() ;
//        System.out.println("iiiiiiiii-----------"+id_list_event);
//        r.handleEventNotification(not_ev_list);
//        System.out.println(r.getRecommendations(not_eq_list));
*/

}
