package config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class ConfigurationLogin {
    public ConfigurationLogin() {
        try {
            properties = new Properties();
            properties.load(ConfigurationLogin.class.getClassLoader().getResourceAsStream("configFiles/login.yaml"));
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Properties properties;
    public String user;
    public String password;
}
