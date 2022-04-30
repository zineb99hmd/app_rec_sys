package com.example.sr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class page3 implements Initializable {
    @FXML
    Pane rootpane;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}