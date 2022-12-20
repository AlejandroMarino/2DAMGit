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
    requires java.sql;
    requires com.zaxxer.hikari;
    requires commons.dbcp2;
    requires jakarta.annotation;
    requires spring.jdbc;
    requires spring.tx;
    requires jakarta.persistence;

    exports ui.fx;
    exports data;
    exports ui.screens.principal;
    exports ui.screens.Newspaper;
    exports ui.screens.login;
    exports ui.common;
    exports ui.screens.addArticles;
    exports ui.screens.delNewspaper;
    exports ui.screens.articles;
    exports modelo;
    exports servicios;
    exports config;
    exports ui.screens.filterArticles;

    opens ui.screens.delNewspaper;
    opens config;
    opens modelo;
    opens ui.fx;
    opens ui.screens.articles;
    opens ui.screens.filterArticles;
    opens ui.screens.addArticles;
    opens ui.screens.principal;
    opens ui.screens.login;
    opens ui.screens.Newspaper;
}