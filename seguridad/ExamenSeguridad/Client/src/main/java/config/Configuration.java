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
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(ConstantsConfig.CONFIG_FILE));
            this.pathData = properties.getProperty(ConstantsConfig.PATH_DATA);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String pathData;
}
