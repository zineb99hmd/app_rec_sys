module com.example.sr {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.bson;
    requires it.unimi.dsi.fastutil;
    //requires mongodb.driver;
    requires java.logging;
    requires org.mongodb.driver.core;
    requires datafx.core;


    opens com.example.sr to javafx.fxml;
    exports com.example.sr;
}