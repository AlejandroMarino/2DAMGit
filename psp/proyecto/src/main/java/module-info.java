module basefx {
    requires lombok;
    requires org.apache.logging.log4j;
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
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;
    requires retrofit2.adapter.rxjava3;

    exports ui.fx;
    exports common;
    exports servicios;
    exports ui.pantallas.principal;
    exports ui.pantallas.inicio;
    exports ui.common;
    exports ui.pantallas.allFish;
    exports ui.pantallas.infoFish;
    exports data.retrofit;
    exports data;
    exports modelo;
    exports config;
    exports ui.pantallas.imagenFish;

    opens config;
    opens modelo;
    opens ui.fx;
    opens ui.pantallas.principal;
    opens ui.pantallas.inicio;
    opens ui.pantallas.allFish;
    opens ui.pantallas.imagenFish;
    exports data.impl;
    exports servicios.serviciosImpl;
    opens ui.pantallas.infoFish;
}