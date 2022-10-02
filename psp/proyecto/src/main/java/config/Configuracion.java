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
public class Configuracion {

    public Configuracion() {
        try{
            Properties p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream("config.yaml"));

            this.pathApi = p.getProperty("pathApi");
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }

    }

    private String pathApi;

}
