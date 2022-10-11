package ui.pantallas.Newspaper;

import lombok.Data;
import modelo.Newspaper;

import java.util.List;

@Data
public class NewspaperState {

    private final List<Newspaper> newspapers;
    private final String error;
}
