package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subscribe {
    private int idReader;
    private int idNewspaper;
}
