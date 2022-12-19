package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
    private int id;
    private String name;
    private int type;
    private int idNewspaper;
}
