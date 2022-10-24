package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
    private int id;
    private String name;
    private Type type;
    private int idNewspaper;
}
