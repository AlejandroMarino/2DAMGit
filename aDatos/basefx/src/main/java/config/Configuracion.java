package config;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Getter
@Log4j2
public class Configuracion {

    public Configuracion() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            JsonNode node = mapper.readTree(
                    Configuracion.class.getClassLoader().getResourceAsStream("config.yaml"));
            this.newspapers = node.get("newspapers").asText();
            this.articles = node.get("articles").asText();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }



    private String newspapers;
    private String articles;

}
