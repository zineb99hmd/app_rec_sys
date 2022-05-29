package com.example.sr;
import data.Event;
import data.Item;
import data.Notification;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import data.loading.ReadPlista;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class page2Horsligne implements Initializable {
    List<String> list_file;
    private static String lien_item;
    private static String lien_event;
    public static List<Item>data_item=new  ArrayList<>();
    public static List<Event>data_event=new  ArrayList<>();
    public static List<table> table_item=new ArrayList<>();




    @FXML
    Pane rootpane;
    @FXML
    TextField itemfield;
    @FXML
    TextField eventfield;
    @FXML
    TableView<table> itemTable;
    @FXML
    TableView<table> eventTAble;
    @FXML
    TableColumn <table,String>createdAt;
    @FXML
    TableColumn<table, String> ItemID;
    @FXML
    TableColumn <table,String>URL;

    @FXML
    TableColumn <table,String>ItemId;
    @FXML
    TableColumn <table,String>UserId;
    @FXML
    TableColumn <table,String>TimeStampe;
    @FXML
    TableColumn <table,String>Id_event;
    private SimpleStringProperty time;
    private SimpleStringProperty item_id;
    private SimpleStringProperty Creat_At;
    private SimpleStringProperty url;
    private SimpleStringProperty id_event;
    private SimpleStringProperty item_id_;
    private SimpleStringProperty user_id;

    public ObservableList<table> data_item2;
    public ObservableList<table> data_event2;
    public List<table> data_item_=new ArrayList<>();
    public List<table> data_event_=new ArrayList<>();
    @FXML


    private void Quit(ActionEvent event){
        System.exit(0);
    }

    @FXML
    void minimize(ActionEvent event){

        Stage stage =(Stage)rootpane.getScene().getWindow();
        stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }
    @FXML
    void choix_File_item(ActionEvent event){
        FileChooser fc=new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("dataSet1",list_file));
        File f=fc.showOpenDialog(null);
        if(f != null){
            itemfield.setText(f.getAbsolutePath());
            lien_item=f.getAbsolutePath();

        }
    }
    @FXML
    void choix_File_event(ActionEvent event){
        FileChooser fc=new FileChooser();
        HelloApplication S=new HelloApplication();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("dataSet1",list_file));
        File f=fc.showOpenDialog(null);
        if(f != null){
            eventfield.setText(f.getAbsolutePath());
            S.file_event=f.getAbsolutePath();
            System.out.println(S.file_event);
            lien_event=f.getAbsolutePath();
            System.out.println("lien event "+lien_event);


        }
    }
    @FXML
    void choisirAlgorithm(ActionEvent event) throws IOException, ClassNotFoundException{
        Pane p= FXMLLoader.load(getClass().getResource("page3HorsLigne.fxml"));
        rootpane.getChildren().setAll(p);
    }
    @FXML
    public void choisir(ActionEvent event){
        HelloApplication S=new HelloApplication();
        System.out.println("hada choisir item"+S.file_item);
        System.out.println("hada choisir event "+S.file_event);
        System.out.println(ReadPlista.loadPlistaData(S.file_item,S.file_event));

        // S.Sort_By_time( S.read_load_file());
        //itemTable.getItems().add();

    }
    @FXML
    void test(ActionEvent event){

        System.out.println("hada choisir item"+getLien_item());
        System.out.println("hada choisir event "+getLien_event());
        //System.out.println("data" +data_item);
        Notification itemOrEvent;

        List<Notification> trainingWindow=ReadPlista.loadPlistaData(getLien_item(),getLien_event());

//            FileSource fs = new FileSource(S.file_item);
//            System.out.println("fs"+fs);
//            CSVDataSource dataSource = new CSVDataSource(fs, "ItemID", "CreatedAt", "URL");
//            System.out.println("datasource"+dataSource.getNamedColumn("ItemID"));
//           // TableView item_table = new TableView();
//            TableColumn<?, ?> ItemID = dataSource.getNamedColumn("ItemID");
//            TableColumn<?, ?> CreatedAt = dataSource.getNamedColumn("CreatedAt");
//            TableColumn<?, ?> URL = dataSource.getNamedColumn("URL");
//            itemTable.getColumns().addAll(ItemID, CreatedAt, URL);
//            itemTable.setItems(dataSource.getData());
//            itemTable.setEditable(true);

//
//        createdAt.setCellValueFactory(new PropertyValueFactory<Item,String>("createdAt"));
//        URL.setCellValueFactory(new PropertyValueFactory<Item,String>("URL"));

        for (int i = 0; i < trainingWindow.size(); i++) {
            itemOrEvent = trainingWindow.get(i);
            if (itemOrEvent instanceof Item) {


                data_item_.add(new table(Long.toString(itemOrEvent.getId()), (itemOrEvent.getTime()).toString(), ((Item) itemOrEvent).getUrl()));
                //System.out.println("time of item :  " +(itemOrEvent.getTime()).toString());
            }
            else {
                data_event_.add(new table(Long.toString(itemOrEvent.getId()), Long.toString(((Event)itemOrEvent).getId_item()), Long.toString(((Event)itemOrEvent).getId_user()),(itemOrEvent.getTime()).toString()));
                // System.out.println("time of event : " + (itemOrEvent.getTime()).toString());

            }
        }
        data_item2 = FXCollections.observableArrayList(data_item_);
        ItemID.setCellValueFactory(new PropertyValueFactory<table, String>("item_id"));
        createdAt.setCellValueFactory(new PropertyValueFactory<table, String>("Creat_At"));
        itemTable.setItems(data_item2);
        data_event2 = FXCollections.observableArrayList(data_event_);
        ItemId.setCellValueFactory(new PropertyValueFactory<table, String>("item_id_"));
        UserId.setCellValueFactory(new PropertyValueFactory<table, String>("user_id"));
        TimeStampe.setCellValueFactory(new PropertyValueFactory<table, String>("Creat_At"));
        eventTAble.setItems(data_event2);






//        for (int j = 0; j < data_event.size(); j++) {
//
//
//            data_event_.add(  new table(Long.toString(data_event.get(j).getId_event()), Long.toString(data_event.get(j).getId_item()),Long.toString(data_event.get(j).getId_user()), (data_event.get(j).getTime()).toString()));
//
//
//        }
//
//        data_event2 = FXCollections.observableArrayList(data_event_);
//        System.out.println("data event 2" +data_event2);
//        ItemId.setCellValueFactory(new PropertyValueFactory<table, String>("item_id_"));
//        UserId.setCellValueFactory(new PropertyValueFactory<table, String>("user_id"));
//        TimeStampe.setCellValueFactory(new PropertyValueFactory<table, String>("time"));
//
//         //System.out.println("data+++" +data_event2);
//
//        eventTAble.setItems(data_event2);

//            ItemID.setCellValueFactory(new PropertyValueFactory<Item,Long>("ItemID"));
//            itemTable.setItems(data_item);








    }


    public TableColumn<table, String> getCreatedAt() {
        return createdAt;
    }

    public TableColumn<table, String> getItemID() {
        return ItemID;
    }

    public TableColumn<table, String> getURL() {
        return URL;
    }
    public TableColumn<table, String> getid_event() {
        return Id_event;
    }


    public TableColumn<table, String> getItemID_() {
        return ItemId;
    }

    public TableColumn<table, String> getUserID() {
        return UserId;
    }

    public String getitem_id() {
        return item_id.get();
    }
    public SimpleStringProperty item_idProperty() {
        return item_id ;
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

    public TableColumn<table, String> getTimeStampe() {
        return TimeStampe;
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public SimpleStringProperty timestampProperty() {
        return time;
    }

    public SimpleStringProperty user_idProperty() {
        return user_id;
    }

    public String getId_event() {
        return id_event.get();
    }

    public SimpleStringProperty id_eventProperty() {
        return id_event;
    }


    public SimpleStringProperty timeStampProperty() {
        return time;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list_file=new ArrayList<>();
        list_file.add("*.CSV");
        list_file.add("*.RATING");






    }

    public static String getLien_item() {
        return lien_item;
    }

    public static String getLien_event() {
        return lien_event;
    }



}