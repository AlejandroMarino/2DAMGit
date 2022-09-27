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
    requires io.vavr;

    exports ui.fx;
    exports data;
    exports ui.pantallas.principal;
    exports ui.pantallas.Newspaper;
    exports ui.pantallas.login;
    exports ui.common;
    exports ui.pantallas.addArticles;
    exports ui.pantallas.delNewspaper;
    exports ui.pantallas.articles;
    exports modelo;


    opens config;
    opens modelo;
    opens ui.fx;
    opens ui.pantallas.principal;
    opens ui.pantallas.login;
    opens ui.pantallas.Newspaper;
}