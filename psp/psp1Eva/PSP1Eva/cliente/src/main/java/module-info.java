module cliente {
    requires io.reactivex.rxjava3;
    requires io.vavr;
    requires okhttp3;
    requires retrofit2;
    requires domain;
    requires org.apache.logging.log4j;
    requires retrofit2.adapter.rxjava3;
    requires lombok;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.cdi;
    requires jakarta.inject;
    requires com.google.gson;
    requires retrofit2.converter.gson;

    exports cliente.ui.fx;
    exports cliente.ui.pantallas.principal;
    exports cliente.ui.pantallas.games;
    exports cliente.ui.pantallas.shops;
    exports cliente.services.servicesImpl;
    exports cliente.data.dao;
    exports cliente.data.network;
    exports cliente.config;
    exports cliente.ui.common;
    exports cliente.common;
    exports cliente.data.dao.daoImpl;
    exports cliente.ui.pantallas.register;
    exports cliente.ui.pantallas.login;
    exports cliente.data;

    opens cliente.ui.fx;
    opens cliente.ui.pantallas.principal;
    opens cliente.ui.pantallas.games;
    opens cliente.ui.pantallas.shops;
    opens cliente.data.dao;
    opens cliente.ui.pantallas.login;
    opens cliente.ui.pantallas.register;
}