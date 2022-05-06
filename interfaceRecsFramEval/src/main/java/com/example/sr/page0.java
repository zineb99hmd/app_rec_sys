package com.example.sr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.ResourceBundle;

public class page0  implements Initializable {
    @FXML
    Pane rootpane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          new splashscreen().start();
    }
    class splashscreen extends Thread{
        public void run(){
            try{
                Thread.sleep(3000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent fxmlLoader = null;

                        try {
                            fxmlLoader = FXMLLoader.load(getClass().getResource("page1.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(fxmlLoader);
                        Stage stage=new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                        rootpane.getScene().getWindow().hide();
                    }
                });



            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
