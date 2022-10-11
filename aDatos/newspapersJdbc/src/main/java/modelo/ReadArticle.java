package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadArticle {
    private int idArticle;
    private int idReader;
    private int ranking;
}
