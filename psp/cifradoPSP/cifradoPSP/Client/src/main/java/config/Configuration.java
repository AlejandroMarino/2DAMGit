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

    public Configuration() {
        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream(Constants.CONFIG_YAML));

            this.pathApi = p.getProperty(Constants.PATH_API);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private String pathApi;

}
