package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Subscribe {
    private int idReader;
    private int idNewspaper;
    private LocalDate startDate;
    private LocalDate cancellationDate;
}
