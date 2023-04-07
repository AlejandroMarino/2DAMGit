package config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {

    public Configuration() {
        try {
            properties = new Properties();
            properties.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
            this.customers = properties.getProperty("customers");
            this.menuItems = properties.getProperty("menuItems");
            this.orderItems = properties.getProperty("orderItems");
            this.orders = properties.getProperty("orders");
            this.restaurant = properties.getProperty("restaurant");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    private Properties properties;
    public String restaurant;
    public String customers;
    public String menuItems;
    public String orderItems;
    public String orders;
}
