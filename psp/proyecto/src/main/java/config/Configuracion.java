package config;


import common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuracion {

    public Configuracion() {
        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream(Constantes.CONFIG_YAML));

            this.pathApi = p.getProperty(Constantes.PATH_API);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

    private String pathApi;

}
