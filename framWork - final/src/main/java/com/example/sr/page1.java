package com.example.sr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class page1 implements Initializable {
    @FXML
    Pane rootpane;
    @FXML
    Label choixlabel;

    @FXML
    RadioButton horsLigne;
    @FXML
    RadioButton enLigne;
    @FXML
    Button commencer_evaluate;

    public String horsorenLigne;
    @FXML

    private void Quit(MouseEvent MOUSE_CLICKED){
        System.exit(0);
    }
    @FXML
    void minimize(MouseEvent MOUSE_CLICKED){

        Stage stage =(Stage)rootpane.getScene().getWindow();
        stage=(Stage)((Button)MOUSE_CLICKED.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }
    @FXML

    private void choix_evaluate(ActionEvent event) {

        if (!commencer_evaluate.isPressed()) {
            choixlabel.setVisible(true);
            horsLigne.setVisible(true);
            enLigne.setVisible(true);



        }
    }

    @FXML
    void commencer(ActionEvent event) throws IOException, ClassNotFoundException{
      Pane p= FXMLLoader.load(getClass().getResource("page2.fxml"));
      rootpane.getChildren().setAll(p);
    }
    @FXML
    void horsLigne(ActionEvent event) throws IOException, ClassNotFoundException{

        if(horsLigne.isSelected() ){
            horsorenLigne="horsLigne";
        }
        if(!horsLigne.isSelected() || !enLigne.isSelected()){

        }
        Pane p= FXMLLoader.load(getClass().getResource("page2HorsLigne.fxml"));
        rootpane.getChildren().setAll(p);
    }
    @FXML
    void enLigne(ActionEvent event) throws IOException, ClassNotFoundException{
        if(enLigne.isSelected()){
            horsorenLigne="enLigne";

        }
        if(!horsLigne.isSelected() || !enLigne.isSelected()){
        }
        Pane p= FXMLLoader.load(getClass().getResource("page2EnLigne.fxml"));
        rootpane.getChildren().setAll(p);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choixlabel.setVisible(false);
        horsLigne.setVisible(false);
        enLigne.setVisible(false);

    }



}
