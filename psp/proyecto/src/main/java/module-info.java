module basefx {
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires jakarta.cdi;
    requires javafx.graphics;
    requires jakarta.inject;
    requires javafx.fxml;
    requires javafx.controls;
    requires retrofit2;
    requires com.squareup.moshi;
    requires retrofit2.converter.moshi;
    requires okhttp3;
    requires io.vavr;

    exports ui.fx;
    exports servicios;
    exports ui.pantallas.principal;
    exports ui.pantallas.inicio;
    exports ui.common;
    exports ui.pantallas.allFish;
    exports ui.pantallas.infoFish;
    exports data.retrofit;
    exports modelo;
    exports config;

    opens config;
    opens modelo;
    opens ui.fx;
    opens ui.pantallas.principal;
    opens ui.pantallas.inicio;
    opens ui.pantallas.allFish;
    exports data.impl;
    exports servicios.serviciosImpl;
    opens ui.pantallas.infoFish;
}