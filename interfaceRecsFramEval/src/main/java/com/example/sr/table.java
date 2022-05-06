package com.example.sr;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class table {
    private SimpleStringProperty item_id;
    private SimpleStringProperty Creat_At;
    private SimpleStringProperty url;
    private SimpleStringProperty id_event;
    private SimpleStringProperty item_id_;
    private SimpleStringProperty user_id;
    private SimpleStringProperty time;

    private SimpleStringProperty Metrique;
    private SimpleStringProperty Resultats;

    private SimpleStringProperty Alg;
    private SimpleStringProperty recommandation;
    private SimpleDoubleProperty empty;

    public table(String item_id, String Creat_At, String url){
        this.item_id=new SimpleStringProperty(item_id);
        this.Creat_At=new SimpleStringProperty(Creat_At);
        this.url=new SimpleStringProperty(url);
    }
    public table(String id_event,String item_id_, String user_id, String time){
        this.id_event = new SimpleStringProperty(id_event);
        this.item_id_ = new SimpleStringProperty(item_id_);
        this.user_id=new SimpleStringProperty(user_id);
        this.Creat_At=new SimpleStringProperty(time);
    }
    public table(String metric,String resultats){
        this.Metrique=new SimpleStringProperty(metric);
        this.Resultats=new SimpleStringProperty(resultats);

    }
    public table(String alg, String recommandation, double empty){
        this.Alg=new SimpleStringProperty(alg);
        this.recommandation=new SimpleStringProperty(recommandation);
        this.empty=new SimpleDoubleProperty(empty);

    }

    public String getAlg() {
        return Alg.get();
    }

    public SimpleStringProperty algProperty() {
        return Alg;
    }

    public String getRecommandation() {
        return recommandation.get();
    }

    public SimpleStringProperty recommandationProperty() {
        return recommandation;
    }

    public double getEmpty() {
        return empty.get();
    }

    public SimpleDoubleProperty emptyProperty() {
        return empty;
    }

    public String getMetrique() {
        return Metrique.get();
    }

    public SimpleStringProperty metriqueProperty() {
        return Metrique;
    }

    public String getResultats() {
        return Resultats.get();
    }

    public SimpleStringProperty resultatsProperty() {
        return Resultats;
    }

    public String getItem_id() {
        return item_id.get();
    }

    public SimpleStringProperty item_idProperty() {
        return item_id;
    }

    public String getCreat_At() {
        return Creat_At.get();
    }

    public SimpleStringProperty creat_AtProperty() {
        return Creat_At;
    }

    public String getUrl() {
        return url.get();
    }

    public SimpleStringProperty urlProperty() {
        return url;
    }

    public String getItem_id_() {
        return item_id_.get();
    }

    public SimpleStringProperty item_id_Property() {
        return item_id_;
    }

    public String getUser_id() {
        return user_id.get();
    }

    public SimpleStringProperty user_idProperty() {
        return user_id;
    }

    public String getTime_stamp() {
        return time.get();
    }

    public SimpleStringProperty time_stampProperty() {
        return time;
    }

    public String getId_event() {
        return id_event.get();
    }

    public SimpleStringProperty id_eventProperty() {
        return id_event;
    }
}
