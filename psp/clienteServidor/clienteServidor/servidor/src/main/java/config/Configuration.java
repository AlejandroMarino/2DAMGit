package config;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {
    private Properties properties;
    private String driver;
    private String path;
    private String user_name;
    private String dB_password;

    public Configuration() {

        try {
            properties = new Properties();
            properties.load(Configuration.class.getClassLoader().getResourceAsStream("config.yaml"));
            this.path = properties.getProperty("path") + properties.getProperty("database");
            this.user_name = properties.getProperty("user_name");
            this.dB_password = properties.getProperty("dB_password");
            this.driver = properties.getProperty("driver");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
