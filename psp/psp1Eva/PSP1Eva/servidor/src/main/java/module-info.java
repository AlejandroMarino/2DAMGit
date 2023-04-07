module servidor {
    requires com.zaxxer.hikari;
    requires jakarta.jakartaee.web.api;
    requires lombok;
    requires java.sql;
    requires spring.jdbc;
    requires spring.tx;
    requires com.google.gson;
    requires domain;
    requires org.apache.logging.log4j;
}