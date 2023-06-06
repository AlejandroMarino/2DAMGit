module Domain {
    requires lombok;

    exports domain.models;
    exports domain.errors;

    opens domain.errors;
    opens domain.models;
}