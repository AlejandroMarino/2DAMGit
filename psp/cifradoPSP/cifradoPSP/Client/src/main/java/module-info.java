module Client {
    requires Domain;
    requires Seguridad;
    requires jakarta.inject;
    requires jakarta.cdi;
    requires javafx.fxml;
    requires lombok;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.apache.logging.log4j;
    requires io.reactivex.rxjava3;
    requires io.vavr;
    requires com.google.gson;
    requires okhttp3;
    requires retrofit2;
    requires gson.javatime.serialisers;
    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires java.sql;

    exports ui.fx;
    exports data.network;
    exports services.servicesImpl;
    exports ui.pantallas.elegirSicarios;
    exports ui.pantallas.sicario;
    exports ui.pantallas.login;
    exports ui.pantallas.register;
    exports ui.pantallas.contratista;
    exports ui.pantallas.crearContrato;
    exports ui.pantallas.principal;
    exports data.dao.daoImpl;
    exports ui.common;
    exports config;
    exports common;
    exports domain.model;
    exports ui.generateKeyStoreServidor;
    exports data.security;

    opens ui.fx;
    opens ui.pantallas.login;
    opens ui.pantallas.register;
    opens ui.pantallas.contratista;
    opens ui.pantallas.crearContrato;
    opens ui.pantallas.principal;
    opens ui.pantallas.elegirSicarios;
    opens ui.pantallas.sicario;
}