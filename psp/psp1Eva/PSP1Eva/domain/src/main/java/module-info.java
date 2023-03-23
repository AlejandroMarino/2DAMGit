module domain {

    requires lombok;

    exports domain.models;
    exports domain.errors;

    opens domain.models;
    opens domain.errors;
}