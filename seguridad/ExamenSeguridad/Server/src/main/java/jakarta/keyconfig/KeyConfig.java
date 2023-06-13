package jakarta.keyconfig;

import jakarta.common.Constants;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.Properties;

@Singleton
public class KeyConfig {
    private final Properties properties;

    private KeyConfig() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(Constants.KEY_PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
