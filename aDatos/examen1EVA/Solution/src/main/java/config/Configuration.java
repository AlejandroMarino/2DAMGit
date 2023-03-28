package config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {


    private String pathXML;
    private String urlDB;

    private String userDB;

    private String passwordDB;

    private String driverDB;

    public Configuration() {
        Properties pathReadersProp = new Properties();
        try {
            pathReadersProp.loadFromXML(getClass().getClassLoader().getResourceAsStream(ConfigConstants.PATH_CONFIGXML));
            this.pathXML = pathReadersProp.getProperty(ConfigConstants.XML_PATH);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // get DB credentials
        Properties dbCredentials = new Properties();
        try {
            dbCredentials.loadFromXML(getClass().getClassLoader().getResourceAsStream(ConfigConstants.PATH_DB_CREDENTIALS));
            this.urlDB = dbCredentials.getProperty(ConfigConstants.URL_DB);
            this.userDB = dbCredentials.getProperty(ConfigConstants.USER_DB);
            this.passwordDB = dbCredentials.getProperty(ConfigConstants.PASSWORD_DB);
            this.driverDB = dbCredentials.getProperty(ConfigConstants.DRIVER_DB);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }


    }
}
