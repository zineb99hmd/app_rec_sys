package com.example.sr;
import algorithm.Algorithm;
import algorithm.RecentlyPopular;
import evaluation.Evaluator;
import evaluation.metrics.F1;
import evaluation.metrics.MAP;
import evaluation.metrics.Metric;
import evaluation.metrics.NDCG;
import org.bson.Document;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sender.Sender;
import com.example.sr.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
     static page2 p2=new page2();
    static String file_item =p2.getLien_item();
    static String file_event =p2.getLien_event();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("page0.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }



    public static void main(String[] args) {
        launch();

    }
}