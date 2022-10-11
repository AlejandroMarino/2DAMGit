package ui.pantallas.delNewspaper;

import lombok.Data;
import modelo.Newspaper;

import java.util.List;

@Data
public class DelNewspaperState {
    private final List<Newspaper> newspapers;
    private final String error;
}
