module cliente {
    requires lombok;
    requires domain;
    requires io.vavr;
    requires io.reactivex.rxjava3;
    requires retrofit2;
    requires okhttp;
    requires okhttp3;
    requires com.google.gson;

    exports data.dao;

}