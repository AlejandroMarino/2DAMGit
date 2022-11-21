module domain {
    requires lombok;
    requires jakarta.jakartaee.web.api;

    exports domain.modelo;
    exports domain.error;

    opens domain.modelo;
}