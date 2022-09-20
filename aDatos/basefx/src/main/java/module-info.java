module prova {
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires jakarta.cdi;
    requires javafx.graphics;
    requires jakarta.inject;
    requires javafx.fxml;

    exports ui.fx;
    exports servicios;
    exports data;
    exports ui.pantallas.principal;
    exports ui.pantallas.login;


    opens config;
    opens modelo;
    opens ui.fx;
}