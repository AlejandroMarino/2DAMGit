package config;


import common.Constants;
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
            properties.load(Configuration.class.getClassLoader().getResourceAsStream(Constants.CONFIG_YAML));
            this.path = properties.getProperty(Constants.PATH) + properties.getProperty(Constants.DATABASE);
            this.user_name = properties.getProperty(Constants.USER_NAME);
            this.dB_password = properties.getProperty(Constants.D_B_PASSWORD);
            this.driver = properties.getProperty(Constants.DRIVER);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
