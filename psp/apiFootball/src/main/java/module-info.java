module apiFootball {

    requires okhttp3;

    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires io.vavr;
    requires retrofit2;

    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.jackson;
    requires retrofit2.converter.scalars;
    requires com.google.gson;
    requires jakarta.json.bind;
    requires java.validation;
    requires io.reactivex.rxjava3;
    requires org.apache.logging.log4j;
    requires org.pdfsam.rxjavafx;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    //requires javafx.swing;

    //needed for HTTP
    // requires unirest.java;

    //needed for JSON
//    requires gson;
//    requires java.sql;

    exports dao.modelo.jax;
    //needed for JavaFX
    opens gui.controllers to javafx.fxml;

    // yaml
   //opens config to org.yaml.snakeyaml.Yaml;

//    //needed for JSON
//    opens com.edencoding.models.openVision to gson;
//    opens com.edencoding.models.dogs to gson;

    exports gui;
}

