package config;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Getter
@Log4j2
public class Configuration {

    public Configuration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            JsonNode node = mapper.readTree(
                    Configuration.class.getClassLoader().getResourceAsStream("/WEB-INF/config/config.yaml"));
            this.newspapers = node.get("newspapers").asText();
            this.articles = node.get("articles").asText();
            this.types = node.get("types").asText();
            this.path = node.get("path").asText() + node.get("database").asText();
            this.user_name = node.get("user_name").asText();
            this.dB_password = node.get("dB_password").asText();
            this.driver = node.get("driver").asText();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    private String driver;
    private String path;
    private String user_name;
    private String dB_password;
    private String newspapers;
    private String articles;
    private String types;


}
