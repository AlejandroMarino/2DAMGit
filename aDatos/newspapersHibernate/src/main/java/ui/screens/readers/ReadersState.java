package ui.screens.readers;

import lombok.Data;
import modelo.Reader;

import java.util.List;

@Data
public class ReadersState {
    private final List<Reader> readers;
    private final String error;
}
