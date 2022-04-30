package com.example.sr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
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
    @FXML
    Button commencer;


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

    private void choix_evaluate(ActionEvent event) {

        if (!commencer_evaluate.isPressed()) {
            choixlabel.setVisible(true);
            horsLigne.setVisible(true);
            enLigne.setVisible(true);


        }
    }
    @FXML
    void terminer(ActionEvent event) throws IOException, ClassNotFoundException{
          if(!horsLigne.isSelected() || !enLigne.isSelected()){
              commencer.setVisible(true);
          }
    }
    @FXML
    void commencer(ActionEvent event) throws IOException, ClassNotFoundException{
      Pane p= FXMLLoader.load(getClass().getResource("page2.fxml"));
      rootpane.getChildren().setAll(p);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choixlabel.setVisible(false);
        horsLigne.setVisible(false);
        enLigne.setVisible(false);
        commencer.setVisible(false);

    }



}
